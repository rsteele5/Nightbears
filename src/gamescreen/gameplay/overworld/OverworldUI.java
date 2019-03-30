package gamescreen.gameplay.overworld;

import gameengine.GameEngine;
import gameengine.gamedata.VendorData;
import gameengine.rendering.Camera;
import gameobject.container.ButtonGridContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.room.Door;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.item.ItemMeta;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gamescreen.gameplay.PauseMenu;
import gamescreen.gameplay.VendorDialogBox;
import gamescreen.gameplay.VendorScreen;
import gamescreen.gameplay.level.BedroomLevel;
import gamescreen.gameplay.level.LevelFactory;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;

public class OverworldUI extends Overlay {


    private Player player;
    private Button pauseButton;
    private static String emptyBtnPath = "/assets/buttons/Button-Empty.png";
    private static String vendorBtnPath = "/assets/buttons/Button-Vendor.png";
    private SpawnPoint vendorSpawn;
    private final ArrayList<Door> doors;


    public OverworldUI(ScreenManager screenManager, GameScreen parentScreen, Player player, SpawnPoint vendorSpawn, ArrayList<Door> doors) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);
        this.player = player;
        this.vendorSpawn = vendorSpawn;
        this.doors = doors;

    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        ButtonGridContainer buttonLayout = new ButtonGridContainer(10,6, 256, 96,20,20, 20);

            pauseButton = new ButtonText(0,0, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Pause",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new PauseMenu(screenManager, player));
                });
        buttonLayout.addAt(pauseButton, 0, 0);

        Button actionButton = new ButtonText(350, 20,emptyBtnPath,emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Fight!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - level");
                    //TODO save players location
                    //parentScreen.setCamera(null);
                    screenManager.addScreen(new BedroomLevel(screenManager));

        });
        buttonLayout.addAt(actionButton, 0, 1);

        Vendor vendor = new Vendor(vendorSpawn.getTileX(), vendorSpawn.getTileY(), gameData.getVendorData());
        vendor.addToScreen(this, false);
        Button showVendor = new ButtonText(1250, 20, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 58), Color.WHITE, "Vendor",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Vendor");
                    if (vendor.getState() != Vendor.VendorState.idle) {
                        vendor.setState(Vendor.VendorState.crawling);
                        vendor.addToScreen(parentScreen, true);
                        vendor.setPlayerInteractionOW(() -> {
                            addOverlay(new VendorDialogBox(screenManager, this, 0, 0, player));
                        });
                        doors.forEach(door -> door.setOpenable(true));
                    }
                    else {
                        ItemMeta.attributeAmplifier();
                        vendor.getVendorData().restockItems();
                    }
                });
        buttonLayout.addAt(showVendor, 0, 2);

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
        buttonLayout.addAt(warebearWares, 0, 3);

        Button leaveButton = new ButtonText(20, 120, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Leave",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Leave");
                    //TODO save players location
                    parentScreen.setCamera(null);
                    screenManager.addScreen(new MainMenuScreen(screenManager));

                });
        buttonLayout.addAt(leaveButton, 1, 0);

        buttonLayout.addToScreen(this, true);
    }

    public Clickable getPauseBtn() {
        return pauseButton;
    }
}
