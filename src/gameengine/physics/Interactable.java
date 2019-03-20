package gameengine.physics;

import gameobject.GameObject;
import org.w3c.dom.css.Rect;

import java.awt.*;

public interface Interactable {
    /***
     Returns the collision edges represented as a Rectangle.
     */
    default Rectangle collision(){return null;}

    /***
     Performs an action on the passed game object when it intersects collision().
     Returns true or false depending on the user defined execution.
     */
    default boolean action(GameObject g){return false;}
}
