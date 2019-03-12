package gamescreen.splashscreen;

import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class IntroCutScene extends GameScreen {

    public IntroCutScene(ScreenManager screenManager) {
        super(screenManager, "IntroCutScene");
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

        DialogBox diagBox = new DialogBox(320,180, 640, 360, text,
                new Font("NoScary", Font.PLAIN, 40), Color.WHITE);
        diagBox.addToScreen(this, true);

        ImageContainer skipMsg = new ImageContainer(575,660, "/assets/text/TXT-SkipMsg.png", DrawLayer.Scenery);
        skipMsg.addToScreen(this, true);
    }

    @Override
    public boolean handleClickEvent(int x, int y) {
        setScreenState(ScreenState.TransitionOff);
        return true;
    }

    @Override
    protected void transitionOff() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash intro cut scene");
        exiting = true;
        screenManager.addScreen(new OverworldScreen(screenManager));
    }
}
