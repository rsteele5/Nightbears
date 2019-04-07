package gamescreen.gameplay.level;

import gameengine.physics.PhysicsEngine;
import gameengine.physics.Platform;
import gameengine.rendering.SSCamera;
import gameobject.CameraTarget;
import gameobject.LockCameraTrigger;
import gameobject.TriggerableBoundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Door;
import gameobject.renderable.player.Player;
import gamescreen.ScreenManager;
import gamescreen.gameplay.GamePlayScreen;
import gamescreen.gameplay.overworld.OverworldScreen;
import input.listeners.Key.SideScrollKeyHandler;


public class BedroomLevel extends GamePlayScreen {

    private BedroomBackgroundLayout background;
    private Player player;
    private OverworldScreen parentScreen;

    public BedroomLevel(ScreenManager screenManager, OverworldScreen parentScreen) {
        super(screenManager, "BedroomLevel", true, 1f);
        this.parentScreen= parentScreen;
    }

    @Override
    protected void initializeScreen() {
        Platform p = new Platform(350,900,"/assets/testAssets/brick.jpg",DrawLayer.Prop);
        p.setWidth(100);
        p.setHeight(20);
        p.addToScreen(this,true);
        Door finishDoor = new Door(800, 300,
                "/assets/sidescroll/SideScrollDoor.png",
                () -> {
                    setScreenState(ScreenState.TransitionOff);
                    screenManager.addScreen(new EndLevelScreen(screenManager,  true));
                    parentScreen.onLevelComplete();
                });
        finishDoor.addToScreen(this, true);

        Door secondFinishDoor = new Door(1600, 300,
                "/assets/sidescroll/SideScrollDoor.png",
                () -> {
                    setScreenState(ScreenState.TransitionOff);
                    screenManager.addScreen(new EndLevelScreen(screenManager,  true));
                    parentScreen.onLevelComplete();
                });
        secondFinishDoor.addToScreen(this, true);

        player = new Player(50, 276, DrawLayer.Entity, gameData.getPlayerData());
        player.addToScreen(this, true);
        player.setState(Player.PlayerState.sideScroll);
        setKeyHandler(new SideScrollKeyHandler(player));

        CameraTarget cameraTarget = new CameraTarget(player);
        cameraTarget.addToScreen(this, true);


        SSCamera bedroomCamera = new SSCamera(screenManager, this, cameraTarget);
        setCamera(bedroomCamera);
        background = new BedroomBackgroundLayout();
        background.getBackground().addToScreen(this, true);
        background.getBoundaries().forEach(boundary -> boundary.addToScreen(this, true));

        TriggerableBoundary bounds = new TriggerableBoundary(1480, 0, 120, 1000);
        bounds.setTrigger(true);
        bounds.addToScreen(this,true);
        LockCameraTrigger cameraTrigger = new LockCameraTrigger(1600, 0, 1980, 1000, bedroomCamera, bounds);
        cameraTrigger.addToScreen(this,true);
        
        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));
    }

    @Override
    public void transitionOff(){
        exiting = true;
    }
}


