package gamescreen;

import gameengine.gamedata.GameData;
import gameengine.physics.Kinematic;
import gameengine.rendering.Camera;
import input.listeners.KeyHandler;
import input.listeners.MouseController;
import main.utilities.*;
import gameobject.GameObject;
import gameobject.renderable.RenderableObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Contains
 */
public abstract class GameScreen {

    //region <Variables>
    public String name;
    protected int x, y;
    protected float screenAlpha;
    private ScreenState previousState;
    protected ScreenState currentState;
    protected ScreenManager screenManager;
    private GameScreen childScreen;
    private CopyOnWriteArrayList<GameScreen> overlayScreens;
    private Camera camera;
    private boolean loadingScreenRequired = false;
    private boolean isExclusive;
    private boolean isOverlay;
    private boolean isLoading;
    protected boolean exiting = false;
    boolean isRoot;
    private KeyHandler keyHandler;
    /**
     * Contains all of the {@link GameObject}s that are not updated and/or drawn on the GameScreen
     */
    public ArrayList<GameObject> inactiveObjects;
    /**
     * Contains all of the {@link GameObject}s that are updated and/or drawn on the GameScreen
     */
    public ArrayList<GameObject> activeObjects;
    /**
     * Contains all of the active {@link RenderableObject}s on the GameScreen
     */
    public ArrayList<RenderableObject> renderables;
    /**
     * Contains all of the active {@link Clickable}s on the GameScreen
     */
    public ArrayList<Clickable> clickables;
    /**
     * Contains all of the active {@link Kinematic} objects on the GameScreen
     */
    public ArrayList<Kinematic> kinematics;
    /**
     * Contains all objects that need to be loaded. The objects are removed from this list once they are loaded.
     */
    public ArrayList<Loadable> loadables;

    protected GameData gameData;

    /**
     * <p>Screen state describes all possible states that a GameScreen can be in:</p>
     * <p><b>TransitionOn</b> - The GameScreen is currently undergoing transition on effects. ie fade in etc</p>
     * <p><b>Active</b> - The GameScreen is currently active and can accept input and update its objects</p>
     * <p><b>TransitionOff</b> - The GameScreen is currently undergoing transition off effects. ie fade in etc</p>
     * <p><b>Hidden</b> - The GameScreen is currently covered by another GameScreen</p>
     */
    public enum ScreenState {
        TransitionOn,
        Active,
        TransitionOff,
        Hidden
    }
    //endregion

    //region<Construction and Initialization>
    private GameScreen(boolean isRoot, ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos, float screenAlpha) {
        this.name = name;
        x = xPos;
        y = yPos;
        this.screenAlpha = screenAlpha;
        previousState = null;
        currentState = ScreenState.TransitionOn;
        this.screenManager = screenManager;
        childScreen = null;
        overlayScreens = new CopyOnWriteArrayList<>();
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
        isLoading = true;
        this.isRoot = isRoot;
        //GameObjects
        activeObjects = new ArrayList<>();
        inactiveObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        renderables = new ArrayList<>();
        this.gameData = screenManager.getGameData();
    }
    /* Only for root GameScreens */

    /**
     * Used to construct a root screen with a particular alpha value. To easily override the default
     * {@link #transitionOn()} function, set the alpha value to 1f. Root screens will always be anchored at (0,0).
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param screenAlpha   Starting alpha value of all of the GameScreen's renderables.
     */
    public GameScreen(ScreenManager screenManager, String name, float screenAlpha) {
        this(true, screenManager, name, false, 0, 0, screenAlpha);
    }
    /**
     * Used to construct a default root screen. The alpha value is set to 0 to utilize the default
     * {@link #transitionOn()} function. Root screens will always be anchored at (0,0).
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     */
    public GameScreen(ScreenManager screenManager, String name) {
        this(screenManager, name, 0f);
    }

    /* Only for non-root GameScreens */

