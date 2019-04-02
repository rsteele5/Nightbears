package gameengine.physics;

import java.awt.*;
import java.util.ArrayList;

public class CollisionEvent {

    public Collidable collider;
    public ArrayList<Collidable> collidedWith;
    public ArrayList<Rectangle> intersections;

    public CollisionEvent(Collidable c1, ArrayList<Collidable> collidedWith, ArrayList<Rectangle> intersections) {
        this.collider = c1;
        this.collidedWith = collidedWith;
        this.intersections = intersections;
    }

    public void sendCollision(){
        collidedWith.forEach(collidable -> {
            collider.collide(collidable);
            collidable.collide(collider);
        });
    }
}
