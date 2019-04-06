package gameengine.physics;

import gameobject.GameObject;

import java.awt.Rectangle;

public interface Interactable {
    Rectangle getRequestArea();
    boolean action(GameObject g);
}
