package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Trigger {
    Rectangle triggerArea();
    boolean effect(GameObject g);
}
