package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.PhysicsVector;
import gameobject.Boundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class Walker extends Minion {
    public Walker(int x, int y, DrawLayer drawLayer, float speed) {
        super(x, y, "/assets/enemies/minions/walker/walker.png", drawLayer, speed);
    }
//    public Walker() {
//    }
//
//    public Walker(int x, int y) {
//        super(x, y);
//    }
//
//    public Walker(int x, int y, DrawLayer layer) {
//        super(x, y, layer);
//    }
//
//    public Walker(int x, int y, BufferedImage image, DrawLayer layer) {
//        super(x, y, image, layer);
//    }
//
//    public Walker(int x, int y, String imagePath, DrawLayer layer) {
//        super(x, y, imagePath, layer);
//        hp = 10;
//    }
//
//    public Walker(int x, int y, String imagePath, DrawLayer layer, float alpha) {
//        super(x, y, imagePath, layer, alpha);
//    }
//
//    @Override
//    public void update() {
//        state.doAction(this);
//    }
//
//    @Override
//    public boolean isStatic() {
//        return false;
//    }
//
//    @Override
//    public PhysicsVector getVelocity() {
//        return super.getVelocity();
//    }
//
//    @Override
//    public void setVelocity(PhysicsVector pv) {
//        super.setVelocity(pv);
//    }
//
//    @Override
//    public PhysicsVector getAcceleration() {
//        return super.getAcceleration();
//    }
//
//    @Override
//    public void setAcceleration(PhysicsVector pv) {
//        super.setAcceleration(pv);
//    }
//
//    @Override
//    public Rectangle getHitbox() {
//        return super.getHitbox();
//    }
//
    @Override
    public void changeState()
    {
        switch (state.getState()){
            case "Walk Left" :
                image = flipVertical(image);
                state = new WalkRightMS();
                break;
            case "Walk Right" :
                image = flipVertical(image);
                state = new WalkLeftMS();
                break;
        }
    }

    @Override
    public void update() {
        state.doAction(this);
    }

    @Override
    public boolean collide(Collidable c2) {
        //Debug.log(true, "Colliding with: " + c2.getClass().toString());
        if(!(c2 instanceof Boundary))
            changeState();
        if(c2 instanceof Player) {
            if(((Player) c2).isAttacking()){
                //Damage function here hp - c2.getWeaponDamage()
                Debug.success(true, "Walker took some damage!!!!");
            }
        }
        return true;
    }
}
