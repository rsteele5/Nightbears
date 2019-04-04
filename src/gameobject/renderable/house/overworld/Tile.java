package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;

import java.io.Serializable;


public class Tile extends RenderableObject implements Serializable {

    public Tile(String imagePath) {
        super(0, 0, imagePath, DrawLayer.Background);
    }

    @Override
    public void update() {

    }
}
