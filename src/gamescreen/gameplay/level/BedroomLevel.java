package gamescreen.gameplay.level;

import gameengine.physics.*;
import gameengine.rendering.SSCamera;
import gameobject.CameraTarget;
import gameobject.LockCameraTrigger;
import gameobject.TriggerableBoundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.enemy.*;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Door;
import gameobject.renderable.player.Player;
import gamescreen.ScreenManager;
import gamescreen.gameplay.GamePlayScreen;
import gamescreen.gameplay.overworld.OverworldScreen;
import gamescreen.splashscreen.GameOverScreen;
import input.listeners.Key.SideScrollKeyHandler;
import main.utilities.Debug;


public class BedroomLevel extends GamePlayScreen {

    private BedroomBackgroundLayout background;
    private Player player;
    private OverworldScreen parentScreen;
    private int coins;

    public BedroomLevel(ScreenManager screenManager, OverworldScreen parentScreen) {
        super(screenManager, "BedroomLevel", true, 1f);
        this.parentScreen= parentScreen;
    }

    @Override
    protected void initializeScreen() {
        coins = gameData.getPlayerData().getGold();
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
                    screenManager.addScreen(new EndLevelScreen(screenManager,  true, gameData.getPlayerData().getGold() - coins));
                    parentScreen.onLevelComplete();
                });
        finishDoor.setDrawLayer(DrawLayer.Background);
        finishDoor.addToScreen(this, true);

        SpikeBall s = new SpikeBall(300,-150);
      //  SpikeBall s2 = new SpikeBall(750,150);
      //  SpikeBall s3 = new SpikeBall(1050,150);
        s.addToScreen(this,true);

        SpikeTrap s2 = new SpikeTrap(200,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(300,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(400,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(500,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(600,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(700,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(800,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(900,925);
        s2.addToScreen(this,true);
        s2 = new SpikeTrap(1000,925);
        s2.addToScreen(this,true);
     //   s2.addToScreen(this,true);
       // s3.addToScreen(this,true);

        //Overlays
        DisappearingPlatform p = new DisappearingPlatform(200,800,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        p = new DisappearingPlatform(350,700,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        p = new DisappearingPlatform(500,600,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        p = new DisappearingPlatform(700,600,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        p = new DisappearingPlatform(850,700,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        p = new DisappearingPlatform(1000,800,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);
        SideScrollUI UI = new SideScrollUI(screenManager, this, player);
        addOverlay(UI);

        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));

        p = new DisappearingPlatform(1200,900,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);


        //enemies

        Minion minion = new Walker(1300,700, DrawLayer.Entity, 3, 50);
        minion.addToScreen(this, true);

        p = new DisappearingPlatform(1900,900,"/assets/testAssets/brick.jpg",DrawLayer.Entity);
        p.setWidth(150);
        p.setHeight(50);
        p.addToScreen(this,true);

//        Flyer flyboi = new Flyer(3000,400, DrawLayer.Entity, 3, 1, player);
//        flyboi.addToScreen(this, true);

        DisappearingPlatform p2 = new DisappearingPlatform(750,900,"/assets/testAssets/brick.jpg",DrawLayer.Prop);
        p2.setWidth(100);
        p2.setHeight(20);
        p2.addToScreen(this,true);


        SloshyBoi boss = new SloshyBoi(6500,300, DrawLayer.Entity, 1, 1000);
        Debug.log(true,"Make a new sloshyboi");
        boss.setAlpha(0f);
        boss.addToScreen(this, true);
        boss.setState(new Hidden());

        TriggerableBoundary bounds = new TriggerableBoundary(5300-120, 0, 120, 1000);
        bounds.setTrigger(true);
        bounds.addToScreen(this,true);
        LockCameraTrigger cameraTrigger = new LockCameraTrigger(5300, 0, 1980, 1000, bedroomCamera, bounds, boss);
        cameraTrigger.addToScreen(this,true);

        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));
        //enemies

//        Minion minion = new Walker(500,700, DrawLayer.Entity, 3, 500);
//        minion.addToScreen(this, true);

//        Flyer flyboi = new Flyer(500,400, DrawLayer.Entity, 3, 500, player);
//        flyboi.addToScreen(this, true);
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


