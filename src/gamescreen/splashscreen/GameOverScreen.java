package gamescreen.splashscreen;

import gameengine.gamedata.EndGamePlayerData;
import gameengine.gamedata.PlayerData;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.time.LocalDate;

public class GameOverScreen extends GameScreen {

    public GameOverScreen(ScreenManager screenManager, float screenAlpha) {
        super(screenManager, "GameOverScreen", screenAlpha);
    }

    @Override
    protected void initializeScreen() {
        PlayerData playerData = gameData.getPlayerData();
        String imagePath = "/assets/player/color/" + playerData.getImageDirectory() + "/hud/Portrait.png";
        EndGamePlayerData deadPlayer = new EndGamePlayerData(playerData.getGold(),
                imagePath,playerData.getName(),playerData.getCreationDate(), LocalDate.now(),null);
        gameData.clearPlayerData();
        gameData.getPreviousPlayerData().add(deadPlayer);
        gameData.save();
        //TODO: add player to end player data

        ImageContainer background = new ImageContainer(0,0,
                "/assets/backgrounds/BG-GameOver.png",
                DrawLayer.Background);
        background.addToScreen(this, true);

        Button button = new Button(100, 100,
                "/assets/buttons/Button-MainMenu.png",
                "/assets/buttons/Button-MainMenuPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    screenManager.addScreen(new MainMenuScreen(screenManager));
                });
        button.addToScreen(this,true);
    }
}
