package gameengine.physics;

import gameobject.renderable.enemy.Enemy;
import gameobject.renderable.player.Player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhysicsEngine {

    private PhysicState physicState;
    private CollisionManager collisionManager;
    private Player player;
    public enum PhysicState{
        TopDown,
        SideScroll
    }

    public PhysicsEngine(Player player, PhysicState state) {
        this.player = player;
        collisionManager = new CollisionManager();
        physicState = state;
        switch (state){
            case TopDown:    player.setState(Player.PlayerState.overWorld); break;
            case SideScroll: player.setState(Player.PlayerState.sideScroll); break;
        }
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
                if (physicState == PhysicState.SideScroll) {
                    if (k.getVelocity().y + PhysicsMeta.Gravity.y < PhysicsMeta.terminalVelocity)
                        k.setVelocity(k.getVelocity().add(PhysicsMeta.Gravity));
                } k.move();
            });
        }
    }

    private void resolveCollisions(ArrayList<CollisionEvent> events) {
        events.forEach(event -> {
            if(event.collider instanceof Kinematic){
                PhysicsVector getOut = new PhysicsVector(0,0);
                for(int i = 0; i < event.collidedWith.size(); i++)
                    getOut = getOut.add(collisionReslover(event.collider, event.collidedWith.get(i)));
                ((Kinematic)event.collider).move(getOut);
            }

            event.collidedWith.forEach(collidable -> {
                if(collidable instanceof Kinematic) {
                    ((Kinematic) collidable).move(collisionReslover(collidable, event.collider));
                }
            });
            event.sendCollision();
        });
    }

    private PhysicsVector collisionReslover(Collidable c1, Collidable c2){
        if(c1 instanceof Kinematic) {
            if(c1 instanceof Player && ((Player) c1).isAttacking() && c2 instanceof Enemy)
                return PhysicsVector.ZERO;
            Rectangle intersection = c1.getCollisionBox().intersection(c2.getCollisionBox());
            //Direction
            double angleDeg = Math.atan2(
                    intersection.getCenterY() - c1.getCollisionBox().getCenterY(),
                    intersection.getCenterX() - c1.getCollisionBox().getCenterX());
            //X and Y directions
            double yComp, xComp;
            if (intersection.width > intersection.height) {
                yComp = -Math.round(Math.sin(angleDeg));
                return new PhysicsVector(0, intersection.height*yComp);
            } else {
                xComp = -Math.round(Math.cos(angleDeg));
                return new PhysicsVector(intersection.width*xComp, 0);
            }
        }
        return PhysicsVector.ZERO;
    }
}
