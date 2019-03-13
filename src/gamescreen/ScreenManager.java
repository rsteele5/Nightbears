package gamescreen;

import gameengine.gamedata.GameData;
import gameengine.physics.Kinematic;
import gamescreen.splashscreen.LoadingScreen;
import gamescreen.splashscreen.TeamSplashScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScreenManager extends JPanel {
    //region <Variables>
    private GameScreen rootScreen;
    private LoadingScreen loadingScreen;
    private GameData gameData;
    //endregion
    //region <Getters and Setters>

    //endregion
    public ScreenManager(GameData gameData) {
        this.gameData = gameData;
        loadingScreen = new LoadingScreen(this);
        rootScreen = new TeamSplashScreen(this);
    }

    public void update() {
        rootScreen.update();
    }

    //region <Support Functions>
    public void addScreen(GameScreen screen) {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,  "ScreenManager - Add " + screen.name);
        if(screen.isRoot){
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, screen.name + " - is a Root");
            screen.setChildScreen(rootScreen);
            rootScreen = screen;
            rootScreen.removeMe(rootScreen.getChildScreen());
        } else {
            rootScreen.coverWith(screen);
        }
    }

    public void clickEventAtLocation(int x, int y) {
        if(rootScreen.handleClickEvent(x,y))
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, "ScreenManager handled this click");
    }

    public void draw(Graphics2D graphics) {
        rootScreen.drawScreen(graphics);
    }

    LoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    public ArrayList<Kinematic> getPhysicsObjects() {
        return rootScreen.getPhysicsObjects();
    }

    public GameData getGameData() {
        return gameData;
    }
    //endregion
}
