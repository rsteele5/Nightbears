package gamescreen.gameplay;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.util.ArrayList;

public abstract class GamePlayScreen extends GameScreen {

    protected Player player;
    /**
     * Contains all of the active {@link Kinematic} objects on the GameScreen
     */
    public ArrayList<Kinematic> kinematics;
    /**
     * Contains all of the active {@link Interactable} objects on the GameScreen
     */
    public ArrayList<Interactable> interactables;


    /**
     * Used to construct a root screen with a particular alpha value. To easily override the default
     * {@link #transitionOn()} function, set the alpha value to 1f. Root screens will always be anchored at (0,0).
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param screenAlpha   Starting alpha value of all of the GameScreen's renderables.
     */
    public GamePlayScreen(ScreenManager screenManager, String name, float screenAlpha, Player player) {
        super(screenManager, name, screenAlpha);
        this.player = player;
        kinematics = new ArrayList<>();
        interactables = new ArrayList<>();
    }
}
