package model;

public class Ball{

    private final double ballRadius = 10.0; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private AbstractCourt parentCourt;

    public Ball(AbstractCourt parent) {
        this.parentCourt = parent;
    }

    public Ball(AbstractCourt parent, double x, double y, double sx, double sy) {
        this(parent);
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
        if (ballY < 0 || ballY > parentCourt.height) return true;
        return false;
    }

    public Ball nextBall(double deltaT) {
        return new Ball(this.parentCourt, this.ballSpeedX * deltaT, this.ballSpeedY * deltaT, this.ballSpeedX, this.ballSpeedY);
    }
    public boolean directionBall(){
        return (ballSpeedX >= 0); //true si la balle va vers le haut, false sinon.
    }

    public int directionBallInt(){
        if(directionBall())return 1;
        return -1;
    }



}