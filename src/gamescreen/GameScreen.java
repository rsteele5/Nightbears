package gamescreen;

import gameengine.gamedata.GameData;
import gameengine.physics.Kinematic;
import gameengine.rendering.Camera;
import gameobject.renderable.button.Button;
import input.listeners.MouseController;
import main.utilities.*;
import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.splashscreen.LoadingScreen;


import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameScreen {

    //region <Variables>
    protected GameData gameData;
    public String name;
    protected float screenAlpha;

    protected int x, y;

    private GameScreen childScreen;
    protected CopyOnWriteArrayList<GameScreen> overlayScreens;
    public LoadingScreen loadingScreen;
    private Camera camera;

    //TODO: Used for testing, remove after splashscreen management is working. If it so tickles your pickles
    protected ScreenState previousState;

    protected ScreenManager screenManager;

    protected boolean loadingScreenRequired = false;

    /**
     * The variable exclusivePopup describes if a splashscreen is covering a portion of another splashscreen.
     * An exclusive popup splashscreen prevents updates on splashscreen below it in the list.
     */
    protected boolean isExclusive = false;
    protected boolean isOverlay = false;

    /**
     * The variable exclusivePopup describes if a splashscreen is covering a portion of another splashscreen.
     * An exclusive popup splashscreen prevents updates on splashscreen below it in the list.
     */
    private boolean isLoading;

    /**
     * The variable overlay describes if a splashscreen is covering another splashscreen in it's entirety, but
     * does not prevent updates or rendering on splashscreen below it in the list.
     */
    protected boolean isRoot;

    protected boolean exiting = false;

    public ArrayList<GameObject> inactiveObjects;
    public ArrayList<GameObject> activeObjects;
    public ArrayList<Clickable> clickables;
    public ArrayList<Kinematic> kinematics;
    public ArrayList<Loadable> loadables;

    public ArrayList<RenderableObject> getRenderables() {
        return renderables;
    }

    public ArrayList<RenderableObject> renderables;

    public void coverWith(GameScreen gameScreen) {
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

    //Recursively removes all child splashscreen and overlays
    public void removeMe(GameScreen gameScreen){
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


    public ArrayList<Kinematic> getPhysicsObjects() {
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

    public void setChildScreen(GameScreen screen) {
        childScreen = screen;
    }
    public GameScreen getChildScreen() {
        return childScreen;
    }


    /**
     * <p>Screen state describes all possible states that a splashscreen can be in:</p>
     * <p><b>TransitionOn</b> - The splashscreen is currently undergoing transition on effects. ie fade in etc</p>
     * <p><b>Active</b> - The splashscreen is currently active and can accept input and update its objects</p>
     * <p><b>TransitionOff</b> - The splashscreen is currently undergoing transition off effects. ie fade in etc</p>
     * <p><b>Hidden</b> - The splashscreen is currently covered by another splashscreen</p>
     */
    public enum ScreenState {
        TransitionOn,
        Active,
        TransitionOff,
        Hidden
    }

    /**
     * Current state describes what state the splashscreen is currently in.
     */
    protected ScreenState currentState;
    //endregion

    //region<Construction and Initialization>
    public GameScreen(ScreenManager screenManager, String name, float screenAlpha) {
        this.gameData = screenManager.getGameData();
        this.screenManager = screenManager;
        this.name = name;
        this.isRoot = true;
        previousState = null;
        this.screenAlpha = screenAlpha;
        x = 0;
        y = 0;
        overlayScreens = new CopyOnWriteArrayList<>();
        //GameObjects
        activeObjects = new ArrayList<>();
        inactiveObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        renderables = new ArrayList<>();
        isLoading = true;
        currentState = ScreenState.TransitionOn;
    }

    public GameScreen(ScreenManager screenManager, String name) {
        this(screenManager, name, 0f);
    }


    /* Only for non root splashscreen */
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive) {
        this(screenManager, name, isExclusive, 0, 0, 0f);
    }
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, float screenAlpha) {
        this(screenManager, name, isExclusive, 0, 0, screenAlpha);
    }
    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos) {
        this(screenManager, name, isExclusive, xPos, yPos, 0f);
    }

    public GameScreen(ScreenManager screenManager, String name, boolean isExclusive, int xPos, int yPos, float screenAlpha) {
        this.screenManager = screenManager;
        this.gameData = screenManager.getGameData();
        this.name = name;
        this.isRoot = false;
        this.isExclusive = isExclusive;
        this.isOverlay = !isExclusive;
        previousState = null;
        this.screenAlpha = screenAlpha;
        x = xPos;
        y = yPos;
        overlayScreens = new CopyOnWriteArrayList<>();
        //Game Objects
        activeObjects = new ArrayList<>();
        inactiveObjects = new ArrayList<>();
        clickables = new ArrayList<>();
        kinematics = new ArrayList<>();
        loadables = new ArrayList<>();
        renderables = new ArrayList<>();
        currentState = ScreenState.TransitionOn;
        isLoading = true;
    }


    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    protected abstract void initializeScreen();

    /**
     * Loads the contents of this main.Game Screen.
     */
    protected void loadContent() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + " - Load start");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> {
            if (loadingScreenRequired) {
                loadingScreen = screenManager.getLoadingScreen();
                loadingScreen.initializeLoadingScreen(loadables.size());
                coverWith(loadingScreen);
                for (int i = 0; i < loadables.size(); i++) {
                    loadables.get(i).load();
                    loadingScreen.dataLoaded(i);
                }
                isLoading = false;
                childScreen = null;
                loadingScreen.reset();

            } else {
                for (Loadable loadable : loadables) {
                    Debug.log(DebugEnabler.LOADING, name + " - Loading: " + loadable.getClass().getName());
                    loadable.load();
                }
                loadables.clear();
                isLoading = false;
            }
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

    public ScreenState getScreenState() {
        return currentState;
    }

    public void setScreenState(ScreenState state) {
        currentState = state;
    }


    //endregion

    //region <Update>
    protected void transitionOn() {
        if(screenAlpha < 0.9f){
            screenAlpha += 0.05f;
            setScreenAlpha(screenAlpha);
        } else {
            setScreenAlpha(1.0f);
            currentState = ScreenState.Active;
        }
    }

    protected void transitionOff() {
        if(screenAlpha > 0.075f){
            screenAlpha -= 0.07f;
            setScreenAlpha(screenAlpha);
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
     *  Updates the state of the splashscreen
     */
    public void update(){
        // If I have an Exclusive child splashscreen on top on me
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

    public final void drawScreen(Graphics2D graphics) {
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

    protected void drawLayers(Graphics2D graphics) {
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

    protected void setScreenAlpha(float alpha){
        screenAlpha = alpha;
        for(RenderableObject renderable : renderables)
            renderable.setAlpha(screenAlpha);
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public void scaleScreen(){
        for (GameObject gameObject: activeObjects){
            gameObject.scale(gameData.getGraphicsSettings().getScaleFactor());
        }
        for (GameObject gameObject: inactiveObjects){
            gameObject.scale(gameData.getGraphicsSettings().getScaleFactor());
        }
    }
    //endregion
}
