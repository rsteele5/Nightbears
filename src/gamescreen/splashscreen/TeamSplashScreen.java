package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class TeamSplashScreen extends GameScreen {

    //region <Variables>
    private ImageContainer logo;
    private ImageContainer skipMsg;
    //endregion

    //region <Construction and Initialization>
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

        skipMsg = new ImageContainer(900,980, "/assets/text/TXT-SkipMsg.png", DrawLayer.Scenery);
        skipMsg.addToScreen(this, true);
    }


    //endregion

    //region <Update>
    @Override
    public void transitionOn() {
        float alpha = logo.getAlpha();
        if(alpha < 0.991f){
            logo.setAlpha(alpha + 0.008f);
        } else {
            currentState = ScreenState.Active;
        }
    }

    @Override
    public void transitionOff() {
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
    public void hiddenUpdate() {
        exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }

    //endregion

    //region <Support Functions>
    @Override
    public boolean handleClickEvent(int x, int y) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash screen");
        exiting = true;
        screenManager.addScreen(new TitleScreen(screenManager));
        return true;
    }
    //endregion
}
