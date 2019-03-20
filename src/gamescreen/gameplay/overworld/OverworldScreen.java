package gamescreen.gameplay.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.room.Bedroom;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.house.sidescrolling.Floor;
import gamescreen.gameplay.VendorDialogBox;
import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import main.utilities.Debug;

import java.util.ArrayList;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private VendorDialogBox vendorDialogBox;
    private Map overworldMap;
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
        mapBuilder.createMap(this);
        //mapBuilder.addRoomAtCell(5, 5, new Bedroom());
        mapBuilder.addRoomAtCell(0, 0, new Bedroom());
        //mapBuilder.addRoomAtCell(0,5, new Bedroom());
        //mapBuilder.addRoomAtCell(5, 0, new Bedroom());

        overworldMap = mapBuilder.buildMap();

        //Player
        GameEngine.players.get(0).setState(Player.PlayerState.overWorld);
        Debug.log(true, String.valueOf(GameEngine.players.get(0).getState()));
        GameEngine.players.get(0).reset();
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        GameEngine.players.get(0).setPosition(playerSpawn.getTileX(), playerSpawn.getTileY());
        GameEngine.players.get(0).addToScreen(this,true);
        setCamera(new Camera(screenManager, this, GameEngine.players.get(0)));

        //Vendor
        Vendor vendor = GameEngine.vendor;
        SpawnPoint vendorSpawn = overworldMap.getVendorSpawn();
        vendor.setPosition(vendorSpawn.getTileX(), vendorSpawn.getTileY());
        vendor.setImage("/assets/vendor/vendoridleanimation/VendorOverworldForward.png");
        //TODO: make vendor trigger box
        vendor.addToScreen(this, true);


        //Walls
        ArrayList<SpawnPoint> objectSpawns = overworldMap.getObjectSpawns();
        SpawnPoint TLC = objectSpawns.get(0);
        SpawnPoint TRC = objectSpawns.get(1);
        SpawnPoint BLC = objectSpawns.get(2);

        Floor northWall = new Floor(TLC.getTileX()-50,TLC.getTileY()-50,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
        northWall.setHeight(25);
        northWall.setWidth(500);
        northWall.addToScreen(this,true);
        Floor westWall = new Floor(TLC.getTileX()-50,TLC.getTileY()-50,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
        westWall.setHeight(500);
        westWall.setWidth(25);
        westWall.addToScreen(this,true);
        Floor eastWall = new Floor(BLC.getTileX()+25,BLC.getTileY()-450,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
        eastWall.setHeight(500);
        eastWall.setWidth(25);
        eastWall.addToScreen(this,true);
        Floor southWall = new Floor(BLC.getTileX()-450,BLC.getTileY()+25,"/assets/testAssets/alpha0.png", DrawLayer.Entity);
        southWall.setHeight(25);
        southWall.setWidth(500);
        southWall.addToScreen(this,true);

        //Overlay TODO: Fix layering
        UI = new OverworldUI(screenManager, this);
        vendorDialogBox = new VendorDialogBox(screenManager,this, 460,100);
        addOverlay(UI);
        addOverlay(vendorDialogBox);

    }
}
