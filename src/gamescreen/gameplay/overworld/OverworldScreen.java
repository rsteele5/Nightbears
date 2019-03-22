package gamescreen.gameplay.overworld;

import gameengine.gamedata.GameData;
import gameengine.gamedata.VendorData;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.Tile;
import gameobject.renderable.house.overworld.room.Bedroom;
import gameobject.renderable.house.overworld.room.Room;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.house.sidescrolling.Floor;
import gamescreen.gameplay.VendorDialogBox;
import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.OverworldKeyHandler;
import main.utilities.Debug;

import java.awt.*;
import java.util.ArrayList;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private VendorDialogBox vendorDialogBox;
    private Map overworldMap;
    private VendorData vendorData;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 0f);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        setKeyHandler(new OverworldKeyHandler());
        //House generation
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.createMap(this);
        mapBuilder.addRoomAtCell(0, 0, new Bedroom());

        overworldMap = mapBuilder.buildMap();

        //Player
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        Player playerOW = new Player(playerSpawn.getTileX(), playerSpawn.getTileY(), DrawLayer.Entity, gameData.getPlayerData());
        playerOW.setState(Player.PlayerState.overWorld);
        playerOW.addToScreen(this,true);
        setCamera(new Camera(screenManager, this, playerOW));

        //Vendor
        SpawnPoint vendorSpawn = overworldMap.getVendorSpawn();
        Vendor vendor = new Vendor(vendorSpawn.getTileX(), vendorSpawn.getTileY(), gameData.getVendorData());
        vendor.setImage("/assets/vendor/vendoridleanimation/VendorOverworldForward.png");
        //TODO: make vendor trigger box
        vendor.addToScreen(this, true);


        //Walls
        ArrayList<SpawnPoint> objectSpawns = overworldMap.getObjectSpawns();
        SpawnPoint TLC = objectSpawns.get(0);
        SpawnPoint TRC = objectSpawns.get(1);
        SpawnPoint BLC = objectSpawns.get(2);

//        Floor northWall = new Floor(TLC.getTileX()-50,TLC.getTileY()-50,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
//        northWall.setHeight(25);
//        northWall.setWidth(500);
//        northWall.addToScreen(this,true);
//        Floor westWall = new Floor(TLC.getTileX()-50,TLC.getTileY()-50,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
//        westWall.setHeight(500);
//        westWall.setWidth(25);
//        westWall.addToScreen(this,true);
//        Floor eastWall = new Floor(BLC.getTileX()+25,BLC.getTileY()-450,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
//        eastWall.setHeight(500);
//        eastWall.setWidth(25);
//        eastWall.addToScreen(this,true);
//        Floor southWall = new Floor(BLC.getTileX()-450,BLC.getTileY()+25,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
//        southWall.setHeight(25);
//        southWall.setWidth(500);
//        southWall.addToScreen(this,true);

        //Overlay
        UI = new OverworldUI(screenManager, this, playerOW);
        vendorDialogBox = new VendorDialogBox(screenManager,this, 460,100, playerOW);
        addOverlay(UI);
        addOverlay(vendorDialogBox);

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
