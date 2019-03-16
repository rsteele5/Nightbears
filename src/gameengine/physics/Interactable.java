package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Interactable {
    Rectangle hitbox();
    boolean action(GameObject g);
}
