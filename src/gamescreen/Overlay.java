package gamescreen;

/**
 * Abstract implementation of an overlay. An overlay is a special type
 * of screen that is rendered on top of a GameScreen but does not prevent
 * update or draw calls on that GameScreen
 */
public abstract class Overlay extends GameScreen{

    protected GameScreen parentScreen;

    /**
     * Constructs an overlay.
     * @param screenManager The ScreenManager that manages this screen
     * @param parentScreen The screen that this overlay belongs to
     * @param name The name of the overlay
     * @param xPos The x position of the overlay
     * @param yPos The y position of the overlay
     * @param screenAlpha The alpha value of all the objects on the overlay
     */
    public Overlay(ScreenManager screenManager, GameScreen parentScreen, String name, int xPos, int yPos, float screenAlpha) {
        super(screenManager, "Overlay-" + name, false, xPos, yPos, screenAlpha);
        this.parentScreen = parentScreen;
    }
}
