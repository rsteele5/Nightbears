package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Collidable {

    /**
     * @return the collision box of the Collidable
     */
    Rectangle getCollisionBox();

    /**
     * @return  True: Collidable is a trigger <br>
     *          False: Collidable is not a trigger
     */
    default boolean isTrigger(){ return false; }

    /**
     * Executes on a collision with a {@link GameObject} that is collidable
     * @param g The {@link GameObject} that is colliding
     * @return  whether the collision was successfully handled or not.
     */
    default boolean collide(GameObject g) { return true; }

    void triggered(GameObject gameObject);
}
