package gamescreen.gameplay.level;

import gameengine.rendering.Camera;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.house.overworld.room.SpawnType;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.gameplay.overworld.OverworldUI;

public class LevelDecorator extends GameScreen {
    private static Level lBuild;
    private OverworldUI UI;

    private LevelDecorator(ScreenManager screenManager) {
        super(screenManager, "LevelDecorator", true, 1f);
    }

    public static LevelDecorator create(ScreenManager screenManager, Level levelBuild) {
        lBuild = levelBuild;
        return new LevelDecorator(screenManager);
    }

    @Override
    protected void initializeScreen() {

        lBuild.buildBackground(this);
        lBuild.buildTerrain(this);
        Player player = lBuild.buildPlayer(this, gameData.getPlayerData());
        lBuild.buildEnemies(this);
        UI = new OverworldUI(screenManager, this, player, new SpawnPoint(0,0, SpawnType.Vendor));
        addOverlay(UI);
        setCamera(new Camera(screenManager, this, player));
    }
}
