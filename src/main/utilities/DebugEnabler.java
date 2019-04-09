package main.utilities;

import input.listeners.Key.KeyHandler;

/**
 * Holds booleans that enable or disable Debug functions throughout the program.
 */
public class DebugEnabler {
    /**
     * Controls the state of all logs.
     * @see Debug
     */
    public static boolean LOGGING_ACTIVE =        true;
    /**
     * Controls debugging of Garbage Collection. (Only used in Debug)
     * @see Debug
     */
    static boolean GARBAGE_COLLECTION =           true;
    /**
     * Controls Debug functions that draw to the screen.
     * @see Debug
     */
    public static boolean DRAWING_ACTIVE =        false;
    /**
     * ONLY FOR USE ON NON-CONCRETE TESTING CODE.<br>
     * Please remove once testing is complete.
     */
    public static boolean TEST_LOG =              true;
    /**
     * Controls debugging the RenderEngine.
     * @see gameengine.rendering.RenderEngine
     */
    public static boolean RENDER_ENGINE =         true;
    /**
     * Controls debugging GameScreens.
     * @see gamescreen.GameScreen
     */
    public static boolean GAME_SCREEN_LOG =       true;
    /**
     * Controls debugging RenderableObjects.
     * @see gameobject.renderable.RenderableObject
     */
    public static boolean RENDERABLE_LOG =        true;
    /**
     * Controls debugging Buttons.
     * @see gameobject.renderable.button.Button
     */
    public static boolean BUTTON_LOG =            true;
    /**
     * Controls debugging The GameEngine.
     * @see gameengine.GameEngine
     */
    public static boolean GAME_ENGINE =           false;
    /**
     * Controls debugging The FPS when it reaches below the desired results.
     * @see gameengine.GameEngine
     */
    public static boolean FPS_WARNING =           false;
    /**
     * Controls debugging The current FPS.
     * @see gameengine.GameEngine
     */
    public static boolean FPS_CURRENT =           true;
    /**
     * Controls debugging Loadables and Loading.
     * @see Loadable
     * @see gamescreen.GameScreen
     */
    public static boolean LOADING =               false;
    /**
     * Controls debugging the player and its components
     * @see gameobject.renderable.player.Player
     */
    public static boolean PLAYER_STATUS =         true;
    /**
     * Controls debugging the GameData.
     * @see gameengine.gamedata.GameData
     */
    public static boolean GAME_DATA =             true;
    /**
     * Controls debugging the Overworld and its components.
     * @see gamescreen.gameplay.overworld.OverworldScreen
     */
    public static boolean OVERWORLD =             true;
    /**
     * Controls debugging Collisions?
     */
    public static boolean COLLISION =             true;
    /**
     * Controls debugging GridContainer
     * @see gameobject.container.GridContainer
     */
    public static boolean GRID_CONTAINER =        false;
    /**
     * Controls debugging Key Events
     * @see KeyHandler
     */
    public static boolean KEY_EVENTS =            false;
}
