package gameengine.physics;

import gameengine.gamedata.GameData;
import gameobject.GameObject;
import gameobject.renderable.player.Player;
import gamescreen.ScreenManager;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine {

    private ScreenManager screenManager;
    private PhysicState physicState;
    public enum PhysicState{
        TopDown,
        SideScroll
    }

    public PhysicsEngine(ScreenManager myScreenManager) {
        screenManager = myScreenManager;
        physicState = PhysicState.TopDown;
    }

    public void update() {
        if(physicState == null) return;
        ArrayList<Kinematic> kinematics = screenManager.getPhysicsObjects();
        if(kinematics == null) return;
        //Update physics
        kinematics.forEach(k -> {
            if(!k.isStatic()){
                if(physicState == PhysicState.SideScroll)
                    if ((k.getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                        k.setAcceleration(k.getAcceleration().add(new PhysicsVector(0, PhysicsMeta.Gravity)));
                k.move();
            }
        });

        //detect collisions

        //resolveCollisions
    }
}
