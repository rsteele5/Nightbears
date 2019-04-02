package gameengine.physics;

public class PhysicsVector{
    public static final PhysicsVector ZERO = new PhysicsVector(0,0);
    public  double x, y, magnitude;

    public PhysicsVector(double x, double y) {
        this.x = x;
        this.y = y;
        this.magnitude = Math.sqrt( x*x + y*y );
    }

//    public double getX(){ return x; }
//    public double getY(){ return y; }
//    public double getMag(){ return magnitude; }

    public double direction(){
        double direction;
        if(x < 0){
            direction = Math.atan(y/x)+(Math.PI);
        } else {
            direction = Math.atan(y/x);
        }
        return direction;
    }

    public PhysicsVector normalize(){
        PhysicsVector newVector = new PhysicsVector(0,0);
        if (magnitude != 0) {
            newVector.x = x/magnitude;
            newVector.y = y/magnitude;
        } return newVector;
    }

    public PhysicsVector add(PhysicsVector vector) {
        return new PhysicsVector(x+vector.x,y+vector.y);
    }

    public PhysicsVector mult(double scalar) {
        return new PhysicsVector(this.x * scalar, this.y * scalar);
    }

    public PhysicsVector mult(PhysicsVector pv) {
        return new PhysicsVector(this.x * pv.x, this.y * pv.y);
    }
}
