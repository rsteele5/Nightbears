package gamescreen.mainmenu.options;


import gameengine.gamedata.SoundSetting;
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

    private SoundSetting[] localSettings;
    private SoundSetting.SoundOption[] options = SoundSetting.SoundOption.values();
    private boolean didOptionsChange;

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
    }

    @Override
    protected void initializeScreen() {

        //Create Background
        //localSettings = new SoundSetting(gameData.getSoundSetting().getCurrentOption());
        didOptionsChange = false;

        localSettings = new SoundSetting[3];
        for (int i = 0; i < localSettings.length; i++) {
            localSettings[i] = new SoundSetting(gameData.getSoundSetting(i).getCurrentOption());
        }
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/backgrounds/BG-SoundMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);


        //Create button
        Button butt;

        for (int i = 0; i < 3; i++) {
            TextBox controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2,
                    300,
                    150,
                    localSettings[i].getCurrentOption().name(),
                    new Font("NoScary", Font.PLAIN, 60),
                    Color.WHITE);

            controlsText.addToScreen(this, true);
            final int index = i;

            butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                        int nextOptionOrdinal = (localSettings[index].getCurrentOption().ordinal() + 1) % options.length;
                        if(nextOptionOrdinal > 1) nextOptionOrdinal = 0;
                        localSettings[index].setCurrentOption(options[nextOptionOrdinal]);
                        controlsText.setText(localSettings[index].getCurrentOption().name());
                    });
            butt.addToScreen(this, true);

            butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + (i * Y_INIT_BUTTON) / 2, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                        int nextOptionOrdinal = (localSettings[index].getCurrentOption().ordinal() - 1) % options.length;
                        if(nextOptionOrdinal < 0) nextOptionOrdinal = 1;
                        localSettings[index].setCurrentOption(options[nextOptionOrdinal]);
                        controlsText.setText(localSettings[index].getCurrentOption().name());
                    });
            butt.addToScreen(this, true);
        }

        butt = new Button((X_INIT_BUTTON - 600)+ 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    for (int i = 0; i < localSettings.length; i++) {
                        gameData.setSoundSetting(localSettings[i], i);
                    }
                });
        butt.addToScreen(this, true);

        butt = new Button((X_INIT_BUTTON - 600) + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    for (int i = 0; i < localSettings.length; i++) {
                        if (!localSettings[i].getCurrentOption().equals(gameData.getSoundSetting(i).getCurrentOption())) {
                            didOptionsChange = true;
                        }
                    }
                    if (didOptionsChange) {
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
