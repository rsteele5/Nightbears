package gamescreen.gameplay.level;

import gameengine.GameEngine;
import gameengine.rendering.Camera;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldUI;

public class LevelFactory extends GameScreen {
    private static Level lBuild;
    private Player player;
    private OverworldUI UI;

    private LevelFactory(ScreenManager screenManager) {
        super(screenManager, "LevelFactory", 1f);
    }

    /**
     * <p>Initialize variables needed before level construction can begin
     *    and create a LevelFactory object</p>
     * @param screenManager the object that instructs how drawables are added/removed from the screen
     * @param levelBuild the object that is the level that is being built
     * @return an instantiation of LevelFactory
     */
    public static LevelFactory create(ScreenManager screenManager, Level levelBuild) {
        lBuild = levelBuild;
        return new LevelFactory(screenManager);
    }

    @Override
    protected void initializeScreen() {
        player = lBuild.buildPlayer(this, gameData.getPlayerData());
        UI = new OverworldUI(screenManager, this, player);
        addOverlay(UI);
        lBuild.buildBackground(this);
        lBuild.buildTerrain(this);
        lBuild.buildEnemies(this);
        setCamera(new Camera(screenManager, this, player));
    }
}
