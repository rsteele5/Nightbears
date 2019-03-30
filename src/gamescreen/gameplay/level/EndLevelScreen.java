package gamescreen.gameplay.level;

import gameobject.GameObject;
import gameobject.container.ButtonGridContainer;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.mainmenu.PlayerCountScreen;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class EndLevelScreen extends GameScreen {

    private final int X_START = -256;
    private final int Y_START = 400;
    private final int BUTTON_HEIGHT = 96;
    private final int Y_BUFFER = 48;


    public EndLevelScreen(ScreenManager screenManager, boolean isExclusive) {
        super(screenManager, "EndLevelScreen", isExclusive, 0f);
    }

    @Override
    protected void initializeScreen() {
        ImageContainer imageContainer;
        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-LevelComplete.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create buttons
        ButtonGridContainer buttonLayout = new ButtonGridContainer(4,1, 256, 96,
                X_START, Y_START, Y_BUFFER);
        Button continueBtn = (new Button(0, 0,
                "/assets/buttons/Button-Continue.png",
                "/assets/buttons/Button-ContinuePressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Continue");
                    setScreenState(ScreenState.TransitionOff);
                }));
        buttonLayout.addAt(continueBtn, 0, 0);

        buttonLayout.addToScreen(this, true);
    }

    @Override
    protected void transitionOn() {

        if(screenAlpha < 0.9f) {
            screenAlpha += 0.05f;
            setScreenAlpha(screenAlpha);
        }

        int xpos = ((GameObject) clickables.get(0)).getX();
        if(xpos < 64) {
            xpos += 20;
            for(Clickable clickable: clickables){
                ((GameObject) clickable).setX(xpos);
            }
        }

        if(screenAlpha > 0.9f && xpos >= 64){
            setScreenAlpha(1f);
            currentState = ScreenState.Active;
        }
    }
}
