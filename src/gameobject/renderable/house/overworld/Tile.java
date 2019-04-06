package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import main.utilities.Debug;

import java.awt.*;
import java.io.Serializable;


public class Tile extends RenderableObject implements Serializable {

    public Tile(String imagePath) {
        super(0, 0, imagePath, DrawLayer.Background);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        Debug.log(false, "");
    }

    public boolean contains(int x, int y) {
        return getBoundingBox().contains(x,y);
    }
}
