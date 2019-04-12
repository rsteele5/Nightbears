package gamescreen.splashscreen;

import gameengine.gamedata.EndGamePlayerData;
import gameengine.gamedata.PlayerData;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.time.LocalDate;

public class GameOverScreen extends GameScreen {

    public GameOverScreen(ScreenManager screenManager, float screenAlpha) {
        super(screenManager, "GameOverScreen", screenAlpha);
    }

    @Override
    protected void initializeScreen() {
        PlayerData playerData = gameData.getPlayerData();
        String imagePath = "/assets/player/color/" + playerData.getImageDirectory() + "/Teddy.png";
        EndGamePlayerData deadPlayer = new EndGamePlayerData(playerData.getGold(),
                imagePath,imagePath,playerData.getCreationDate(), LocalDate.now(),null);
        gameData.clearPlayerData();
        gameData.getPreviousPlayerData().add(deadPlayer);
        gameData.save();
        //TODO: add player to end player data

        ImageContainer background = new ImageContainer(0,0,
                "/assets/backgrounds/BG-GameOver.png",
                DrawLayer.Background);
        background.addToScreen(this, true);
    }
}
