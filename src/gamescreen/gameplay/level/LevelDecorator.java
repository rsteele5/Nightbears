package gamescreen.gameplay.level;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldUI;

public class LevelDecorator extends GameScreen {
    private static Level lBuild;
    private OverworldUI UI;

    private LevelDecorator(ScreenManager screenManager) {
        super(screenManager, "LevelDecorator", 1f);
    }

    public static LevelDecorator create(ScreenManager screenManager, Level levelBuild) {
        lBuild = levelBuild;
        return new LevelDecorator(screenManager);
    }

    @Override
    protected void initializeScreen() {
        UI = new OverworldUI(screenManager, this);
        addOverlay(UI);
        lBuild.buildBackground(this);
        lBuild.buildTerrain(this);
        lBuild.buildPlayer(this);
        setCamera(new Camera(screenManager,this, GameEngine.players.get(0)));
    }
}