    /**
     * Used to construct exclusive and non exclusive popups that render on top of the Root screen and potentially
     * other GameScreens. The GameScreen will be anchored at a specified xPos and yPos with a particular alpha value.
     * To easily override the default {@link #transitionOn()} function, set the alpha value to 1f.
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param isExclusive   <b>True:</b>  the Screen will stop {@link #update()} and {@link #drawScreen(Graphics2D)}
     *                      on all GameScreens covered by this GameScreen.
     *                      <br><b>False:</b> The screen will behave like an Overlay. I would recommend using the
     *                      {@link Overlay} class instead of setting this to false.
     * @param xPos          x position relative to the top left corner of the {@link main.GameWindow}.
     * @param yPos          y position relative to the top left corner of the {@link main.GameWindow}.
     * @param screenAlpha   Starting alpha value of all of the GameScreen's renderables.
     */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos, float screenAlpha) {
        this(false, screenManager, name, isExclusive, xPos, yPos, screenAlpha);
    }

    /**
     * Used to construct exclusive and non exclusive popups that render on top of the Root screen and potentially
     * other GameScreens. The GameScreen will be anchored at a specified xPos and yPos. The alpha value is set to 0 to
     * utilize the default {@link #transitionOn()} function.
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param isExclusive   <b>True:</b>  the Screen will stop {@link #update()} and {@link #drawScreen(Graphics2D)}
     *                      on all GameScreens covered by this GameScreen.
     *                      <br><b>False:</b> The screen will behave like an Overlay. I would recommend using the
     *                      {@link Overlay} class instead of setting this to false.
     * @param xPos          x position relative to the top left corner of the {@link main.GameWindow}.
     * @param yPos          y position relative to the top left corner of the {@link main.GameWindow}.
     */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos) {
        this(screenManager, name, isExclusive, xPos, yPos, 0f);
    }

    /**
     * Used to construct exclusive and non exclusive popups that render on top of the Root screen and potentially
     * other GameScreens. The GameScreen will be anchored at (0,0) with a particular alpha value. To easily override
     * the default {@link #transitionOn()} function, set the alpha value to 1f.
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param isExclusive   <b>True:</b>  the Screen will stop {@link #update()} and {@link #drawScreen(Graphics2D)}
     *                      on all GameScreens covered by this GameScreen.
     *                      <br><b>False:</b> The screen will behave like an Overlay. I would recommend using the
     *                      {@link Overlay} class instead of setting this to false.
     * @param screenAlpha   Starting alpha value of all of the GameScreen's renderables.
     */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, float screenAlpha) {
        this(screenManager, name, isExclusive, 0, 0, screenAlpha);
    }
    /**
     * Used to construct exclusive and non exclusive popups that render on top of the Root screen and potentially
     * other GameScreens. The GameScreen will be anchored at (0,0). The alpha value is set to 0 to
     * utilize the default {@link #transitionOn()} function.
     *
     * @param screenManager The Manager for this GameScreen.
     * @param name          Name of the GameScreen (Used for {@link Debug}ing).
     * @param isExclusive   <b>True:</b>  the Screen will stop {@link #update()} and {@link #drawScreen(Graphics2D)}
     *                      on all GameScreens covered by this GameScreen.
     *                      <br><b>False:</b> The screen will behave like an Overlay. I would recommend using the
     *                      {@link Overlay} class instead of setting this to false.
     */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive) {
        this(screenManager, name, isExclusive, 0f);
    }



    /**
     * Initializes all of the contents and functionality of the GameScreen
     */
    protected abstract void initializeScreen();

    /**
     * Loads the contents of the GameScreen.
     */
    protected void loadContent() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + " - Load start");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> {
            for (Loadable loadable : loadables) {
                Debug.log(DebugEnabler.LOADING, name + " - Loading: " + loadable.getClass().getName());
                loadable.load();
            }
            loadables.clear();
            isLoading = false;
            scaleScreen();
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + " - Loaded");
            setScreenAlpha(screenAlpha);
        });

        executorService.shutdown();
    }
    //endregion

    //region <Getters and Setters>
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }

    public void setChildScreen(GameScreen screen) {
        childScreen = screen;
    }
    public GameScreen getChildScreen() {
        return childScreen;
    }

    protected void setScreenAlpha(float alpha){
        screenAlpha = alpha;
        for(RenderableObject renderable : renderables)
            renderable.setAlpha(screenAlpha);
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public void setKeyHandler(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
    }

    public ArrayList<RenderableObject> getRenderables() {
        return renderables;
    }

    ArrayList<Kinematic> getPhysicsObjects() {
        if (!isLoading) {
            if (childScreen != null) {
                return childScreen.getPhysicsObjects();
            } else {
                return kinematics;
            }
        } else {
            return null;
        }
    }

    public boolean isLoadingScreenRequired() {
        return loadingScreenRequired;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public boolean isLoading() {
        return isLoading;
    }

    /**
     * Returns true if a splashscreen is active and can accept input or updates
     */
    public boolean isActive() {
        return currentState == ScreenState.Active;
    }

    public boolean isHidden() {
        return currentState == ScreenState.Hidden;
    }

    public boolean isExiting() {
        return exiting;
    }
    //endregion

    //region <Update>
    protected void transitionOn() {
        if(screenAlpha < 0.9f){
            screenAlpha += 0.05f;
            setScreenAlpha(screenAlpha);
            for(GameScreen ov : overlayScreens){
                ov.setScreenAlpha(screenAlpha);
            }
        } else {
            setScreenAlpha(1.0f);
            for(GameScreen ov : overlayScreens){
                ov.setScreenAlpha(1.0f);
            }
            currentState = ScreenState.Active;
        }
    }

    protected void transitionOff() {
        if(screenAlpha > 0.075f){
            screenAlpha -= 0.07f;
            setScreenAlpha(screenAlpha);
            for(GameScreen ov : overlayScreens){
                ov.setScreenAlpha(screenAlpha);
            }
        } else {
            exiting = true;
        }
    }

    //Override if you know what ur doing
    protected void hiddenUpdate() {}

    protected void activeUpdate() {
        for(GameObject activeObject: activeObjects){
            activeObject.update();
        }
    }

    /**
     *  Updates the state of the screen
     */
    public void update(){
        // If I have an Exclusive child screen on top on me
        if(childScreen != null) {
            if(childScreen.isExiting()){
                if(!isLoading){
                    childScreen = null;
                }
            } else {
                childScreen.update();
            }
        } else {
            if(!isLoading){
                switch(currentState) {
                    case TransitionOn: transitionOn(); break;
                    case TransitionOff: transitionOff(); break;
                    case Active: activeUpdate(); break;
                    case Hidden: hiddenUpdate(); break;
                    default: Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Unknown splashscreen state");
                }
            }
            if(!overlayScreens.isEmpty()) {
                for (GameScreen overlay : overlayScreens) {
                    if(overlay.isExiting()){
                        if(!overlay.isLoading)
                            overlayScreens.remove(overlay);
                    }else overlay.update();
                }
            }

            if(currentState != previousState){
                previousState = currentState;
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-CurrentState: " + currentState.name());
            }
        }
    }

    final void drawScreen(Graphics2D graphics) {
        if(!isLoading) {
            if(camera != null){
                camera.track(graphics);
            } else drawLayers(graphics);

            if(!overlayScreens.isEmpty()) {
                for (GameScreen overlay : overlayScreens) {
                    if(!overlay.isLoading)
                        overlay.drawScreen(graphics);
                }
            }
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }

        } else {
            if(childScreen != null) {
                childScreen.drawScreen(graphics);
            }
        }
    }

    private void drawLayers(Graphics2D graphics) {
        for(RenderableObject renderable : renderables)
            renderable.draw(graphics);
    }
    //endregion

    //region <Support Functions>
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        //Handle press on the Exlusive splashscreen covering me
        if(childScreen != null) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle press on child");
            childScreen.handleMousePress(mouseController, x,y);
        } else {
            //Handle press on all overlays
            for (GameScreen overlay : overlayScreens) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle press on overlay");
                if (overlay.handleMousePress(mouseController, x, y))
                    return true;
            }
            // If no overlays handled press
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle press " + x + " " + y);
            for(Clickable thing: clickables) {
                if(thing.contains(x,y)) {
                    mouseController.setPressTarget(thing);
                    thing.setPressed(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void handleKeyPressed(KeyEvent e){
        if(keyHandler != null){
            Debug.success(true, "Screen handle this press " + e.getKeyCode());
            this.keyHandler.keyPressed(e);
        } else {
            Debug.criticalError("No key handler attached to screen!");
        }

    }

    public void handleKeyReleased(KeyEvent e){
        if(keyHandler != null){
            Debug.success(true, "Screen handle this release " + e.getKeyCode());
            this.keyHandler.keyReleased(e);
        } else {
            Debug.criticalError("No key handler attached to screen!");
        }

    }


    public boolean handleMouseRelease(MouseController mouseController, int x, int y){
        //Handle release on the Exlusive splashscreen covering me
        if(childScreen != null) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle release on child");
            childScreen.handleMouseRelease(mouseController, x,y);
        } else {
            //Handle release on all overlays
            for (GameScreen overlay : overlayScreens) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle release on overlay");
                if (overlay.handleMouseRelease(mouseController, x, y))
                    return true;
            }
            // If no overlays handled press
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle release " + x + " " + y);
            for(Clickable thing: clickables) {
                if(thing.equals(mouseController.getPressTarget())) {
                    if(thing.contains(x,y)){
                        thing.onClick();
                        mouseController.clearPressTarget();
                        thing.setPressed(false);
                        return true;
                    } else {
                        mouseController.clearPressTarget();
                        thing.setPressed(false);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean handleClickEvent(int x, int y) {
        //Handle click on the Exlusive splashscreen covering me
        if(childScreen != null) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on child");
            childScreen.handleClickEvent(x,y);
        } else {
            //Handle click on all overlays
            for (GameScreen overlay : overlayScreens) {
                Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "-handle click on overlay");
                if (overlay.handleClickEvent(x, y))
                    return true;
            }
            // If no overlays handled clicks
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
            for(Clickable thing: clickables) {
                if(thing.contains(x,y)) {
                    thing.onClick();
                    return true;
                }
            }
        }
        return false;
    }

    public void reset(){
        previousState = null;
        exiting = false;
    }

    protected void addOverlay(GameScreen overlay){
        if(!overlay.isOverlay){
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,
                    overlay.name +"- is not an overlay. Will not add to overlays.");
        } else {
            overlay.initializeScreen();
            overlay.loadContent();
            overlayScreens.add(overlay);
        }
    }

    protected void coverWith(GameScreen gameScreen) {
        if (gameScreen.isExclusive) {
            if (childScreen == null) {
                childScreen = gameScreen;
            } else {
                childScreen.coverWith(gameScreen);
            }
        } else {
            addOverlay(gameScreen);
        }
    }

    //Recursively removes all child screens and overlays
    void removeMe(GameScreen gameScreen){
        if(gameScreen != null) {
            if (gameScreen.childScreen != null)
                removeMe(gameScreen.childScreen);

            if (!gameScreen.overlayScreens.isEmpty()) {
                for (GameScreen overlay : gameScreen.overlayScreens)
                    removeMe(overlay);
            }
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, gameScreen.name + " - Remove Scheduled");
            gameScreen.currentState = ScreenState.TransitionOff;
        }
        else Debug.warning(DebugEnabler.GAME_SCREEN_LOG,  "Screen is already removed");
    }

    void scaleScreen(){
        for (GameObject gameObject: activeObjects){
            gameObject.scale(gameData.getGraphicsSettings().getScaleFactor());
        }
        for (GameObject gameObject: inactiveObjects){
            gameObject.scale(gameData.getGraphicsSettings().getScaleFactor());
        }
    }
    //endregion
}
