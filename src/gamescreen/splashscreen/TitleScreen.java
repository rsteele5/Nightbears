package gamescreen.splashscreen;

import gameengine.audio.BackgroundAudio;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gamescreen.mainmenu.MainMenuScreen;

public class TitleScreen extends GameScreen {
    //region <Variables>
    ImageContainer moonImg;
    ImageContainer titleImg;
    ImageContainer skipMsg;
    //endregion
    private boolean musicStart = false;
    //region <Construction and Initialization>
    public TitleScreen(ScreenManager screenManager, String name) {
        super(screenManager,name);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        ImageContainer image;

        moonImg = new ImageContainer(585,-1058, "/assets/backgrounds/BG-Moon.png", DrawLayer.Scenery);
        moonImg.addToScreen(this,true);

        titleImg = new ImageContainer(626,174, "/assets/backgrounds/BG-Title.png", DrawLayer.Scenery);
        titleImg.addToScreen(this,true);

        skipMsg = new ImageContainer(900,980, "/assets/text/TXT-SkipMsg.png", DrawLayer.Scenery);
        skipMsg.addToScreen(this,true);
    }

    //endregion

    //region <Update>
    @Override
    public void transitionOn() {
        if(!musicStart) {
            musicStart = true;
            BackgroundAudio.play(this.getClass().getClassLoader().getResource("assets/music/title.wav"));
        }
        if(moonImg.getY() < -248)
            moonImg.setY(moonImg.getY() + 2);
        else
            currentState = ScreenState.Active;
    }

    @Override
    public void transitionOff() {
        screenManager.addScreen(new MainMenuScreen(screenManager));
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
        currentState = ScreenState.TransitionOff;
        return true;
    }
    //endregion
}
