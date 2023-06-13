package model;

import sfx.*;

public class Court extends AbstractCourt {
    // instance parameters
    protected final RacketController playerA, playerB;
    
    // instance state
   
    protected double countdown; //En frames
    protected int scoreA, scoreB;
    protected Ball ball;
    protected Racket racketA;
    protected Racket racketB;

    protected IA IA;


    public Court(RacketController playerA, RacketController playerB, double width, double height) {
    	super(width, height);
        this.playerA = playerA;
        this.playerB = playerB;
        this.ball = new Ball();
        this.racketA = new Racket();
        this.racketB = new Racket();
        this.IA = new IA();
        reset();
    }
    
    public Racket getRacketA(){
        return this.racketA;
    }

    public Racket getRacketB(){
        return this.racketB;
    }
    
    public Ball getBall(){
        return this.ball;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

   
    public double getCountdown() {
    	return countdown;
    }
    
    public void setCountdown(double c){
        this.countdown=c;
    }
    
    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreA(int n){
        this.scoreA = n;
    }

    public void setScoreB(int n){
        this.scoreB = n;
    }

    @Override
    public void update(double deltaT) {		// Met a jour la position des raquettes en fonction de leur etat actuel

        switch (playerA.getState()) {
            case GOING_UP:
                racketA.setRacketY(racketA.getRacketY()-racketA.getRacketSpeed()* deltaT);
                if (racketA.getRacketY()< 0.0) racketA.setRacketY(0.0);
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA.setRacketY(racketA.getRacketY()+racketA.getRacketSpeed()* deltaT);
                if (racketA.getRacketY() + racketA.getRacketSize() > height) racketA.setRacketY(height - racketA.getRacketSize());
                break;
        }
        if(!IA.getState()) {
            switch (playerB.getState()) {
                case GOING_UP:
                    racketB.setRacketY(racketB.getRacketY() - racketB.getRacketSpeed() * deltaT);
                    if (racketB.getRacketY() < 0.0) racketB.setRacketY(0.0);
                    break;
                case IDLE:
                    break;
                case GOING_DOWN:
                    racketB.setRacketY(racketB.getRacketY() + racketB.getRacketSpeed() * deltaT);
                    if (racketB.getRacketY() + racketB.getRacketSize() > height)
                        racketB.setRacketY(height - racketB.getRacketSize());
                    break;

            }
        }else{
            IA.repositionnement(this);
        }
        this.countdown -= deltaT;
        if (gameEnd()) System.exit(0);
        if (updateBall(deltaT) || this.countdown < 0) reset();
    }


    /**
     * @return true if a player lost
     */
    @Override
    protected boolean updateBall(double deltaT) {		// Met a jour la position de la balle
    	
        // first, compute possible next position if nothing stands in the way
        double nextBallX = this.ball.getBallX() + deltaT * this.ball.getBallSpeedX();
        double nextBallY = this.ball.getBallY() + deltaT * this.ball.getBallSpeedY();
        Ball nextBall = new Ball(nextBallX, nextBallY, this.ball.getBallSpeedX(), this.ball.getBallSpeedY());
        // next, see if the ball would meet some obstacle
        
        if (nextBall.outOfVerticalCourtBounds()) {	//Si la balle touche une extremite verticale
            ball.setBallSpeedY(-(ball.getBallSpeedY()));
            nextBallY = ball.getBallY() + deltaT * ball.getBallSpeedY();
            //new SFX("SFXDir" + SYS_SEP + "WallHit.mp3").play();
        }
        
        if (racketA.hasHitLeft(nextBall)) { //Si la balle touche une raquette
            ball.setBallSpeedX(-(ball.getBallSpeedX()));
            ball.setBallSpeedY((double)10*(this.ball.getBallY()-(racketA.getRacketY()+racketA.getRacketSize()/2)));
            nextBallY=ball.getBallY() + deltaT * ball.getBallSpeedY();
            nextBallX = ball.getBallX() + deltaT * ball.getBallSpeedX();
           // new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();
        
        }else if(racketB.hasHitRight(nextBall)){
            ball.setBallSpeedX(-(ball.getBallSpeedX()));
            ball.setBallSpeedY((double)10*(this.ball.getBallY()-(racketB.getRacketY()+racketB.getRacketSize()/2)));
            nextBallY=ball.getBallY() + deltaT * ball.getBallSpeedY();
            nextBallX = ball.getBallX() + deltaT * ball.getBallSpeedX();
            //new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();

        } else if (nextBallX < 0) {	//Si la balle touche l'extremite gauche
            scoreB++; //incremente le score du joueur B si le joueur A (a gauche) manque la balle 
            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
            return true;
        } else if (nextBallX > width) {//Si la balle touche l'extremite droite
            scoreA++; //incremente le score du joueur A si le joueur B (a droite) manque la balle 
            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
            return true;
        }
        this.ball.setBallX(nextBallX);
        this.ball.setBallY(nextBallY);

        return false;
    }

    @Override
    void reset() {
        IA.setBoth(racketB,ball);
    	this.racketA.setRacketX(0);
        this.racketA.setRacketY(height / 2);
        this.racketB.setRacketX(width);
        this.racketB.setRacketY(height / 2);
        
        //Vitesse de base : 200.0; Vitesse de 150 a 250
        switch(rng.nextInt(2)) {	//Permet de rendre la direction horizontale aleatoire
            case 0 : this.ball.setBallSpeedX(-(rng.nextInt(100) + 150)); break;
            case 1 : this.ball.setBallSpeedX(rng.nextInt(100) + 150); break;
            default : this.ball.setBallSpeedX(200);
        }
        switch(rng.nextInt(2)) {
            case 0 : this.ball.setBallSpeedY(-(rng.nextInt(100) + 150)); break;
            case 1 : this.ball.setBallSpeedY(rng.nextInt(100) + 150); break;
            default : this.ball.setBallSpeedX(200);
        }

        this.ball.setBallX(width / 2);
        this.ball.setBallY(height / 2);
        
        this.countdown = 60;
    }
    
    @Override
    boolean gameEnd() {
    	return scoreA > 20 || scoreB > 20;
    }
}
