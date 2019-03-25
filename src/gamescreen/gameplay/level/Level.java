package gamescreen.gameplay.level;

import gamescreen.GameScreen;

public interface Level {
    /**
     * Constructs the background of a level
     * @param gameScreen the screen that the background is drawn on
     */
    void buildBackground(GameScreen gameScreen);

    /**
     * Constructs the terrain, such as floors and obstacles, of a level
     * @param gameScreen the screen that the terrain is rendered on
     */
    void buildTerrain(GameScreen gameScreen);

    /**
     * Constructs the player(s) for a level based on saved information such as
     * attributes and equipment
     * @param gameScreen the screen that the player(s) is/are rendered on
     */
    void buildPlayer(GameScreen gameScreen);

    /**
     * Constructs the enemies for a level
     * @param gameScreen the screen that the enemies are rendered on
     */
    void buildEnemies(GameScreen gameScreen);
}
