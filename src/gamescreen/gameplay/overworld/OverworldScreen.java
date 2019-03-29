package gamescreen.gameplay.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.room.*;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.OverworldKeyHandler;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private Map overworldMap;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 0f);
    }


    @Override
    protected void initializeScreen() {
        //House generation
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.createMap();
        mapBuilder.addRoomAtCell(0, 0, new Bedroom());
        mapBuilder.addRoomAtCell(8,0, new LivingRoom());
        mapBuilder.addRoomAtCell(0,8, new Bathroom());
        overworldMap = mapBuilder.buildMap();
        overworldMap.addToScreen(this, true);
        for(Room room : overworldMap.getRooms()){
            room.setInactive(this);
        }
        overworldMap.getRooms().get(0).setActive(this);

        //Player
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        Player playerOW = new Player(playerSpawn.getTileX(), playerSpawn.getTileY(), DrawLayer.Entity, gameData.getPlayerData());
        playerOW.setState(Player.PlayerState.overWorld);
        playerOW.addToScreen(this,true);
        setCamera(new Camera(screenManager, this, playerOW));

        //Overlays
        UI = new OverworldUI(screenManager, this, playerOW, overworldMap.getVendorSpawn());
        addOverlay(UI);

        //KeyListener
        setKeyHandler(new OverworldKeyHandler(playerOW, UI.clickables, UI.getPauseBtn()));
    }
}
