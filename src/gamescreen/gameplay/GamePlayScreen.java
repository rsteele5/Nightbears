package gamescreen.gameplay;

import gameengine.physics.Collidable;
import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsEngine;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import main.utilities.Debug;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GamePlayScreen extends GameScreen {

    /**
     * Contains all of the active {@link Collidable} objects on the GameScreen
     */
    public ArrayList<Collidable> collidables;
    /**
     * Contains all of the active {@link Kinematic} objects on the GameScreen
     */
    public ArrayList<Kinematic> kinematics;
    /**
     * Contains all of the active {@link Interactable} objects on the GameScreen
     */
    public ArrayList<Interactable> interactables;

    protected PhysicsEngine physicsEngine = null;


    /**
     * Used to construct a root screen with a particular alpha value. To easily override the default
     * {@link #transitionOn()} function, set the alpha value to 1f. Root screens will always be anchored at (0,0).
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param screenAlpha   Starting alpha value of all of the GameScreen's renderables.
     */
    public GamePlayScreen(ScreenManager screenManager, String name, float screenAlpha) {
        super(screenManager, name, screenAlpha);
        collidables = new ArrayList<>();
        kinematics = new ArrayList<>();
        interactables = new ArrayList<>();
    }

    public GamePlayScreen(ScreenManager screenManager, String name, boolean isExclusive, float screenAlpha){
        super(screenManager,name,isExclusive,screenAlpha);
        collidables = new ArrayList<>();
        kinematics = new ArrayList<>();
        interactables = new ArrayList<>();
    }

    protected void setPhysicsEngine(PhysicsEngine pe){
        physicsEngine = pe;
    }

    @Override
    protected void activeUpdate() {
        if(physicsEngine != null) physicsEngine.update(collidables, kinematics, interactables);
        super.activeUpdate();
    }
}
