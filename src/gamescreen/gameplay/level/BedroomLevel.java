package gamescreen.gameplay.level;

import gameengine.rendering.Camera;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Door;
import gameobject.renderable.player.Player;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.SideScrollKeyHandler;

import java.awt.event.KeyEvent;


public class BedroomLevel extends GameScreen {

    private BedroomBackgroundLayout background;
    private Player player;

    public BedroomLevel(ScreenManager screenManager) {
        super(screenManager, "BedroomLevel", true);
    }

    @Override
    protected void initializeScreen() {
        Door finishDoor = new Door(800, 300,
                "/assets/sidescroll/SideScrollDoor.png",
                () -> {
                    setScreenState(ScreenState.TransitionOff);
                    screenManager.addScreen(new EndLevelScreen(screenManager,  true));
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


    }

    @Override
    public void transitionOff(){
        exiting = true;
    }
}


