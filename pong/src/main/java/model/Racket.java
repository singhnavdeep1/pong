package model;
public class Racket {

    private final double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private double racketX;
    private double racketY; // m ; position verticale
    private AbstractCourt parentCourt;

    private IA IA;

    public Racket(AbstractCourt parent) {
        this.parentCourt = parent;
    }

    public Racket(AbstractCourt parent, double racketx, double rackety) {
        this(parent);
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

    public void takeOver(){

    }

    public boolean isTheBallUpsideTheCenter(Ball b){
        return (b.getBallY() <= racketY + 0.5*racketSize);
    }
}
