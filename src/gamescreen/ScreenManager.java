package gamescreen;

import gameengine.gamedata.GameData;
import gameengine.physics.Kinematic;
import gamescreen.splashscreen.LoadingScreen;
import gamescreen.splashscreen.TeamSplashScreen;
import input.listeners.KeyHandler;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The ScreenManager manages how screens are added to our game.
 * It also facilitates updating and draw the various screens.
 * Additionally the ScreenManager provides methods to send
 * MouseEvents to the screens it is managing.
 */
public class ScreenManager extends JPanel {

    private GameScreen rootScreen;
    private GameScreen loadingScreen;
    private GameData gameData;

    /**
     * Constructs a ScreenManager. This method also initializes a loading
     * screen as well as creates the first screen of our game and add sets
     * it as the root screen. It takes as a parameter a GameData instance
     * to provide its screens with the access to the GameData
     * @param gameData Instance of our games GameData
     */
    public ScreenManager(GameData gameData) {
        this.gameData = gameData;
        loadingScreen = new LoadingScreen(this);
        loadingScreen.initializeScreen();
        loadingScreen.loadContent();
        addScreen(new TeamSplashScreen(this));
    }

    /**
     * Returns the ScreenManager's GameData instance
     * @return The ScreenManager's GameData instance
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     * Returns a reusable loading screen that a GameScreen
     * can display while it is loading
     * @return The ScreenManager's LoadingScreen instance
     */
    LoadingScreen getLoadingScreen() {
        return (LoadingScreen)loadingScreen;
    }

    /**
     * Returns a list of the root screens Kinematic objects
     * @return The root screens Kinematic objects
     */
    public ArrayList<Kinematic> getPhysicsObjects() {
        return rootScreen.getPhysicsObjects();
    }

    /**
     * Updates the screens the ScreenManager is managing
     */
    public void update() {
        rootScreen.update();
    }

    /**
     * Draws the screens the ScreenManager is managing
     */
    public void draw(Graphics2D graphics) {
        rootScreen.drawScreen(graphics);
    }

    /**
     * Adds a GameScreen to the ScreenManager. The screen is first
     * initialized, then it has its content loaded before it is added
     * to the ScreenManager.
     * @param screen The screen to be added
     */
    public void addScreen(GameScreen screen) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,  "ScreenManager - Add " + screen.name);
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, screen.name + " - is initializing");
        screen.initializeScreen();
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, screen.name + " - initialized");
        screen.loadContent();
        if(screen.isRoot){
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, screen.name + " - is a Root");
            screen.setChildScreen(rootScreen);
            rootScreen = screen;
            rootScreen.removeMe(rootScreen.getChildScreen());
        } else {
            rootScreen.coverWith(screen);
        }
    }

    public void mousePressedAtLocation(MouseController mouseController, int x, int y){
        if(rootScreen.handleMousePress(mouseController, x,y)){
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager handled this press");
        }
    }

    public void mouseReleasedAtLocation(MouseController mouseController, int x, int y){
        if(rootScreen.handleMouseRelease(mouseController, x,y)){
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager handled this release");
        }
    }

    /**
     * Tells the root screen it has been clicked at location (x,y)
     * @param x The x position of the mouse click
     * @param y The y position of the mouse click
     */
    public void clickEventAtLocation(int x, int y) {
        if(rootScreen.handleClickEvent(x,y))
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager handled this click");
    }

    //TODO change how screens handle graphics changes
    public void changeGraphics() {
        rootScreen = null;
    }

    public void handleKeyPressed(KeyEvent e) {
        Debug.success(true, "Key controller handle this press " + e.getKeyCode());
        rootScreen.handleKeyPressed(e);
    }

    public void handleKeyReleased(KeyEvent e) {
        Debug.success(true, "Key controller handle this release " + e.getKeyCode());
        rootScreen.handleKeyReleased(e);
    }
}
