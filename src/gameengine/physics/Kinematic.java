package gameengine.physics;

import java.awt.*;

public interface Kinematic {
   boolean isStatic();
   PhysicsVector getVelocity();
   void setVelocity(PhysicsVector pv);
   PhysicsVector getAcceleration();
   void setAcceleration(PhysicsVector pv);
   Rectangle getHitbox();
}
