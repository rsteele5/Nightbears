package gamescreen.mainmenu;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.ScreenManager;
import gamescreen.gameplay.level.BedroomLevel;
import gamescreen.gameplay.level.LevelDecorator;
import gamescreen.gameplay.PauseMenu;
import gamescreen.gameplay.VendorScreen;
import gamescreen.gameplay.level.SideScroll;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class DevScreen extends GameScreen {
    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 920;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;

    private Player player;

    //endregion

    //region <Construction and Initialization>
    public DevScreen(ScreenManager screenManager) {
        super(screenManager, "DevScreen", true);
    }

    @Override
    protected void initializeScreen() {
        //Background image
        ImageContainer imageContainer;
        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-DevMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create game entities
        player = new Player(0, 0, DrawLayer.Entity, gameData.getPlayerData());

        //Create button
        Button button;

        button = new Button(X_INIT_BUTTON+(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON - 128,
                "/assets/buttons/Button-NewGame.png",
                "/assets/buttons/Button-NewGamePressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - level");
                    screenManager.addScreen(new BedroomLevel(screenManager));
                });
        button.addToScreen(this, true);

        //Dev screen debug button
        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON - 128,
                "/assets/buttons/Button-Debug.png",
                "/assets/buttons/Button-DebugPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Debug");
                    screenManager.addScreen(new DebugOptionsScreen(screenManager));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Vendor.png",
                "/assets/buttons/Button-VendorPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor");
                    screenManager.addScreen(new VendorScreen(screenManager, player));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Physics.png",
                "/assets/buttons/Button-PhysicsPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Physics");
                    screenManager.addScreen(new SideScroll(screenManager));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Inventory.png",
                "/assets/buttons/Button-InventoryPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    //TODO: Add Inventory Screen
                    screenManager.addScreen(new PauseMenu(screenManager, player));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-MainMenu.png",
                "/assets/buttons/Button-MainMenuPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);

        setKeyHandler(new ClickableKeyHandler(this.clickables));

    }

    //endregion
}
