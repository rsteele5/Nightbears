package gamescreen.splashscreen;

import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldScreen;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.Font;
import java.awt.Color;


/**
 * The intro IntroCutScene adds a bit of narration before our game
 * starts. The screen is skippable if the gamer so wishes. After the
 * IntroCutScene is done the overworld is displayed.
 */
public class IntroCutScene extends GameScreen {

    public IntroCutScene(ScreenManager screenManager) {
        super(screenManager, "IntroCutScene",1f);
    }

    private final String text = "Arise!...\n\n" +
            "Your child needs your help!\n\n" +
            "Once again the nightmarish horrors seek to destroy that which you hold dear!\n" +
            "Destroy them like so many countless times before\n\n" +
            "Arise!. . . to arms brave teddy! TO ARMS!";

    @Override
    protected void initializeScreen() {

        ImageContainer cover = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        cover.setAlpha(1f);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(480,90, 960, 540, text,
                new Font("NoScary", Font.PLAIN, 56), Color.WHITE, true);
        diagBox.addToScreen(this, true);
    }

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash intro cut scene");
        setScreenState(ScreenState.TransitionOff);
        return true;
    }

//    @Override
//    public boolean handleClickEvent(int x, int y) {
//        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash intro cut scene");
//        setScreenState(ScreenState.TransitionOff);
//        return true;
//    }

    @Override
    protected void transitionOn() { this.setScreenState(ScreenState.Active); }

    @Override
    protected void transitionOff() {
        exiting = true;
        screenManager.addScreen(new OverworldScreen(screenManager, true));
    }
}
