package gamescreen.splashscreen;

import gameengine.audio.BackgroundAudio;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gamescreen.mainmenu.MainMenuScreen;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class TitleScreen extends GameScreen {

    private ImageContainer moonImg;
    private ImageContainer titleImg;
    private boolean musicStart = false;

    public TitleScreen(ScreenManager screenManager) {
        super(screenManager,"TitleScreen", 1f);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        ImageContainer image;

        moonImg = new ImageContainer(585,-1058, "/assets/backgrounds/BG-Moon.png", DrawLayer.Scenery);
        moonImg.addToScreen(this,true);

        titleImg = new ImageContainer(625,165, "/assets/backgrounds/BG-Title.png", DrawLayer.Scenery);
        titleImg.addToScreen(this,true);
    }

    @Override
    public void transitionOn() {
        if(!musicStart) {
            musicStart = true;
            BackgroundAudio.play(this.getClass().getClassLoader().getResource("assets/music/title.wav"));
        }
        if(moonImg.getY() < -248 * gameData.getGraphicsSettings().getScaleFactor())
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

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        currentState = ScreenState.TransitionOff;
        return true;
    }
}
