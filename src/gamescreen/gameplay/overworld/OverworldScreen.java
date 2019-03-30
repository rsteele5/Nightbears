package gamescreen.gameplay.overworld;

import gameengine.gamedata.VendorData;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.room.Bedroom;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.text.DialogBox;
import gamescreen.gameplay.VendorDialogBox;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.OverworldKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    //private VendorDialogBox vendorDialogBox;
    private Map overworldMap;
    private VendorData vendorData = gameData.getVendorData();
    private Vendor vendor = new Vendor(0, 0, vendorData);
    private DialogBox diagBox;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 0f);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        //House generation
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.createMap();
        mapBuilder.addRoomAtCell(0, 0, new Bedroom());

        overworldMap = mapBuilder.buildMap();
        overworldMap.addToScreen(this, true);

        //Player
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        Player playerOW = new Player(playerSpawn.getTileX(), playerSpawn.getTileY(), DrawLayer.Entity, gameData.getPlayerData());
        playerOW.setState(Player.PlayerState.overWorld);
        playerOW.addToScreen(this,true);
        setCamera(new Camera(screenManager, this, playerOW));

        //Overlay TODO: Fix layering
        UI = new OverworldUI(screenManager, this, playerOW, overworldMap.getVendorSpawn());

        addOverlay(UI);

        //KeyListener
        setKeyHandler(new OverworldKeyHandler(playerOW, UI.clickables));

    }

    @Override
    protected void activeUpdate() {
        for(GameObject activeObject: activeObjects){
            activeObject.update();
        }
//        if (vendor.getState() == Vendor.VendorState.sittingup && diagBox == null){
//            Debug.log(DebugEnabler.TEST_LOG, "Vendor is sitting up and dialog box is null");
//            diagBox = new DialogBox(1318, 485, 355, 160, vendor.firstLevel,
//                    new Font("NoScary", Font.PLAIN, 40), Color.WHITE, false);
//            diagBox.addToScreen(this, true);
//        }
    }

//    @Override
//    protected void loadContent() {
//
//        if (loadingScreenRequired) {
//            loadingScreen = screenManager.getLoadingScreen();
//            loadingScreen.initializeLoadingScreen(loadables.size());
//            coverWith(loadingScreen);
//            for (int i = 0; i < loadables.size(); i++) {
//                loadables.get(i).load();
//                loadingScreen.dataLoaded(i);
//            }
//            isLoading = false;
//            childScreen = null;
//            loadingScreen.reset();
//
//        }
//    }
}
