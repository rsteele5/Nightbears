package gameobject.renderable.house.sidescrolling;

import gameobject.container.TileGridContainer;

public abstract class BackgroundLayout {
    protected Integer[][] layout;
    protected TileGridContainer background;

    public BackgroundLayout() {
        constructLayout();
        constructBackground();
        constructBoundaries();
    }

    protected abstract void constructLayout();
    protected abstract void constructBackground();
    protected abstract void constructBoundaries();

    public abstract TileGridContainer getBackground();



    public Integer[][] getBackgroundArray() {
        return layout;
    }
}
