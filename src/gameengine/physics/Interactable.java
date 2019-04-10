package gameengine.physics;

import gameobject.GameObject;

import java.awt.*;

public interface Interactable {
    Rectangle getRequestArea();
    void setRequesting(boolean isRequesting);
    boolean isRequesting();
    boolean action(GameObject g);
}
