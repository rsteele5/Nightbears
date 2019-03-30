package gameengine.physics;

import gameobject.renderable.RenderableObject;

import java.awt.*;

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
   Rectangle getHitbox();
   default void move(PhysicsVector move){
      if (this instanceof RenderableObject){
         ((RenderableObject) this).setX(((RenderableObject) this).getX() + (int)move.x);
         ((RenderableObject) this).setY(((RenderableObject) this).getY() + (int)move.y);
      }
   }
   default void move(){
      if (this instanceof RenderableObject){
         ((RenderableObject) this).setX(((RenderableObject) this).getX() + (int)getVelocity().x);
         ((RenderableObject) this).setY(((RenderableObject) this).getY() + (int)getVelocity().y);
      }
   }
   default int getX(){
      return ((RenderableObject) this).getX();
   }
   default int getY(){
      return ((RenderableObject) this).getY();
   }
   default void setX(int x){
      ((RenderableObject) this).setX(x);
   }
   default void setY(int y){
      ((RenderableObject) this).setY(y);
   }
}
