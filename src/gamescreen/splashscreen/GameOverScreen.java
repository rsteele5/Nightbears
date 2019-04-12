package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

public class GameOverScreen extends GameScreen {

    public GameOverScreen(ScreenManager screenManager, float screenAlpha) {
        super(screenManager, "GameOverScreen", screenAlpha);
    }

    @Override
    protected void initializeScreen() {
        gameData.clearPlayerData();
        //TODO: add player to end player data

        ImageContainer background = new ImageContainer(0,0,
                "/assets/backgrounds/BG-GameOver.png",
                DrawLayer.Background);
        background.addToScreen(this, true);
    }
}
