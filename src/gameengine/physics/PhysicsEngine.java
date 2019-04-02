package gameengine.physics;

import gameobject.renderable.player.Player;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine {

    private PhysicState physicState;
    private CollisionManager collisionManager;
    private Player player;
    public enum PhysicState{
        TopDown,
        SideScroll
    }

    public PhysicsEngine(Player player, PhysicState state) {
        physicState = state;
        collisionManager = new CollisionManager();
        this.player = player;
    }

    public void setPhysicState(PhysicState ps){
        physicState = ps;
    }

    public void update(ArrayList<Collidable> collidables, ArrayList<Kinematic> kinematics,
                       ArrayList<Interactable> interactables) {

        this.applyPhysics(kinematics);
        this.resolveCollisions(collisionManager.detectCollisions(collidables));
        collisionManager.procressInteractions(player, interactables);
    }

    private void applyPhysics(ArrayList<Kinematic> kinematics){
        if(!kinematics.isEmpty()) {
            kinematics.forEach(k -> {
                if (physicState == PhysicState.SideScroll)
                    if ((k.getAcceleration().y + PhysicsMeta.Gravity) < PhysicsMeta.terminalVelocity)
                        k.setAcceleration(k.getAcceleration().add(new PhysicsVector(0, PhysicsMeta.Gravity)));
                k.move();
            });
        }
    }

    private void resolveCollisions(ArrayList<CollisionEvent> events) {
        events.forEach(event -> {
            if(event.collider instanceof Kinematic){
                Kinematic ckinematic = (Kinematic)event.collider;
                double x = 0, y = 0;
                for(int i = 0; i < event.collidedWith.size(); i++){
                    Rectangle intersection = event.intersections.get(i);
                    //Direction
                    double angleDeg = Math.atan2(
                            intersection.getY() - event.collider.getCollisionBox().getY(),
                            intersection.getX() - event.collider.getCollisionBox().getX());
                    //Get The X and Y components
                    if(intersection.width > intersection.height){
                        if(Math.abs(y) < intersection.height){
                            y = intersection.height;
                            double yComp = Math.round(Math.sin(angleDeg));
                            if (Double.isNaN(yComp)) yComp = 0;
                            y *= yComp;
                        }
                    } else if(Math.abs(x) < intersection.width) {
                        x = intersection.width;
                        double xComp = Math.round(Math.cos(angleDeg));
                        if (Double.isNaN(xComp)) xComp = 0;
                        x *= xComp;
                    }
                }
                ckinematic.move(new PhysicsVector(x,y));
            }
            event.sendCollision();
        });
    }
}
