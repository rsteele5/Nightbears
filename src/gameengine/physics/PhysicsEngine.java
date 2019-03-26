package gameengine.physics;

import _test.Square;
import gameengine.GameEngine;
import gameengine.gamedata.PlayerData;
import gameobject.renderable.enemy.Enemy;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.house.sidescrolling.Floor;
import gameengine.gamedata.GameData;
import gameobject.renderable.player.Player;
import gameobject.renderable.item.weapon.Weapon;
import gamescreen.ScreenManager;
import gameobject.GameObject;
import main.utilities.Debug;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine {

    private GameData gameData;
    private PlayerData playerData;
    private ScreenManager screenManager;
    public PhysicsEngine(GameData gameData, ScreenManager myScreenManager) {
        screenManager = myScreenManager;
    }

    public void update() {
        ArrayList<Kinematic> objects;
        objects = screenManager.getPhysicsObjects();
        if (objects == null)
            return;

        Player.PlayerState playerState = null;
        for (Kinematic k : objects)
            if(k instanceof Player)
                playerState = ((Player) k).getState();

        if(playerState == null)
            return;

        int indices = objects.size();
        for (int i1 = 0; i1 < indices; i1++) {
            if(objects.get(i1).isStatic())
                continue;;

            Kinematic obj1 = objects.get(i1);
            if(playerState == Player.PlayerState.sideScroll)
                if ((obj1.getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                    obj1.setAcceleration(obj1.getAcceleration().add(new PhysicsVector(0, PhysicsMeta.Gravity)));

            obj1.move();
            for (int i2 = 0; i2 < indices; i2++) {
                if (i2 == i1)
                    continue;

                Kinematic obj2 = objects.get(i2);
                if(obj2 instanceof Interactable && (obj1.getHitbox().intersects(((Interactable) obj2).collision())))
                        ((Interactable) obj2).action((GameObject)obj1);

                if (obj1.getHitbox().intersects(obj2.getHitbox())) {
                    if (obj1 instanceof Weapon && obj2 instanceof Player) {
                        //GameEngine.players.get(0).addItem((Weapon) obj1);
                        playerData = gameData.getPlayerData();
                        playerData.addItem((Weapon) obj1);
                        Debug.log(true, "Weapon Get!");
                    }

                    if (obj1 instanceof Enemy && obj2 instanceof Square) {
                        if(!(obj2 instanceof Floor)) {
                            Minion e = (Minion) obj1;
                            e.changeState();
                        }
                    }

                    if (obj1 instanceof Minion && obj2 instanceof Player) {
                        Debug.success(true,"ENEMY->Player");
                        Minion e = (Minion) obj1; e.addhp(-1);//todo sword damage
                        Debug.success(true,Integer.toString(e.getHp()));
                        if(e.getHp() < 1) e.setInactive(e.getScreen());
                    }

                    obj1.setAcceleration(new PhysicsVector(1, 1));
                    if (!obj2.isStatic())
                        obj2.setAcceleration(new PhysicsVector(1, 1));

                    Rectangle intersect = obj1.getHitbox().intersection(obj2.getHitbox());
                    int signX = obj1.getX() < obj2.getX() + obj2.getHitbox().width / 2 ? -1 : 1;
                    int signY = obj1.getY() < obj2.getY() + obj2.getHitbox().height / 2 ? -1 : 1;

                    if (intersect.height > .5 && intersect.width > .5) {
                        if (obj2.isStatic()) {
                            if(intersect.width > intersect.height)
                                if(signY == -1 && obj1 instanceof Player)
                                    ((Player) obj1).grounded = true;

                            if (intersect.height < intersect.width)
                                obj1.setY(obj1.getY() + intersect.height * signY);

                            else
                                obj1.setX(obj1.getX() + intersect.width * signX);

                        } else {
                            if(intersect.width > intersect.height)
                                if(signY == -1 && obj1 instanceof Player) ((Player) obj1).grounded = true;

                            if (intersect.height < intersect.width) {
                                obj1.setY(obj1.getY() + (intersect.height / 2 + 1) * signY);
                                obj2.setY(obj2.getY() - (intersect.height / 2 + 1) * signY);

                            } else {
                                obj1.setX(obj1.getX() + (intersect.width / 2 + 1) * signX);
                                obj2.setX(obj2.getX() - (intersect.width / 2 + 1) * signX);

                            }
                        }
                    }
                    i2 = 0;
                }
            }
        }
    }
}
