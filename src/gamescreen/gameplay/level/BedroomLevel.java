package gamescreen.gameplay.level;

import gameengine.physics.PhysicsEngine;
import gameengine.physics.SpikeBall;
import gameengine.rendering.SSCamera;
import gameobject.CameraTarget;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.enemy.Flyer;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.enemy.Walker;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Door;
import gameobject.renderable.player.Player;
import gamescreen.ScreenManager;
import gamescreen.gameplay.GamePlayScreen;
import gamescreen.gameplay.overworld.OverworldScreen;
import gamescreen.splashscreen.GameOverScreen;
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

        Door finishDoor = new Door(6750, 300,
                "/assets/sidescroll/SideScrollDoor.png",
                () -> {
                    setScreenState(ScreenState.TransitionOff);
                    screenManager.addScreen(new EndLevelScreen(screenManager,  true));
                    parentScreen.onLevelComplete();
                });
        finishDoor.setDrawLayer(DrawLayer.Background);
        finishDoor.addToScreen(this, true);

        SpikeBall s = new SpikeBall(250,150);
        s.addToScreen(this,true);

        //Overlays
        SideScrollUI UI = new SideScrollUI(screenManager, this, player);
        addOverlay(UI);

        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));

        //enemies

        Minion minion = new Walker(500,700, DrawLayer.Entity, 3, 500);
        minion.addToScreen(this, true);

        Flyer flyboi = new Flyer(500,400, DrawLayer.Entity, 3, 500, player);
        flyboi.addToScreen(this, true);
    }

    @Override
    public void transitionOff(){
        exiting = true;
    }

    @Override
    protected void activeUpdate() {
        if(physicsEngine != null) physicsEngine.update(collidables, kinematics, interactables);
        super.activeUpdate();
        if(player.getHealth() <= 0){
            screenManager.addScreen(new GameOverScreen(screenManager, 0f));
            setScreenState(ScreenState.TransitionOff);
        }
    }
}


