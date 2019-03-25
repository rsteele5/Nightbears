package gameobject.container;

import gameobject.renderable.house.overworld.Tile;
import gamescreen.GameScreen;

public class TileGridContainer extends GridContainer<Tile> {

    public TileGridContainer(int rows, int cols, int itemWidth, int itemHeight, int xPos, int yPos) {
        super(rows, cols, itemWidth, itemHeight, xPos, yPos, 0);
    }

    @Override
    protected void setContentPosition(Tile content, int newX, int newY) {
        content.setPosition(newX, newY);
    }

    @Override
    protected void setContentActive(Tile content, GameScreen screen) {
        content.setActive(screen);
    }

    @Override
    protected void setContentInactive(Tile content, GameScreen screen) {
        content.setInactive(screen);
    }

    @Override
    protected void addContentToScreen(Tile content, GameScreen screen, boolean isActive) {
        content.addToScreen(screen,isActive);
    }

    @Override
    public void update() {

    }
}
