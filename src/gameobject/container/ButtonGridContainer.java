package gameobject.container;

import gameobject.renderable.button.Button;
import gamescreen.GameScreen;

public class ButtonGridContainer extends GridContainer<Button> {
    public ButtonGridContainer(int rows, int cols, int itemWidth, int itemHeight, int xPos, int yPos, int padding) {
        super("Button", rows, cols, itemWidth, itemHeight, xPos, yPos, padding);
    }

    @Override
    protected void setContentPosition(Button content, int newX, int newY) {
        content.setPosition(newX, newY);
    }

    @Override
    protected void setContentActive(Button content, GameScreen screen) {
        content.setActive(screen);
    }

    @Override
    protected void setContentInactive(Button content, GameScreen screen) {
        content.setInactive(screen);
    }

    @Override
    protected void addContentToScreen(Button content, GameScreen screen, boolean isActive) {
        content.addToScreen(screen, isActive);
    }

    @Override
    public void update() {

    }
}
