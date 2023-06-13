package model;

public class Acceleration extends Court{
	
    public Acceleration(RacketController playerA, RacketController playerB, double width, double height) {
    	super(playerA, playerB, width, height);
        reset();
    }

    @Override
    public boolean updateBall(double deltaT){
        double nextBallX = this.ball.getBallX() + deltaT * this.ball.getBallSpeedX();
        double nextBallY = this.ball.getBallY() + deltaT * this.ball.getBallSpeedY();
        Ball nextBall = new Ball(nextBallX, nextBallY, this.ball.getBallSpeedX(), this.ball.getBallSpeedY());
        
        if (nextBall.outOfVerticalCourtBounds()){
            ball.setBallSpeedY(-(ball.getBallSpeedY()));
            nextBallY = ball.getBallY() + deltaT * ball.getBallSpeedY();

        }
        if (racketA.hasHitLeft(nextBall) || racketB.hasHitRight(nextBall)) { //Si la balle touche une raquette
            ball.setBallSpeedX(-(ball.getBallSpeedX()));
            nextBallX = ball.getBallX() + deltaT * ball.getBallSpeedX();
            //new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();

        
            this.ball.setBallSpeedX(this.ball.getBallSpeedX()*(1.2));
            this.ball.setBallSpeedY(this.ball.getBallSpeedY()*(1.2));
            this.racketA.setRacketSpeed(this.racketA.getRacketSpeed()*(1.1));
            this.racketB.setRacketSpeed(this.racketB.getRacketSpeed()*(1.1));

        } else if (nextBallX < 0) {	
            scoreB++; 
            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
            return true;
        } else if (nextBallX > width) {
            scoreA++; //incremente le score du joueur A si le joueur B (a droite) manque la balle 
            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
            return true;
        }
        this.ball.setBallX(nextBallX);
        this.ball.setBallY(nextBallY);
        return false;
    }
    @Override
    boolean gameEnd() {
    	return scoreA > 2 || scoreB > 2;
    }
}