package gameengine.physics;

import java.awt.*;
import java.util.ArrayList;

public class CollisionEvent {

    public Collidable collider;
    public ArrayList<Collidable> collidedWith;

    public CollisionEvent(Collidable c1, ArrayList<Collidable> collidedWith) {
        this.collider = c1;
        this.collidedWith = collidedWith;
    }

    public void sendCollision(){
        collidedWith.forEach(collidable -> {
            collider.collide(collidable);
            collidable.collide(collider);
        });
    }
}
