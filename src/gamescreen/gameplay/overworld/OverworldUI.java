package gamescreen.gameplay.overworld;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gamescreen.gameplay.PauseMenu;
import gamescreen.gameplay.level.BedroomLevel;
import gamescreen.gameplay.level.LevelDecorator;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class OverworldUI extends Overlay {

    private Button actionButton;
    private Button inventoryButton;
    private Button leaveButton;
    private static String inventoryBtnPath = "/assets/buttons/Button-Inventory.png";
    private static String fightBtnPath = "/assets/buttons/Button-Fight.png";
    private static String vendorBtnPath = "/assets/buttons/Button-Vendor.png";


    public OverworldUI(ScreenManager screenManager, GameScreen parentScreen) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        inventoryButton = new Button(20,20, inventoryBtnPath, DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new PauseMenu(screenManager));
                });
        inventoryButton.addToScreen(this, true);

        actionButton = new ButtonText(350, 20,
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity, new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Fight!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - level");
                    //TODO save players location
                    parentScreen.setCamera(null);
                    screenManager.addScreen(LevelDecorator.create(screenManager, new BedroomLevel()));

        });
        actionButton.addToScreen(this, true);

        leaveButton = new ButtonText(20, 120,
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity, new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Leave",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Leave");
                    //TODO save players location
                    parentScreen.setCamera(null);

                    GameEngine.players.get(0).setState(Player.PlayerState.asleep);
                    screenManager.addScreen(new MainMenuScreen(screenManager));

                });
        leaveButton.addToScreen(this, true);

        Button cameraOnButton = new ButtonText(650,20,
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58),
                Color.WHITE, "Camera Off!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor");
                    parentScreen.setCamera(null);
                });
        cameraOnButton.addToScreen(this, true);

        Button cameraOffButton = new ButtonText(920,20,
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58),
                Color.WHITE, "Camera On!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Vendor");
                    parentScreen.setCamera(new Camera(screenManager, parentScreen, GameEngine.players.get(0)));
                });
        cameraOffButton.addToScreen(this, true);
    }
}
