package gamescreen.gameplay.overworld;

import gameobject.container.ButtonGridContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.DrawLayer;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gamescreen.gameplay.PauseMenu;
import gamescreen.gameplay.level.BedroomLevel;
import gamescreen.mainmenu.MainMenuScreen;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class OverworldUI extends Overlay {

    private Button pauseButton;
    private static String emptyBtnPath = "/assets/buttons/Button-Empty.png";


    public OverworldUI(ScreenManager screenManager, OverworldScreen parentScreen) {
        super(screenManager, parentScreen,"OverworldUI", 0,0, 1f);

    }

    /**
     * Initializes all of the stuff you want on your OverworldUI
     */
    @Override
    protected void initializeScreen() {
        ButtonGridContainer buttonLayout = new ButtonGridContainer(10,6, 256, 96,20,20, 20);

        pauseButton = new ButtonText(0,0, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Pause",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                    screenManager.addScreen(new PauseMenu(screenManager));
                });
        buttonLayout.addAt(pauseButton, 0, 0);

        Button actionButton = new ButtonText(350, 20,emptyBtnPath,emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Fight!",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - level");
                    screenManager.addScreen(new BedroomLevel(screenManager, (OverworldScreen)parentScreen));
                });
        buttonLayout.addAt(actionButton, 0, 1);

        Button leaveButton = new ButtonText(20, 120, emptyBtnPath, emptyBtnPath, DrawLayer.Entity,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, "Leave",
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Leave");
                    screenManager.addScreen(new MainMenuScreen(screenManager));
                });
        buttonLayout.addAt(leaveButton, 1, 0);

        buttonLayout.addToScreen(this, true);
    }

    public Clickable getPauseBtn() {
        return pauseButton;
    }
}
