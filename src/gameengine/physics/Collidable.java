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

    default boolean collide(Collidable c2){ return true;}

    default boolean triggered(GameObject gameObject){ return false;}
}
