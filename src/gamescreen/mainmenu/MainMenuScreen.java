package gamescreen.mainmenu;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.mainmenu.options.OptionScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class MainMenuScreen extends GameScreen {

    //region <Variables>
    private final int X_START = 64;
    private final int Y_START = 400;
    private final int BUTTON_HEIGHT = 96;
    private final int Y_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public MainMenuScreen(ScreenManager screenManager) {
        super(screenManager, "MainMenuScreen", 1f);
    }

    @Override
    protected void initializeScreen() {

        //Create Background on layer 0
        ImageContainer background = new ImageContainer(0, 0, "/assets/backgrounds/BG-MainMenu.png", DrawLayer.Background);
        background.addToScreen(this,true);

        //Create buttons
        Button newGameButton = (new Button(X_START, Y_START,
                "/assets/buttons/Button-NewGame.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - New Game");
                    screenManager.addScreen(new PlayerCountScreen(screenManager));
                }));
        newGameButton.addToScreen(this,true);

        Button optionsButton = (new Button(X_START, Y_START + BUTTON_HEIGHT + Y_BUFFER,
                "/assets/buttons/Button-Options.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
                }));
        optionsButton.addToScreen(this,true);

        Button devModeButton = (new Button(X_START, Y_START + 2 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-Dev.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - DevMode");
                    screenManager.addScreen(new DevScreen(screenManager));
                }));
        devModeButton.addToScreen(this,true);

        Button exitButton = (new Button(X_START, Y_START + 3 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-Dev.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit");
                    gameData.save();
                    System.exit(0);
                    screenManager.addScreen(new DevScreen(screenManager));
                }));
        exitButton.addToScreen(this,true);
    }
    //endregion


    @Override
    protected void transitionOff() {
        exiting = true;
    }
}
