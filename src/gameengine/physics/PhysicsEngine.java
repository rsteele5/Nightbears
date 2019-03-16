package gameengine.physics;

import gameengine.GameEngine;
import gameengine.gamedata.GameData;
import gameobject.renderable.player.Player;
import gameobject.renderable.item.weapon.Weapon;
import gamescreen.ScreenManager;
import gameobject.GameObject;
import main.Game;
import main.utilities.Debug;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine {

    private GameData gameData;
    private ScreenManager screenManager;
    private final int winWidth = 1920;
    private final int winHeight = 1080;
    //Collide with edges of Screen?

    public PhysicsEngine(GameData gameData, ScreenManager myScreenManager) {
        this.gameData = gameData;
        screenManager = myScreenManager;
    }

    public void update() {
        ArrayList<Kinematic> objects;
        objects = screenManager.getPhysicsObjects();
        if (objects == null) return;
        Player.PlayerState playerState = null;
        for (Kinematic k : objects){
            if(k instanceof Player) playerState = ((Player) k).getState();
        }
        if(playerState == null) return;
        int indices = objects.size();
        for (int i1 = 0; i1 < indices; i1++) {
            if(objects.get(i1).isStatic()) continue;;
            GameObject obj1 = (GameObject) objects.get(i1);

            if(playerState == Player.PlayerState.sideScroll) {
                if ((((Kinematic) obj1).getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                    ((Kinematic) obj1).setAcceleration(((Kinematic) obj1).getAcceleration().add(new PhysicsVector(0, PhysicsMeta.Gravity)));
            }

            obj1.setY(obj1.getY() + (int) ((Kinematic) obj1).getVelocity().y);
            obj1.setX(obj1.getX() + (int) ((Kinematic) obj1).getVelocity().x);

            if (PhysicsMeta.boundaries) {
                if ((obj1.getY() + ((Kinematic) obj1).getHitbox().height) > winHeight) {
                    obj1.setY(winHeight - ((Kinematic) obj1).getHitbox().height);
                    ((Kinematic) obj1).setAcceleration(new PhysicsVector(1, 1));
                    if (obj1 instanceof Player) ((Player) obj1).grounded = true;
                }
                if ((obj1.getX() + ((Kinematic) obj1).getHitbox().width) > winWidth) {
                    obj1.setX(winWidth - ((Kinematic) obj1).getHitbox().width);
                    ((Kinematic) obj1).setAcceleration(new PhysicsVector(1, 1));
                }
                if (obj1.getX() <= 0) obj1.setX(0);
                if (obj1.getY() <= 0) {
                    obj1.setY(0);
                }
            }

            //Collision Detection
            //TODO: EXTRACT
            for (int i2 = 0; i2 < indices; i2++) {
                if (i2 == i1) continue;
                GameObject obj2 = (GameObject) objects.get(i2);
                if(obj2 instanceof Interactable){
                    if(((Kinematic) obj1).getHitbox().intersects(((Interactable) obj2).hitbox())){
                        ((Interactable) obj2).action(obj1);
                    }
                }
                if (((Kinematic) obj1).getHitbox().intersects(((Kinematic) obj2).getHitbox())) {
                    if (obj1 instanceof Weapon && obj2 instanceof Player) {
                        GameEngine.players.get(0).addItem((Weapon) obj1);
                        Debug.log(true, "Weapon Get!");
                    }
                    ((Kinematic) obj1).setAcceleration(new PhysicsVector(1, 1));
                    if (!((Kinematic) obj2).isStatic()) ((Kinematic) obj2).setAcceleration(new PhysicsVector(1, 1));
                    Rectangle intersect = ((Kinematic) obj1).getHitbox().intersection(((Kinematic) obj2).getHitbox());
                    int signX = obj1.getX() < obj2.getX() + ((Kinematic) obj2).getHitbox().width / 2 ? -1 : 1;
                    int signY = obj1.getY() < obj2.getY() + ((Kinematic) obj2).getHitbox().height / 2 ? -1 : 1;

                    if (intersect.height > .5 && intersect.width > .5) {
                        if (((Kinematic) obj2).isStatic()) {
                            if(intersect.width > intersect.height)
                                if(signY == -1 && obj1 instanceof Player) ((Player) obj1).grounded = true;
                            if (intersect.height < intersect.width)
                                obj1.setY(obj1.getY() + intersect.height * signY);
                            else obj1.setX(obj1.getX() + intersect.width * signX);
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
