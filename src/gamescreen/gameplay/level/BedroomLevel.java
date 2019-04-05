package gamescreen.gameplay.level;

import gameengine.physics.PhysicsEngine;
import gameengine.physics.Platform;
import gameengine.rendering.Camera;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Door;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.GamePlayScreen;
import gamescreen.gameplay.overworld.OverworldScreen;
import input.listeners.Key.SideScrollKeyHandler;


public class BedroomLevel extends GamePlayScreen {

    private BedroomBackgroundLayout background;
    private Player player;
    private OverworldScreen parentScreen;

    public BedroomLevel(ScreenManager screenManager, OverworldScreen parentScreen) {
        super(screenManager, "BedroomLevel", 1f);
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
        player = new Player(30, 276, DrawLayer.Entity, gameData.getPlayerData());
        player.addToScreen(this, true);
        player.setState(Player.PlayerState.sideScroll);
        setKeyHandler(new SideScrollKeyHandler(player));
        setCamera(new Camera(screenManager, this, player));
        background = new BedroomBackgroundLayout();
        background.getBackground().addToScreen(this, true);
        background.getBoundaries().forEach(boundary -> boundary.addToScreen(this, true));


        Weapon myWeap = new WeaponBuilder()
                .position(800, 476)
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword!")
                .type(WeaponType.Sword)
                .value(15)
                .buildWeapon();
        myWeap.setWidth(50);
        myWeap.setHeight(50);
        myWeap.addToScreen(this, true);

        setPhysicsEngine(new PhysicsEngine(player, PhysicsEngine.PhysicState.SideScroll));

    }

    @Override
    public void transitionOff(){
        exiting = true;
    }
}


