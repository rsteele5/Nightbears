package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

/**
 * This screen is contains our teams logo and slowly fades
 * into view before fading out of view. Once the transition
 * is complete the title screen should be displayed.
 */
public class TeamSplashScreen extends GameScreen {

    private ImageContainer logo;
    //endregion

    public TeamSplashScreen(ScreenManager screenManager) {
        super(screenManager, "TeamSplashScreen");
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        logo = new ImageContainer(670,420, "/assets/backgrounds/BG-TeamLogo.png", DrawLayer.Background);
        logo.addToScreen(this,true);
        logo.setAlpha(0);
    }

    @Override
    protected void transitionOn() {
        float alpha = logo.getAlpha();
        if(alpha < 0.991f){
            logo.setAlpha(alpha + 0.008f);
        } else {
            currentState = ScreenState.Active;
        }
    }

    @Override
    protected void transitionOff() {
        float alpha = logo.getAlpha();
        if(alpha > 0.008f) {
            logo.setAlpha(alpha - 0.008f);
            if(logo.getAlpha() <= 0.008f) {
                exiting = true;
                screenManager.addScreen(new TitleScreen(screenManager));  //TODO: Add title splashscreen
            }
        }
    }

    @Override
    protected void hiddenUpdate() {
        exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
        exiting = true;
        screenManager.addScreen(new TitleScreen(screenManager));
        return true;
    }

    @Override
    public boolean handleClickEvent(int x, int y) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
        exiting = true;
        screenManager.addScreen(new TitleScreen(screenManager));
        return true;
    }
}
