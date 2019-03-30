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
    private Map overworldMap;
    private VendorData vendorData = gameData.getVendorData();
    private Vendor vendor = new Vendor(0, 0, vendorData);
    private DialogBox diagBox;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 0f);
    }


    @Override
    protected void initializeScreen() {
        //House generation
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.createMap(this);
        mapBuilder.addRoomAtCell(0, 0, new Bedroom());
        mapBuilder.addRoomAtCell(8,0, new LivingRoom());
        mapBuilder.addRoomAtCell(0,8, new Bathroom());
        overworldMap = mapBuilder.buildMap();
        overworldMap.addToScreen(this, true);
        overworldMap.getRooms().forEach(room -> room.setInactive(this));
        overworldMap.getRooms().get(0).setActive(this);

        //Player
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        Player playerOW = new Player(playerSpawn.getTileX(), playerSpawn.getTileY(), DrawLayer.Entity, gameData.getPlayerData());
        playerOW.setState(Player.PlayerState.overWorld);
        playerOW.addToScreen(this,true);
        setCamera(new Camera(screenManager, this, playerOW));

        //Overlays
        UI = new OverworldUI(screenManager, this, playerOW, overworldMap.getVendorSpawn(),
                             overworldMap.getRooms().get(0).getDoors());
        addOverlay(UI);

        //KeyListener
        setKeyHandler(new OverworldKeyHandler(playerOW, UI.clickables, UI.getPauseBtn()));
    }

    @Override
    protected void activeUpdate() {
        super.activeUpdate();

    }
}
