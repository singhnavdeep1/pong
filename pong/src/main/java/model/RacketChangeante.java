package model;

public class RacketChangeante extends Court {

    public RacketChangeante(RacketController playerA, RacketController playerB, double width, double height){
        super(playerA,playerB,width,height);
    }

    @Override
    protected boolean updateBall(double deltaT) {		// Met a jour la position de la balle
        // first, compute possible next position if nothing stands in the way
        double nextBallX = this.getBall().getBallX() + deltaT * this.getBall().getBallSpeedX();
        double nextBallY = this.getBall().getBallY() + deltaT * this.getBall().getBallSpeedY();
        Ball nextBall = new Ball(nextBallX, nextBallY, this.ball.getBallSpeedX(), this.ball.getBallSpeedY());
        
        // next, see if the ball would meet some obstacle
        if (nextBall.outOfVerticalCourtBounds()) {	//Si la balle touche une extremite verticale
            getBall().setBallSpeedY(-(getBall().getBallSpeedY()));
            nextBallY = getBall().getBallY() + deltaT * getBall().getBallSpeedY();
            //new SFX("SFXDir" + SYS_SEP + "WallHit.mp3").play();
            
        }

        if (racketA.hasHitLeft(nextBall) || racketB.hasHitRight(nextBall)) { //Si la balle touche une raquette
            getBall().setBallSpeedX(-(getBall().getBallSpeedX()));
            nextBallX = getBall().getBallX() + deltaT * getBall().getBallSpeedX();
            //new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();

        } 
        
        else if (nextBallX < 0) {	//Si la balle touche l'extremite gauche
            setScoreB(getScoreB()+1); //incremente le score du joueur B si le joueur A (a gauche) manque la balle 
       //     new SFX("SFXDIR" + SYS_SEP + "RacketHit.mp3").play();
            this.getRacketA().setRacketSize(this.getRacketA().getRacketSize()/1.3);
            this.getRacketB().setRacketSize(this.getRacketB().getRacketSize()/1.3);
             if(racketA.getRacketSize()< 10)System.exit(0);
         //   new SFX("SFXDIR" + SYS_SEP + "RacketShrink.mp3").play();
            return true;
        } 
        
        else if (nextBallX > getWidth()) {//Si la balle touche l'extremite droite
            setScoreA(getScoreA()+1); //incremente le score du joueur A si le joueur B (a droite) manque la balle
         //   new SFX("SFXDIR" + SYS_SEP + "RacketHit.mp3").play();
            this.getRacketA().setRacketSize(this.getRacketA().getRacketSize()/1.3);
            this.getRacketB().setRacketSize(this.getRacketB().getRacketSize()/1.3); 
            if(racketA.getRacketSize()< 10)System.exit(0);
         //   new SFX("SFXDIR" + SYS_SEP + "RacketShrink.mp3").play();
            return true;
        }
        
        this.getBall().setBallX(nextBallX);
        this.getBall().setBallY(nextBallY);
        
        return false;
    }
    
    @Override
    boolean gameEnd() {
    	return (racketA.getRacketSize() < 25);
    }
  
    

}


