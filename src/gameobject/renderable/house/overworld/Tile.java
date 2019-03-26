package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;


public class Tile extends RenderableObject {

    public Tile(String imagePath) {
        super(0, 0, imagePath, DrawLayer.Background);
    }

    @Override
    public void update() {

    }
}
