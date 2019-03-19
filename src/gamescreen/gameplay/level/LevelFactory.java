package gamescreen.gameplay.level;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldUI;

public class LevelFactory extends GameScreen {
    private static Level lBuild;
    private OverworldUI UI;

    private LevelFactory(ScreenManager screenManager) {
        super(screenManager, "LevelFactory", 1f);
    }

    public static LevelFactory create(ScreenManager screenManager, Level levelBuild) {
        lBuild = levelBuild;
        return new LevelFactory(screenManager);
    }

    @Override
    protected void initializeScreen() {
        UI = new OverworldUI(screenManager, this);
        addOverlay(UI);
        lBuild.buildBackground(this);
        lBuild.buildTerrain(this);
        lBuild.buildPlayer(this);
        lBuild.buildEnemies(this);
        setCamera(new Camera(screenManager, this, GameEngine.players.get(0)));
    }
}
