package gamescreen.gameplay.overworld;

import _test.Square;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Map;
import gameobject.renderable.house.overworld.MapBuilder;
import gameobject.renderable.house.overworld.room.Bedroom;
import gameobject.renderable.house.sidescrolling.Floor;
import gamescreen.gameplay.VendorDialogBox;
import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import main.utilities.Debug;

public class OverworldScreen extends GameScreen {

    //region <Variable Declaration>
    private OverworldUI UI;
    private VendorDialogBox vendorDialogBox;
    private Map overworldMap;
    //endregion

    public OverworldScreen(ScreenManager screenManager) {
        super(screenManager, "Overworld", 1f);
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        //House generation
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.createMap(this);
        mapBuilder.addRoomAtCell(1,1, new Bedroom());
        mapBuilder.addRoomAtCell(4,4, new Bedroom());
        overworldMap = mapBuilder.buildMap();

        //Player
        GameEngine.players.get(0).setState(Player.PlayerState.overWorld);
        Debug.log(true, String.valueOf(GameEngine.players.get(0).getState()));
        GameEngine.players.get(0).reset();
        GameEngine.players.get(0).addToScreen(this,true);
        setCamera(new Camera(this, GameEngine.players.get(0)));

        //Vendor
        Vendor vendor = GameEngine.vendor;
        vendor.setPosition(150,0);
        vendor.setImage("/assets/vendor/vendoridleanimation/VendorOverworldForward.png");
        //TODO: make vendor trigger box
        vendor.addToScreen(this, true);
        Floor test = new Floor(30,30,"/assets/testAssets/brick.jpg", DrawLayer.Entity);
        test.setHeight(100);
        test.setWidth(100);
        //Square test2 = new Square(100,100,"/assets/testAssets/square.png", DrawLayer.Entity);
        //test2.addToScreen(this,true);

        //Overlay TODO: Fix layering
        UI = new OverworldUI(screenManager, this);
        vendorDialogBox = new VendorDialogBox(screenManager,this, 460,100);
        addOverlay(UI);
        addOverlay(vendorDialogBox);

    }
}
