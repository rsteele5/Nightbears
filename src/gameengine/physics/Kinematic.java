package gameengine.physics;

public interface Kinematic {

   PhysicsVector getVelocity();
   void setVelocity(PhysicsVector pv);
   PhysicsVector getAcceleration();
   void setAcceleration(PhysicsVector pv);

   default void move(PhysicsVector move){
      translate((int)move.x, (int)move.y);
   }
   default void move(){
      translate((int)getVelocity().x, (int)getVelocity().y);
   }

   int getX();
   int getY();
   void translate(int x, int y);
}
