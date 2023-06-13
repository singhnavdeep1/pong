package model;

import sfx.SFX;

public class CustomCourt extends AbstractCourt {
	
	private RacketController[] players = new RacketController[2];
	private Racket[] rackets = new Racket[2];
	private int[] scores = new int[2];
	private final double[] initialSizes = {100.0, 100.0};
	private final double[] initialSpeeds = {300.0, 300.0};
	
	private Ball[] balls;
	
	private Double initCountdown = null;
	private Double countdown = null;
	
	private double[] accelCoefs = {1, 1};
	private double[] sizeChangeCoefs = {1, 1};
	
	private double[] ballAccelCoefs;
	
	private Integer maxScore;
	

	private CustomCourt(double width, double height, 
			RacketController playerA, RacketController playerB, 
			int ballAmnt) {
		super(width, height);
		rackets[0] = new Racket();
		if (playerB != null) rackets[1] = new Racket();
		players[0] = playerA;
		players[1] = playerB;
		balls = new Ball[(ballAmnt < 1) ? 1 : ballAmnt];
		for (int i = 0 ; i < ballAmnt ; i++) {
			balls[i] = new Ball();
		}
	}
	
	public CustomCourt(double width, double height, 
			RacketController playerA, RacketController playerB, int ballAmnt,
			double[] iSizes, double[] iSpeeds, Double cd, Integer maxS,
			double[] cSizes, double[] cCoefs, double[] aCoefs) {
		this(width, height, playerA, playerB, ballAmnt);
		
		for (int i = 0 ; i <= 1 ; i++) if (rackets[i] != null) {
			initialSizes[i] = iSizes[i];
			initialSpeeds[i] = iSpeeds[i];
			rackets[i].setRacketSize(iSizes[i]);
			rackets[i].setRacketSpeed(iSpeeds[i]);
		}
		
		sizeChangeCoefs = cSizes.clone();
		accelCoefs = cCoefs.clone();
		ballAccelCoefs = aCoefs.clone();
		maxScore = maxS;
		initCountdown = cd;
		reset();
	}
	
	public CustomCourt(CustomSettings settings) {
		super(settings.width, settings.height);
		rackets[0] = new Racket();
		if (settings.playerB != null) rackets[1] = new Racket();
		
		players[0] = settings.playerA;
		players[1] = settings.playerB;
		
		balls = new Ball[(settings.ballAmount < 1) ? 1 : settings.ballAmount];
		for (int i = 0 ; i < settings.ballAmount ; i++) {
			balls[i] = new Ball();
		}
		
		for (int i = 0 ; i <= 1 ; i++) if (rackets[i] != null) {
			initialSizes[i] = settings.initialRacketSizes[i];
			initialSpeeds[i] = settings.initialRacketSpeeds[i];
			rackets[i].setRacketSize(initialSizes[i]);
			rackets[i].setRacketSpeed(initialSpeeds[i]);
		}
		
		sizeChangeCoefs = settings.coefRacketSizes.clone();
		accelCoefs = settings.coefRacketAccel.clone();
		ballAccelCoefs = settings.coefBallAccel.clone();
		maxScore = settings.maxScore;
		initCountdown = settings.countdown;
		
		reset();
	}
	
	public Racket getRacket(int i) {
		return rackets[i];
	}
	
	public Ball getBall(int i) {
		return balls[i];
	}
	
	public int getBallAmnt() {
		return balls.length;
	}
	
	public Double getCountdown() {
		return countdown;
	}
	
	public int getScore(int i) {
		return scores[i];
	}

	@Override
	public void update(double deltaT) {
		
		for (int i = 0 ; i <= 1 ; i++) if (players[i] != null) {
			switch (players[i].getState()) {
			case GOING_UP:
                rackets[i].setRacketY(rackets[i].getRacketY()-rackets[i].getRacketSpeed()* deltaT);
                if (rackets[i].getRacketY()< 0.0) rackets[i].setRacketY(0.0);
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                rackets[i].setRacketY(rackets[i].getRacketY()+rackets[i].getRacketSpeed()* deltaT);
                if (rackets[i].getRacketY() + rackets[i].getRacketSize() > height) rackets[i].setRacketY(height - rackets[i].getRacketSize());
                break;
			}
		}
		
		if (countdown != null) countdown -= deltaT;
		if (gameEnd()) System.exit(0);
		if (updateBall(deltaT)) reset();
		if (countdown != null) if (countdown < 0) reset();

	}

