package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class CollisionManager {


    public ArrayList<Collidable> detectCollisions(ArrayList<Collidable> collidables){
        collidables.forEach(c1 -> {
            if(!c1.isTrigger()) {
                Rectangle c1Box = c1.getCollisionBox();
                collidables.forEach(c2 -> {
                    if(c1 != c2) {
                        Rectangle intersect = c1Box.intersection(c2.getCollisionBox());
                        if (!intersect.isEmpty()) {
                            Rectangle c2Box = c2.getCollisionBox();
                            if (c2.isTrigger())
                                c2.triggered((GameObject) c1);
                            else {
                                //TODO: Send Collision Event
                                int signX = c1Box.getX() < c2Box.getX() + c2Box.width / 2 ? -1 : 1;
                                int signY = c1Box.getY() < c2Box.getY() + c2Box.height / 2 ? -1 : 1;

                            }
                        }
                    }
                });
            }
        });
        for (int i2 = 0; i2 < indices; i2++) {
            if (i2 == i1) continue;
            GameObject obj2 = (GameObject) objects.get(i2);
            Kinematic kObj2 = objects.get(i2);

            //Interactable
            if(iObj1 != null) {
                if (obj2 instanceof Interactable) {
                    Interactable iObj2 = (Interactable) obj2;
                    if (iObj1.getRequestArea().intersects(iObj2.getRequestArea()) && iObj1.isRequesting()) {
                        iObj2.action(obj1);
                        iObj1.setRequesting(false);
                    }
                }
            }

            if (kObj1.getHitbox().intersects(kObj2.getHitbox())) {

                kObj1.setAcceleration(new PhysicsVector(1, 1));
                if (!kObj2.isStatic())
                    kObj2.setAcceleration(new PhysicsVector(1, 1));

                Rectangle intersect = kObj1.getHitbox().intersection(kObj2.getHitbox());
                int signX = kObj1.getX() < obj2.getX() + kObj2.getHitbox().width / 2 ? -1 : 1;
                int signY = kObj1.getY() < obj2.getY() + kObj2.getHitbox().height / 2 ? -1 : 1;

                if (intersect.height > .5 && intersect.width > .5) {
                    if (kObj2.isStatic()) {
                        if(intersect.width > intersect.height)
                            if(signY == -1 && obj1 instanceof Player)
                                ((Player) obj1).grounded = true;

                        if (intersect.height < intersect.width)
                            obj1.setY(obj1.getY() + intersect.height * signY);

                        else
                            obj1.setX(obj1.getX() + intersect.width * signX);

                    } else {
                        if(intersect.width > intersect.height)
                            if(signY == -1 && obj1 instanceof Player) ((Player) obj1).grounded = true;

                        if (intersect.height < intersect.width) {
                            obj1.setY(obj1.getY() + (intersect.height / 2 + 1) * signY);
                            obj2.setY(obj2.getY() - (intersect.height / 2 + 1) * signY);

                        } else {
                            obj1.setX(obj1.getX() + (intersect.width / 2 + 1) * signX);
                            obj2.setX(obj2.getX() - (intersect.width / 2 + 1) * signX);

                        }
                    }
                }
            }

        }
        if(iObj1 != null) iObj1.setRequesting(false);
    }
}
