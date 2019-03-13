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
    private ImageContainer cover;
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
        logo = new ImageContainer(0,0, "/assets/backgrounds/BG-TeamLogo.png", DrawLayer.Background);
        logo.addToScreen(this,true);

        cover = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        cover.setAlpha(1f);
        cover.addToScreen(this, true);

        skipMsg = new ImageContainer(575,660, "/assets/text/TXT-SkipMsg.png", DrawLayer.Scenery);
        skipMsg.addToScreen(this, true);

    }


    //endregion

    //region <Update>
    @Override
    public void transitionOn() {

        float alpha = cover.getAlpha();
        if(alpha > 0.008f) {
            cover.setAlpha(alpha - 0.008f);
            if(cover.getAlpha() <= 0.008f) {
                currentState = ScreenState.Active;
            }
        }
    }

    @Override
    public void transitionOff() {
        float alpha = cover.getAlpha();
        if(alpha < 1f){
            cover.setAlpha(alpha + 0.008f);
        } else {
            exiting = true;
            screenManager.addScreen(new TitleScreen(screenManager, "TitleScreen"));  //TODO: Add title splashscreen
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
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash splashscreen");
        exiting = true;
        screenManager.addScreen(new TitleScreen(screenManager,"TitleScreen"));
        return true;
    }
    //endregion
}