	@Override
	protected boolean updateBall(double deltaT) {
		
		int index = 0;
		for (Ball b : balls) if (b != null) {
			// first, compute possible next position if nothing stands in the way
	        double nextBallX = b.getBallX() + deltaT * b.getBallSpeedX();
	        double nextBallY = b.getBallY() + deltaT * b.getBallSpeedY();
	        Ball nextBall = new Ball(nextBallX, nextBallY, b.getBallSpeedX(), b.getBallSpeedY());
	        // next, see if the ball would meet some obstacle
	        
	        if (nextBall.outOfVerticalCourtBounds()) {	//Si la balle touche une extremite verticale
	            b.setBallSpeedY(-(b.getBallSpeedY()));
	            nextBallY = b.getBallY() + deltaT * b.getBallSpeedY();
	            //new SFX("SFXDir" + SYS_SEP + "WallHit.mp3").play();
	        }
	        
	        if (rackets[1] != null) {
	
		        if (rackets[0].hasHitLeft(nextBall) || rackets[1].hasHitRight(nextBall)) { //Si la balle touche une raquette
		        	int hitId = (rackets[0].hasHitLeft(nextBall)) ? 0 : 1;	//Numéro de la raquette à avoir touché la balle
		            b.setBallSpeedX(-(b.getBallSpeedX())*ballAccelCoefs[index]);
		            b.setBallSpeedY((double) 10*(b.getBallY() - (rackets[hitId].getRacketY() + rackets[hitId].getRacketSize() / 2)));
		            rackets[0].setRacketSpeed(rackets[0].getRacketSpeed() * accelCoefs[0]);
		            rackets[1].setRacketSpeed(rackets[1].getRacketSpeed() * accelCoefs[1]);
		            nextBallX = b.getBallX() + deltaT * b.getBallSpeedX();
		            //new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();
		
		        } else if (nextBallX < 0) {	//Si la balle touche l'extremite gauche
		            scores[1]++; //incremente le score du joueur B si le joueur A (a gauche) manque la balle 
		            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
		            return true;
		        } else if (nextBallX > width) {//Si la balle touche l'extremite droite
		        	scores[0]++; //incremente le score du joueur A si le joueur B (a droite) manque la balle 
		            //new SFX("SFXDir" + SYS_SEP + "Score.mp3").play();
		            return true;
		        }
		        
	        } else {
	        	
	        	if (rackets[0].hasHitLeft(nextBall)) {	//La balle rebondit si elle est derrière la raquette
	        		b.setBallSpeedX(-(b.getBallSpeedX())*ballAccelCoefs[index]);
	        		b.setBallSpeedY((double) 10*(b.getBallY() - (rackets[0].getRacketY() + rackets[0].getRacketSize() / 2)));
	        		rackets[0].setRacketSpeed(rackets[0].getRacketSpeed() * accelCoefs[0]);
		            nextBallX = b.getBallX() + deltaT * b.getBallSpeedX();
		            scores[0]++;	//Nombre de balles touchées
		            //new SFX("SFXDir" + SYS_SEP + "RacketHit.mp3").play();
	        	} else if (nextBallX < 0) {
	        		scores[1]++; //Nombres de balles ratées
	        		//new SFX("SFXDir" + SYS_SEP + "Score.mp3");
	        		return true;
	        	} else if (nextBallX > width && b.getBallSpeedX() > 0) {	//Il arrive sans la vérification de direction que la balle reste bloquée
	        		b.setBallSpeedX(-(b.getBallSpeedX()));
	        		//new SFX("SFXDir" + SYS_SEP + "WallHit.mp3").play();
	        	}
	        	
	        }
	        if (b != null) {
	        	b.setBallX(nextBallX);
	        	b.setBallY(nextBallY);
	        }
	        index++;
		}
		
		return false;
	}

	@Override
	void reset() {
		//new SFX("SFXDir" + SYS_SEP + "MenuGameStart.mp3");
		rackets[0].setRacketX(0);
		if (rackets[1] != null) rackets[1].setRacketX(width);
		for (int i = 0 ; i <= 1 ; i++) if (rackets[i] != null) {
			rackets[i].setRacketY(height / 2);
			rackets[i].setRacketSize(rackets[i].getRacketSize() * sizeChangeCoefs[i]);
			rackets[i].setRacketSpeed(initialSpeeds[i]);
		}
		
		for (Ball b : balls) {
			
			switch(rng.nextInt(2)) {	//Permet de rendre la direction horizontale aleatoire
            case 0 : b.setBallSpeedX(-(rng.nextInt(100) + 150)); break;
            case 1 : b.setBallSpeedX(rng.nextInt(100) + 150); break;
            default : b.setBallSpeedX(200);
			}
			switch(rng.nextInt(2)) {
            case 0 : b.setBallSpeedY(-(rng.nextInt(100) + 150)); break;
            case 1 : b.setBallSpeedY(rng.nextInt(100) + 150); break;
            default : b.setBallSpeedX(200);
			}
			
			b.setBallX(width/2);
			b.setBallY(height/2);
			
		}
		
		if (initCountdown != null) countdown = initCountdown;
	}

	@Override
	boolean gameEnd() {
		for (int i = 0 ; i <= 1 ; i++) {
			if (maxScore != null && scores[i] > maxScore) return true;
			if (rackets[i] != null && rackets[i].getRacketSize() < 10) return true;
		}
		return false;
	}

}
