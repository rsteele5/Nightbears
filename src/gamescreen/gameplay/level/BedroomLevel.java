package gamescreen.gameplay.level;

import _test.Square;
import gameengine.gamedata.PlayerData;
import gameengine.rendering.Camera;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Floor;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.enemy.WalkLeftMS;
import gameobject.renderable.enemy.Walker;
import gameobject.renderable.player.Player;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.SideScrollKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class BedroomLevel extends GameScreen {

    public BedroomLevel(ScreenManager screenManager) {
        super(screenManager, "BedroomLevel");
    }

    @Override
    protected void initializeScreen() {
        Player player = new Player(10, 276, DrawLayer.Entity, gameData.getPlayerData());
        player.addToScreen(this, true);
        player.setState(Player.PlayerState.overWorld);
        setKeyHandler(new SideScrollKeyHandler(player));
        setCamera(new Camera(screenManager, this, player));
        BedroomBackgroundLayout background = new BedroomBackgroundLayout();
        background.getBackground().addToScreen(this, true);
        //This is where the instruction for how to procedurally generate a level would go
        Floor floorTile = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        Floor floorTile2 = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        floorTile.setWidth(1260);
        floorTile.setHeight(50);
        floorTile2.setWidth(50);
        floorTile2.setHeight(96);
        floorTile.addToScreen(this, true);

        Square square;
        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                square = new Square(x1 * 75 + 100,y1 * 75,"/assets/testAssets/square.png",DrawLayer.Entity);
                square.addToScreen(this, true);
            }
        }

        square = new Square(800,75,"/assets/testAssets/square.png",DrawLayer.Entity);
        square.addToScreen(this, true);

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
}
