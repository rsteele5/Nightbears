package gamescreen.gameplay.level;

import gameobject.container.ButtonGridContainer;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.HUD;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gamescreen.gameplay.PauseMenu;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class SideScrollUI extends Overlay {


    private Player player;
    private Button pauseButton;
    private static String emptyBtnPath = "/assets/buttons/Button-Empty.png";


    public SideScrollUI(ScreenManager screenManager, GameScreen parentScreen, Player player) {
        super(screenManager, parentScreen,"SideScrollUI", 0,0, 1f);
        this.player = player;
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        HUD hud = new HUD(gameData.getPlayerData());
        hud.addToScreen(this, true);

        ButtonGridContainer buttonLayout = new ButtonGridContainer(10,6, 256, 96,20,300, 20);

        pauseButton = new ButtonText(0,0, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Pause",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Pause");
                    parentScreen.addOverlay(new PauseMenu(screenManager, parentScreen));
                });
        buttonLayout.addAt(pauseButton, 0, 0);

        Button leaveButton = new ButtonText(20, 120, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Leave",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Leave");
                    //TODO save players location
                    parentScreen.setCamera(null);
                    screenManager.addScreen(new MainMenuScreen(screenManager));

                });
        buttonLayout.addAt(leaveButton, 1, 0);

        buttonLayout.addToScreen(this, true);
    }

    public Clickable getPauseBtn() {
        return pauseButton;
    }
}