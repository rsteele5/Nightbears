package gameobject.renderable.house.sidescrolling;

import gameobject.container.TileGridContainer;

public abstract class BackgroundLayout {
    protected Integer[][] layout;

    public BackgroundLayout() {
        layout = constructLayout();
    }

    protected abstract Integer[][] constructLayout();

    public abstract TileGridContainer getBackground();
}
