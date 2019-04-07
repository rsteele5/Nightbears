package gameobject;

import gameengine.physics.PhysicsVector;

public class PositionVector {

    public static final PositionVector ORIGIN = new PositionVector(0,0);
    public  int x, y;

    public PositionVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int magnitude(){ return (int)Math.sqrt( x*x + y*y ); }

    public int distance(int x, int y){
        PositionVector distance = new PositionVector( this.x-x, this.y-y );
        return distance.magnitude();
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

    public PositionVector add(PositionVector vector) {
        return new PositionVector(x+vector.x,y+vector.y);
    }

    public PositionVector add(PhysicsVector pVector) {
        return new PositionVector((int)(x+Math.round(pVector.x)),(int)(y+Math.round(pVector.y)));
    }

    public PositionVector add(int x, int y) {
        return new PositionVector(this.x+x,this.y+y);
    }
}
