package gamescreen.mainmenu.options;

import static gameengine.GameSettings.*;
import static gameengine.GameSettings.SoundOption.*;

import gameobject.renderable.text.TextBox;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.popup.ConfirmationPopup;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;


public class SoundScreen extends GameScreen {

    private final static int NUMBER_OF_OPTIONS = 3;
    private static SoundOption[] soundOptions = new SoundOption[NUMBER_OF_OPTIONS];
    private SoundOption exitSetting;

    //region <Variables>
    private final int X_INIT_BUTTON = 664;
    private final int Y_INIT_BUTTON = 226;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    private final int Y_BUFFER = 110;
    //endregion

    //region <Construction and Initialization>
    public SoundScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);
        for (int i = 0; i < soundOptions.length; i++) {
            soundOptions[i] = screenManager.getGameSettings().getSoundOption(i);
        }
        exitSetting = On;
    }

    @Override
    protected void initializeScreen() {

        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/backgrounds/BG-SoundMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);


        //Create button
        Button butt;

        for (int i = 0; i < 3; i++) {
            //controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2,
            TextBox controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2,
                    300,
                    150,
                    screenManager.getGameSettings().getSoundOption(0).name(),
                    new Font("NoScary", Font.PLAIN, 60),
                    Color.WHITE);

            controlsText.addToScreen(this, true);

            butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                        if (exitSetting == On) {
                            exitSetting = Off;
                            controlsText.setText(exitSetting.name());
                        } else {
                            exitSetting = On;
                            controlsText.setText(exitSetting.name());
                        }
                    });
            butt.addToScreen(this, true);

            butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                        if (exitSetting == On) {
                            exitSetting = Off;
                            controlsText.setText(exitSetting.name());
                        } else {
                            exitSetting = On;
                            controlsText.setText(exitSetting.name());
                        }
                    });
            butt.addToScreen(this, true);
        }

        butt = new Button((X_INIT_BUTTON - 600)+ 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    for(int i = 0; i < soundOptions.length; i++) {
                        soundOptions[i] = exitSetting; //This line is causing the bug
                        screenManager.getGameSettings().setSoundOption(soundOptions);
                    }
                    soundOptions[0] = exitSetting;
                });
        butt.addToScreen(this, true);

        butt = new Button((X_INIT_BUTTON - 600) + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (!exitSetting.equals(soundOptions[0])) {
                        screenManager.addScreen(new ConfirmationPopup(screenManager,
                                "Return Without Saving?",
                                ()-> this.setScreenState(ScreenState.TransitionOff)));
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                });
        butt.addToScreen(this, true);
    }
    //endregion
}
