package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Interactable {
    /***
     Returns the collision edges represented as a Rectangle.
     */
    default Rectangle collision(){return null;}

    /***
     Performs an action on the passed game object when it intersects collision().
     If true is returned, delete upon interaction. Otherwise don't.
     */
    default boolean action(GameObject g){return false;}
}
