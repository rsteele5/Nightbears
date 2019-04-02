package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.player.Player;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionManager {


    public void procressInteractions(Player player, ArrayList<Interactable> interactables){
        //Interactable
        interactables.forEach(interactable -> {
            if (player.getRequestArea().intersects(interactable.getRequestArea()) && player.isRequesting()) {
                interactable.action(player);
                player.setRequesting(false);
            }
        });
        player.setRequesting(false);
    }

    public ArrayList<CollisionEvent> detectCollisions(ArrayList<Collidable> collidables){
        ArrayList<CollisionEvent> collisionEvents = new ArrayList<>();

        for(int i = 0; i < collidables.size(); i++){
            Collidable collider = collidables.get(i);
            ArrayList<Collidable> collidedWith = new ArrayList<>();
            ArrayList<Rectangle> intersections = new ArrayList<>();
            for(int j = i+1; j < collidables.size(); j++){
                Collidable c2 = collidables.get(j);
                Rectangle intersection = collider.getCollisionBox().intersection(c2.getCollisionBox());
                if(!intersection.isEmpty()) {
                    if(collider.isTrigger()){
                        if(!c2.isTrigger())
                            collider.triggered((GameObject) c2);
                    } else if(c2.isTrigger())
                        c2.triggered((GameObject) collider);
                    else {
                        collidedWith.add(c2);
                        intersections. add(intersection);
                    }
                }
            }
            if(!collidedWith.isEmpty())
                collisionEvents.add(new CollisionEvent(collider,collidedWith,intersections));
        }
        return collisionEvents;
    }
}
