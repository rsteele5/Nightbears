package gameobject.renderable.enemy;


import gameengine.physics.PhysicsVector;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Boss extends Enemy /*implements Collidable, Kinematic, Interactable*/ {

    protected EnemyState state;
    protected PhysicsVector accel = new PhysicsVector(0,1);
    protected PhysicsVector movement = new PhysicsVector(0, 0);
    private GameScreen screen;

    public Boss(int x, int y, String path, DrawLayer drawLayer, float speed, int hp) {
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

    public GameScreen getScreen() {
        return screen;
    }

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    protected void attack(){

    }

    @Override
    public void update() {
        state.doAction(this);
        if(hp < 1){} //todo Killself
    }
}
