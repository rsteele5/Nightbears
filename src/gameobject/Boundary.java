package gameobject;

import java.io.Serializable;

public class Boundary extends CollidableObject implements Serializable {

    public Boundary(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() { }
}
