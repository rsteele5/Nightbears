package gameengine.physics;

public interface Kinematic {
   /***
    True: Object does not change x and y position.
    False: Object can move.
    */
   default boolean isStatic(){return false;}
   PhysicsVector getVelocity();
   void setVelocity(PhysicsVector pv);
   PhysicsVector getAcceleration();
   void setAcceleration(PhysicsVector pv);

   default void move(PhysicsVector move){ translate((int)move.x, (int)move.y); }
   default void move(){ translate((int)getVelocity().x, (int)getVelocity().y); }
   int getX();
   int getY();
   void setPosition(int x, int y);
   void translate(int x, int y);
}
