package gameobject.renderable.house.sidescrolling;

import gameengine.physics.Interactable;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import main.utilities.Debug;

import java.awt.*;

public class Door extends RenderableObject implements Interactable {

    public Door(int x, int y, String imagePath) {
        super(x,y,imagePath, DrawLayer.Entity);
    }


    @Override
    public void update() {

    }

    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public boolean action(GameObject g) {
        Debug.log(true, "In the door!");
        return false;
    }
}
