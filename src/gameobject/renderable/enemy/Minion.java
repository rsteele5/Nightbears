package gameobject.renderable.enemy;


import gameengine.physics.PhysicsVector;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Minion extends Enemy /*implements Collidable, Kinematic, Interactable*/ {



    protected EnemyState state;
    protected PhysicsVector accel = new PhysicsVector(0,1);
    protected PhysicsVector movement = new PhysicsVector(0, 0);
    private GameScreen screen;

    public Minion(int x, int y, String path, DrawLayer drawLayer, float speed, int hp) {
        super(x, y, path, drawLayer, speed, hp);
    }

    /**
     * Returns true or false depending on the acceptance of the state transition.
     */
    public boolean setState(EnemyState state) {

        this.state = state;
        //TODO: Implement error checking
        return true;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        // add stuff to Screen here
    }

    @Override
    public void changeState(){
    }

    protected BufferedImage flipVertical(BufferedImage src){
        AffineTransform tx=AffineTransform.getScaleInstance(-1.0,1.0);  //scaling
        tx.translate(-src.getWidth(),0);  //translating
        AffineTransformOp tr=new AffineTransformOp(tx,null);  //transforming

        return tr.filter(src, null);  //filtering
    }

    protected BufferedImage flipHorizontal(BufferedImage src){
        AffineTransform tx=AffineTransform.getScaleInstance(1.0,-1.0);  //scaling
        tx.translate(0,-src.getHeight());  //translating
        AffineTransformOp tr=new AffineTransformOp(tx,null);  //transforming

        return tr.filter(src, null);  //filtering
    }

    public GameScreen getScreen() {
        return screen;
    }

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    public void attack(){

    }

}
