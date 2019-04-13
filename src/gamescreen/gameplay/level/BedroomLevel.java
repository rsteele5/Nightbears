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

        Minion minion = new Walker(500,700, DrawLayer.Entity, 3, 500);
        minion.addToScreen(this, true);

        Flyer flyboi = new Flyer(500,400, DrawLayer.Entity, 3, 500, player);
        flyboi.addToScreen(this, true);


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

        /*TriggerableBoundary bounds = new TriggerableBoundary(1480, 0, 120, 1000);
        bounds.setTrigger(true);
        bounds.addToScreen(this,true);
        LockCameraTrigger cameraTrigger = new LockCameraTrigger(1600, 0, 1980, 1000, bedroomCamera, bounds);
        cameraTrigger.addToScreen(this,true);*/
        

        SpikeBall s = new SpikeBall(250,150);
      //  SpikeBall s2 = new SpikeBall(750,150);
      //  SpikeBall s3 = new SpikeBall(1050,150);
        s.addToScreen(this,true);
     //   s2.addToScreen(this,true);
       // s3.addToScreen(this,true);

        /*
        Square s3;
        for(int i1 = 0 ; i1 < 7; i1++){
            for(int i2 = 0; i2 < 7; i2++){
                s3 = new Square(75 + i1 * 150,75 + i2 * 150);
                s3.addToScreen(this,true);
            }
        }

        DisappearingPlatform p = new DisappearingPlatform(350,900,"/assets/testAssets/brick.jpg",DrawLayer.Prop);
        p.setWidth(100);
        p.setHeight(20);
        p.addToScreen(this,true);
        */

        //Overlays
        SideScrollUI UI = new SideScrollUI(screenManager, this, player);
        addOverlay(UI);

        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));


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


