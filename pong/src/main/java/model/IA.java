package model;

import gui.GameView;
import sfx.SFX;

import java.awt.*;

public class IA {
    private int level; //1 à 10 on pourrait bind une touche pour chaque niveau
    private double speedCap = 0;
    private double randomness;//1 à 100%
    private boolean state = false; // allumer ou éteint

    private int priorite; //permettra que l'ia aie un comportement à la fois

    private Racket racket;

    private Ball ball;
    private Court court;
    private double intensite = 1;
    private boolean predictionMade = false;
    private Point predictionPoint = new Point(1,1);


    public IA (int level, boolean state) {
        this.level = level;
        this.state = state;
        this.randomness = level*0.1;
    }

    public IA (int level) {
        this(level,true);
    }

    public IA () {
        this(1,false);
    }

    public void setRacket(Racket racket){
        this.racket = racket;
        speedCap = racket.getRacketSpeed();
    }
    public void setBall(Ball ball){
        this.ball = ball;
    }
    public void setBoth(Racket racket, Ball ball){
        setRacket(racket);
        setBall(ball);

    }
    public void setCourt(Court court){
        this.court = court;
    }
    public void setState(boolean b) {
        this.state = b;
    }

    public boolean getState(){
        return this.state;
    }

    public boolean isPredictionMade() {
        return predictionMade;
    }


    public void repositionnement(Court court){
        if(dansLaxe()){
            glisser();
        }else{
            glisser();
        }
    }

    public boolean dansLaxe(){
        return(ball.getBallY() > racket.getRacketY() || ball.getBallY() < racket.getRacketY() + racket.getRacketSize());
        //si la racket est superposé à la balle âr rapport à l'axe Y
    }

    public boolean procheDuBordY(){
        return( (ball.getBallY() > 0 && ball.getBallY() < 150) || (ball.getBallY() > court.getHeight()-150 && ball.getBallY() < court.getHeight()) );
    }
    public boolean procheDuBordX(){
        return ( /*(ball.getBallX() > 0 && ball.getBallX() < 500) ||*/ (ball.getBallX() > court.getWidth()-500 && ball.getBallY() < court.getWidth()) ); //pas propre à une raquette
    }

    public void glisser() {
        if(dansLaxe() || !procheDuBordY()){
            intensite = intensite - 0.005;
        }else{
            intensite = intensite +0.005;
        }
        if(procheDuBordX()){
            intensite = intensite +0.007 ;
        }

        if (racket.getRacketY() < 0) {

            if(!racket.isTheBallUpsideTheCenter(ball)){
                deplacement();
            }

        }else if( (racket.getRacketY() >= court.getHeight() - racket.getRacketSize())){

            if(racket.isTheBallUpsideTheCenter(ball)){
                deplacement();
            }

        }else{
            deplacement();
        }
    }

    public void setIntensite(boolean positionBall){
        if(!racket.isTheBallUpsideTheCenter(ball)){
            intensite = (Math.abs(intensite));
        }else{
            intensite = -(Math.abs(intensite));
        }
        double i = (Math.abs(intensite));
        if(i >= 1){
            intensite = 1;/////////////////////////////////////////
        } else if (i < 1 && i >0.1) {

        }else if(i < 0.1){
            intensite = 0.1;
        }
        System.out.println(/*racket.getRacketSpeed() * -ball.directionBallInt() *  */intensite /* GameView.getDeltaT()*/ );
    }

    public void deplacement(){
        System.out.println(/*racket.getRacketSpeed() * -ball.directionBallInt() *  */intensite /* GameView.getDeltaT()*/ );
        //System.out.println(racket.isTheBallUpsideTheCenter(ball));
        setIntensite(racket.isTheBallUpsideTheCenter(ball));
        racket.setRacketY(racket.getRacketY() + racket.getRacketSpeed() *  intensite * GameView.getDeltaT() );
    }

    public void deplacementPoint(){
        if(predictionPoint.x == -1){
            predictionMade = false;
            return;
        }
        int y = predictionPoint.y;
        double deltaT = GameView.getDeltaT();
        if ( y > racket.getRacketY() + 20){
            System.out.println("variant "+ (+deltaT * racket.getRacketSpeed()));
            System.out.println("deltaT "+ (deltaT ));
            System.out.println("position " + racket.getRacketY());
            System.out.println("vitesse "+ racket.getRacketSpeed());
            if(racket.getRacketY() > court.getHeight() - racket.getRacketSize() && ball.getBallSpeedY() > -1)return;
            racket.setRacketY(racket.getRacketY() + deltaT * racket.getRacketSpeed());
        }else if(y < racket.getRacketY() - 20){
            System.out.println("position " + racket.getRacketY());
            System.out.println("variant " + (-deltaT * racket.getRacketSpeed()));
            System.out.println("position " + racket.getRacketY());
            System.out.println("vitesse "+ racket.getRacketSpeed());
            racket.setRacketY(racket.getRacketY() - deltaT * racket.getRacketSpeed());
        }else{
            predictionPoint = new Point(-1,-1);
        }
    }

    public void predictionBord(boolean side){ // side = true → bord droit, bord gauche sinon
        if(predictionMade){return;}
        boolean atteint = false;
        double deltaT = GameView.getDeltaT();
        double ballSpeedX = ball.getBallSpeedX();
        double ballSpeedY = ball.getBallSpeedY();
        double ballXPosition = ball.getBallX();
        double ballYPosition = ball.getBallY();
        double nextBallX;
        double nextBallY;
        double width = court.getWidth();
        double height = court.getHeight();
        int securite = 0;
        while(!atteint && securite != 10000){
            securite++;
            nextBallX = ballXPosition + deltaT * ballSpeedX;
            nextBallY = ballYPosition + deltaT * ballSpeedY;
            if ( nextBallY < 0 || nextBallY > height) {
                ballSpeedY = -ballSpeedY;
                nextBallY = ballYPosition + deltaT * ballSpeedY;
            }
            if (nextBallX < width/10) {
                if(!side){
                    if(ballYPosition > height)ballYPosition = height - racket.getRacketSize();
                    predictionMade = true;
                    predictionPoint = new Point((int)ballXPosition,(int)ballYPosition);
                    break;
                }
                ballSpeedX = -ballSpeedX;
                nextBallX = ballXPosition + deltaT * ballSpeedX;
            } else if (nextBallX > 900 - width/10) {
                if(side){
                    if(ballYPosition > height)ballYPosition = height - racket.getRacketSize();
                    predictionMade = true;
                    predictionPoint = new Point((int)ballXPosition,(int)ballYPosition);
                    break;
                }
                ballSpeedX = -ballSpeedX;
                nextBallX = ballXPosition + deltaT * ballSpeedX;
            }
            ballXPosition = nextBallX;
            ballYPosition = nextBallY;
        }
    }

}