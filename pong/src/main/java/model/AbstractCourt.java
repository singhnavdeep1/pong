package model;

import gui.GameView;

import java.util.Random;

abstract public class AbstractCourt {
	
	public class Racket {
		
	    private double racketSpeed = 300.0; // m/s
	    private double racketSize = 100.0; // m
	    private double racketX;
	    private double racketY; // m ; position verticale
	    
	    public Racket() {}
	    
	    public Racket(double racketx, double rackety) {
	    	this.racketX = racketx;
	        this.racketY = rackety;
	    }

	    public double getRacketSize() {
	        return racketSize;
	    }
	    
	    public void setRacketSize(double s){
	        this.racketSize=s;
	    }

	    public double getRacketY() {
	        return racketY;
	    }
	    
	    public double getRacketX() {
	        return racketX;
	    }

	    public double getRacketSpeed(){
	        return racketSpeed;
	    }
	    
	    public void setRacketSpeed(double s) {
	    	racketSpeed = s;
	    }

	    public void setRacketY(double h){
	        this.racketY = h;
	    }
	    
	    public void setRacketX(double w){
	        this.racketX = w;
	    }
	    
	    public boolean hasHitLeft(Ball b) {
	    	boolean res = false;
	    	if (b.getBallX() < this.racketX) {
	    		if (b.getBallY() >= this.racketY && b.getBallY() <= this.racketY + this.racketSize) res = true;
	    	}
	    	return res;
	    }
	    
	    public boolean hasHitRight(Ball b) {
	    	boolean res = false;
	    	if (b.getBallX() > this.racketX) {
	    		if (b.getBallY() >= this.racketY && b.getBallY() <= this.racketY + this.racketSize) res = true;
	    	}
	    	return res;
	    }

		public boolean isTheBallUpsideTheCenter(Ball b){
			return (b.getBallY() <= racketY + 0.5*racketSize);
		}
	}
	
	public class Ball {
		
	    private final double ballRadius = 10.0; // m
	    private double ballX, ballY; // m
	    private double ballSpeedX, ballSpeedY; // m
	    
	    public Ball() {}
	    
	    public Ball(double x, double y, double sx, double sy) {
	    	this.ballX = x;
	    	this.ballY = y;
	    	this.ballSpeedX = sx;
	    	this.ballSpeedY = sy;
	    }
	    
	    public double getBallX() {
	        return this.ballX;
	    }

	    public double getBallY() {
	        return this.ballY;
	    }

	    public void setBallX(double x){
	        this.ballX=x;
	    }
	    public void setBallY(double y){
	        this.ballY=y;
	    }
	    
	    public double getBallRadius() {
	        return this.ballRadius;
	    }

	    public double getBallSpeedX(){
	        return this.ballSpeedX;
	    }

	    public double getBallSpeedY(){
	        return this.ballSpeedY;
	    }

	    public void setBallSpeedX(double sx){
	        this.ballSpeedX=sx;
	    }

	    public void setBallSpeedY(double sy){
	        this.ballSpeedY=sy;
	    }
	    
	    public boolean outOfVerticalCourtBounds() {
	    	if (ballY < 0 || ballY > AbstractCourt.this.height) return true;
	    	return false;
	    }
	    
	    public Ball nextBall(double deltaT) {
	    	return new Ball(this.ballSpeedX * deltaT, this.ballSpeedY * deltaT, this.ballSpeedX, this.ballSpeedY);
	    }
	    
	}

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


		public void repositionnement(Court court){
			if(dansLaxe()){
				glisser();
			}else{
				glisser();
			}
		}

		public boolean dansLaxe(){
			return(ball.getBallY() > racket.getRacketY() || ball.getBallY() < racket.getRacketY() + racket.getRacketSize());
			//si la racket est superposé à la balle par rapport à l'axe Y
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



	}

	//static variables
	static String SYS_SEP = System.getProperty("file.separator");
	
	protected Random rng = new Random();
    // instance parameters
    protected final double width, height; // m   


    public AbstractCourt(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    abstract public void update(double deltaT);		// Met a jour la position des raquettes en fonction de leur etat actuel

    /**
     * @return true if a player lost
     */
    abstract protected boolean updateBall(double deltaT);		// Met a jour la position de la balle


    abstract void reset();
    
    abstract boolean gameEnd();
}
