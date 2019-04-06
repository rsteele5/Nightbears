package gameengine.physics;

public interface Kinematic {

   PhysicsVector getVelocity();
   void setVelocity(PhysicsVector pv);
   double getSpeed();
   void move();
   void move(PhysicsVector pV);
}
