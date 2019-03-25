package gamescreen.gameplay.level;

import gameengine.gamedata.PlayerData;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;

public interface Level {
    void buildBackground(GameScreen gameScreen);
    void buildTerrain(GameScreen gameScreen);
    Player buildPlayer(GameScreen gameScreen, PlayerData playerData);
    void buildEnemies(GameScreen gameScreen);
}
