package gameengine.physics;

import gameengine.gamedata.GameData;
import gameengine.gamedata.PlayerData;
import gameobject.GameObject;
import gameobject.renderable.player.Player;
import gamescreen.ScreenManager;

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
                continue;
            GameObject obj1 = (GameObject) objects.get(i1);

            Kinematic kObj1 = objects.get(i1);
            if(playerState == Player.PlayerState.sideScroll)
                if ((kObj1.getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                    kObj1.setAcceleration(kObj1.getAcceleration().add(new PhysicsVector(0, PhysicsMeta.Gravity)));

            kObj1.move();
            for (int i2 = 0; i2 < indices; i2++) {
                if (i2 == i1) continue;
                GameObject obj2 = (GameObject) objects.get(i2);
                Kinematic kObj2 = objects.get(i2);

                //Interactable
                if(obj1 instanceof Interactable) {
                    if (obj2 instanceof Interactable) {
                        Interactable iObj1 = (Interactable) obj1;
                        Interactable iObj2 = (Interactable) obj2;
                        if (iObj1.getRequestArea().intersects(iObj2.getRequestArea()) && iObj1.isRequesting()) {
                            iObj2.action(obj1);
                            iObj1.setRequesting(false);
                        }
                    }
                }

                if (kObj1.getHitbox().intersects(kObj2.getHitbox())) {

                    kObj1.setAcceleration(new PhysicsVector(1, 1));
                    if (!kObj2.isStatic())
                        kObj2.setAcceleration(new PhysicsVector(1, 1));

                    Rectangle intersect = kObj1.getHitbox().intersection(kObj2.getHitbox());
                    int signX = kObj1.getX() < obj2.getX() + kObj2.getHitbox().width / 2 ? -1 : 1;
                    int signY = kObj1.getY() < obj2.getY() + kObj2.getHitbox().height / 2 ? -1 : 1;

                    if (intersect.height > .5 && intersect.width > .5) {
                        if (kObj2.isStatic()) {
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
                }
            }
        }
    }
}
