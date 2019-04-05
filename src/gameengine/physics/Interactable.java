package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Interactable {
    Rectangle getRequestArea();
    boolean action(GameObject g);
}
