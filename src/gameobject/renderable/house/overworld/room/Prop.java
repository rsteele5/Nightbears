package gameobject.renderable.house.overworld.room;

import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;

public class Prop extends CollidableRenderable {

    public Prop(int x, int y, String imagePath) {
        super(x, y, imagePath, DrawLayer.Prop, 1f);
    }

    @Override
    public void update() {

    }
}
