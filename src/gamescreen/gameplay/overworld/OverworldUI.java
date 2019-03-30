package gamescreen.gameplay.overworld;

import gameengine.GameEngine;
import gameengine.gamedata.VendorData;
import gameengine.rendering.Camera;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.item.ItemMeta;
import gameobject.renderable.player.Player;
import gameobject.renderable.text.DialogBox;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gamescreen.gameplay.PauseMenu;
import gamescreen.gameplay.VendorDialogBox;
import gamescreen.gameplay.VendorScreen;
import gamescreen.gameplay.level.BedroomLevel;
import gamescreen.gameplay.level.LevelDecorator;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class OverworldUI extends Overlay {

    private Player player;
    private Button actionButton;
    private Button inventoryButton;
    private Button leaveButton;
    private static String inventoryBtnPath = "/assets/buttons/Button-Inventory.png";
    private static String inventoryBtnPressedPath = "/assets/buttons/Button-InventoryPressed.png";
    private static String fightBtnPath = "/assets/buttons/Button-Fight.png";
    private static String vendorBtnPath = "/assets/buttons/Button-Vendor.png";
    private SpawnPoint vendorSpawn;
    private int vendorWait = 0;


    public OverworldUI(ScreenManager screenManager, GameScreen parentScreen, Player player, SpawnPoint vendorSpawn) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);
        this.player = player;
        this.vendorSpawn = vendorSpawn;

    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        inventoryButton = new Button(20,20, inventoryBtnPath, inventoryBtnPressedPath, DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new PauseMenu(screenManager, player));
                });
        inventoryButton.addToScreen(this, true);

        actionButton = new ButtonText(350, 20,
                "/assets/buttons/Button-Empty.png",
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
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity, new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Leave",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Leave");
                    //TODO save players location
                    parentScreen.setCamera(null);
                    screenManager.addScreen(new MainMenuScreen(screenManager));

                });
        leaveButton.addToScreen(this, true);

        Button cameraOnButton = new ButtonText(650,20,
                "/assets/buttons/Button-Empty.png",
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58),
                Color.WHITE, "Camera Off!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Camera off");
                    parentScreen.setCamera(null);
                });
        cameraOnButton.addToScreen(this, true);

        Button cameraOffButton = new ButtonText(920,20,
                "/assets/buttons/Button-Empty.png",
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58),
                Color.WHITE, "Camera On!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Camera On");
                    parentScreen.setCamera(new Camera(screenManager, parentScreen, player));
                });
        cameraOffButton.addToScreen(this, true);

        Vendor vendor = new Vendor(vendorSpawn.getTileX(), vendorSpawn.getTileY(), gameData.getVendorData());
        vendor.addToScreen(this, false);
        Button showVendor = new ButtonText(1250, 20,
                "/assets/buttons/Button-Empty.png",
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58),
                Color.WHITE, "Vendor",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor");
                    if (vendor.getState() != Vendor.VendorState.idle) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        vendor.setState(Vendor.VendorState.crawling);

                        //TODO: make vendor trigger box
                        vendor.addToScreen(parentScreen, true);
                    }
                    if (){
                        DialogBox diagBox = new DialogBox(1318, 485, 355, 160, vendor.firstLevel,
                        new Font("NoScary", Font.PLAIN, 40), Color.WHITE, false);
                        diagBox.addToScreen(, true);
                    }
                    else {
                        vendorWait = 0;
                        vendor.getVendorData().restockItems();
                    }
                });
        showVendor.addToScreen(this, true);

        Button warebearWares = new ButtonText(1500, 20,
                "/assets/buttons/Button-Empty.png",
                "/assets/buttons/Button-Empty.png",
                DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 38),
                Color.WHITE, "Wearbear's\nWares",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Warebear's Wares");
                    screenManager.addScreen(new VendorScreen(screenManager, player));
                });
        warebearWares.addToScreen(this, true);
    }
}
