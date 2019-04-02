package gamescreen.gameplay.overworld;

import gameengine.physics.PhysicsEngine;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.room.*;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.ScreenManager;
import gamescreen.gameplay.GamePlayScreen;
import gamescreen.gameplay.VendorDialogBox;
import gamescreen.gameplay.VendorScreen;
import input.listeners.Key.OverworldKeyHandler;

public class OverworldScreen extends GamePlayScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private Map overworldMap;
    private Player player;
    private Vendor vendor;
    private int vendorVisits = -1;
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

        //Bed
        SpawnPoint bedSpawn = overworldMap.getRooms().get(0).getSpawnETCOptions().get(0);
        Prop bed = new Prop(bedSpawn.getTileX(),bedSpawn.getTileY(),
                "/assets/overworld/bedroom/Overworld-Bed2.png");
        bed.addToScreen(this, true);

        //Player
        SpawnPoint playerSpawn = overworldMap.getPlayerSpawn();
        player = new Player(playerSpawn.getTileX(), playerSpawn.getTileY(), DrawLayer.Entity, gameData.getPlayerData());
        player.setState(Player.PlayerState.overWorld);
        player.addToScreen(this,true);
        setCamera(new Camera(screenManager, this, player));

        //Vendor
        SpawnPoint vSpawn = overworldMap.getVendorSpawn();
        vendor = new Vendor(vSpawn.getTileX(), vSpawn.getTileY(), gameData.getVendorData());
        vendor.addToScreen(this, false);

        //Overlays
        UI = new OverworldUI(screenManager, this);
        addOverlay(UI);
        //KeyListener
        setKeyHandler(new OverworldKeyHandler(player, UI.clickables, UI.getPauseBtn()));
        //Physics Engine
        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.TopDown));
    }

    @Override
    protected void activeUpdate() {
        super.activeUpdate();
    }

    public void onLevelComplete(){
        //Vendor
        //TODO: Edit this later
        if(vendorVisits == 0) vendorVisits++;
        vendorVisits++;
        SpawnPoint vSpawn = overworldMap.getVendorSpawn();
        vendor.setPosition(vSpawn.getTileX(), vSpawn.getTileY());
        vendor.setState(Vendor.VendorState.crawling);
        vendor.setActive(this);
        vendor.setPlayerInteractionOW(() -> addOverlay(new VendorScreen(screenManager,this)));
        vendor.setIntroduction(() ->
                addOverlay(new VendorDialogBox(screenManager,this, 0, 0, vendorVisits))
        );
        //Doors
        overworldMap.getRooms().get(0).getDoors().forEach(door -> door.setOpenable(true));
    }
}
