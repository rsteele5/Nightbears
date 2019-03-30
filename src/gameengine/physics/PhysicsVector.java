package gameengine.physics;

public class PhysicsVector{
    public static final PhysicsVector ZERO = new PhysicsVector(0,0);
    public double x, y;

    public PhysicsVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double direction(){
        double direction;
        if(x < 0){
            direction = Math.atan(y/x)+(Math.PI);
        } else {
            direction = Math.atan(y/x);
        }
        return direction;
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
