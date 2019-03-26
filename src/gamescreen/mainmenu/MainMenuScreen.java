package gamescreen.mainmenu;

import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.mainmenu.options.OptionScreen;
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
        Button newGameButton = (new Button(X_START, Y_START,
                "/assets/buttons/Button-NewGame.png",
                "/assets/buttons/Button-NewGamePressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - New Game");
                    screenManager.addScreen(new PlayerCountScreen(screenManager));
                }));
        newGameButton.addToScreen(this,true);

        Button optionsButton = (new Button(X_START, Y_START + BUTTON_HEIGHT + Y_BUFFER,
                "/assets/buttons/Button-Options.png",
                "/assets/buttons/Button-OptionsPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
                }));
        optionsButton.addToScreen(this,true);

        Button devModeButton = (new Button(X_START, Y_START + 2 * (BUTTON_HEIGHT + Y_BUFFER),
                "/assets/buttons/Button-Dev.png",
                "/assets/buttons/Button-DevPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - DevMode");
                    screenManager.addScreen(new DevScreen(screenManager));
                }));
        devModeButton.addToScreen(this,true);

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
        exitButton.addToScreen(this,true);
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
