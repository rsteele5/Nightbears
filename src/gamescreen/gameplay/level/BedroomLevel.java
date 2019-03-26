package gamescreen.gameplay.level;

import _test.Square;
import gameengine.gamedata.PlayerData;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.enemy.WalkLeftMS;
import gameobject.renderable.enemy.Walker;
import gameobject.renderable.house.sidescrolling.Floor;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;

public class BedroomLevel implements Level {

    @Override
    public void buildBackground(GameScreen gameScreen) {
        ImageContainer background = new ImageContainer(0, 0, "/assets/backgrounds/BG-Level.png", DrawLayer.Background);
        background.addToScreen(gameScreen, true);

    }

    @Override
    public void buildTerrain(GameScreen gameScreen) {
        //This is where the instruction for how to procedurally generate a level would go
        Floor floorTile = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        Floor floorTile2 = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        floorTile.setWidth(1260);
        floorTile.setHeight(50);
        floorTile2.setWidth(50);
        floorTile2.setHeight(96);
        gameScreen.kinematics.add(floorTile);
        gameScreen.kinematics.add(floorTile2);
        floorTile.addToScreen(gameScreen, true);

        Square square;
        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                square = new Square(x1 * 75 + 100,y1 * 75,"/assets/testAssets/square.png",DrawLayer.Entity);
                square.addToScreen(gameScreen, true);
            }
        }

        square = new Square(800,75,"/assets/testAssets/square.png",DrawLayer.Entity);
        square.addToScreen(gameScreen, true);

        Weapon myWeap = new WeaponBuilder()
                .position(800, 476)
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword!")
                .type(WeaponType.Sword)
                .value(15)
                .buildWeapon();
        myWeap.setWidth(50);
        myWeap.setHeight(50);
        gameScreen.kinematics.add(myWeap);
        myWeap.addToScreen(gameScreen, true);
    }

    @Override
    public Player buildPlayer(GameScreen gameScreen, PlayerData playerData) {
        Player player = new Player(10, 476, DrawLayer.Entity, playerData);
        player.setState(Player.PlayerState.sideScroll);
        player.addToScreen(gameScreen, true);

        return player;

    }


    @Override
    public void buildEnemies(GameScreen gameScreen) {
        Minion guy1 = new Walker(600,0, "/assets/enemies/minions/walker/walker.png", DrawLayer.Entity);
        guy1.setState(new WalkLeftMS());
        guy1.addToScreen(gameScreen,true);

    }

}
