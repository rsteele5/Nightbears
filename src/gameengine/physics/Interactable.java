package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Interactable {
    Rectangle getRequestArea();
    //boolean requ
    boolean action(GameObject g);
}
