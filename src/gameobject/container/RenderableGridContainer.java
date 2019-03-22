package gameobject.container;

import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;

public class RenderableGridContainer extends GridContainer<RenderableObject> {

    public RenderableGridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth, int itemHeight) {
        super(parentScreen, rows, cols, itemWidth, itemHeight, 0, 0, 5);
    }

    public RenderableGridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth , int itemHeight, int xPos, int yPos) {
        super(parentScreen, rows, cols, itemWidth, itemHeight, xPos, yPos, 5);
    }

    public RenderableGridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth , int itemHeight, int xPos, int yPos, int padding) {
        super(parentScreen, rows, cols, itemWidth, itemHeight, xPos, yPos, padding);
    }

    @Override
    protected void setContentPosition(RenderableObject content, int newX, int newY) {
        content.setPosition(newX, newY);
    }
    @Override
    protected void setContentActive(RenderableObject content, GameScreen screen) {
        content.setActive(screen);
    }
    @Override
    protected void setContentInactive(RenderableObject content, GameScreen screen) {
        content.setInactive(screen);
    }
    @Override
    protected void addContentToScreen(RenderableObject content, GameScreen screen, boolean isActive) {
        content.addToScreen(screen, isActive);
    }
    @Override
    public void update() {}
}
