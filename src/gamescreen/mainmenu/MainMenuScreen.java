package gamescreen.mainmenu;

import gameobject.GameObject;
import gameobject.container.ButtonGridContainer;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.mainmenu.options.OptionScreen;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class MainMenuScreen extends GameScreen {

    //region <Variables>
    private final int X_START = -150;
    private final int Y_START = 150;
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
        ButtonGridContainer buttonLayout = new ButtonGridContainer(4,1, 256, 96,
                                                                    X_START, Y_START, Y_BUFFER);
        Button newGameButton = (new Button(0, 0,
                "/assets/buttons/Button-NewGame.png",
                "/assets/buttons/Button-NewGamePressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - New Game");
                    screenManager.addScreen(new PlayerCountScreen(screenManager));
                }));
        buttonLayout.addAt(newGameButton, 0, 0);

        Button optionsButton = (new Button(0, 0,
                "/assets/buttons/Button-Options.png",
                "/assets/buttons/Button-OptionsPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
                }));
        buttonLayout.addAt(optionsButton, 1, 0);

        Button devModeButton = (new Button(0, 0,
                "/assets/buttons/Button-Dev.png",
                "/assets/buttons/Button-DevPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - DevMode");
                    screenManager.addScreen(new DevScreen(screenManager));
                }));
        buttonLayout.addAt(devModeButton, 2, 0);

        Button heroHallButton = (new Button(X_START, Y_START + 3 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-HeroHall.png",
                "/assets/buttons/Button-HeroHallPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Hall of Heroes");
                    screenManager.addScreen(new HeroHallScreen(screenManager));
                }));
        heroHallButton.addToScreen(this,true);

        Button fallenHallButton = (new Button(X_START, Y_START + 4 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-FallenHeroHall.png",
                "/assets/buttons/Button-FallenHeroHallPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Hall of Fallen");
                    screenManager.addScreen(new FallenHeroHallScreen(screenManager));
                }));
        fallenHallButton.addToScreen(this,true);

        Button exitButton = (new Button(X_START, Y_START + 5 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-Quit.png",
                "/assets/buttons/Button-QuitPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit");
                    gameData.save();
                    System.exit(0);
                    screenManager.addScreen(new DevScreen(screenManager));
                }));
        buttonLayout.addAt(exitButton, 3, 0);

        buttonLayout.addToScreen(this, true);

        setKeyHandler(new ClickableKeyHandler(this.clickables));
    }
    //endregion

    @Override
    protected void transitionOn() {
        int xpos = ((GameObject) clickables.get(0)).getX();
        if(xpos < 64) {
            xpos += 20;
            for(Clickable clickable: clickables){
                ((GameObject) clickable).setX(xpos);
            }
        } else { currentState = ScreenState.Active; }
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }
}
