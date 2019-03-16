package gamescreen.mainmenu.options;

import static gameengine.gamedata.InputSetting.InputMethod;

import gameengine.gamedata.InputSetting;
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


public class ControlsScreen extends GameScreen {

    //region <Variables>
    private InputMethod[] options = InputMethod.values();
    private int optionCount = options.length;
    private InputSetting localSetting;
    private TextBox controlsText;
    //endregion

    //region <Construction and Initialization>
    public ControlsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);

    }

    @Override
    protected void initializeScreen() {
        //Grab the graphics settings so we can keep our changes local until we confirm them
        localSetting = new InputSetting(gameData.getInputSetting().getCurrentOption());

        //Initial position of the first button
        int X_INIT_BUTTON = 64;
        int Y_INIT_BUTTON = 576;
        int X_BUFFER = 48;

        //Create Background
        ImageContainer background = new ImageContainer(0, 0, "/assets/backgrounds/BG-ControlsMenu.png", DrawLayer.Background);
        background.addToScreen(this, true);

        //Create Text Box
        controlsText = new TextBox(X_INIT_BUTTON + X_BUFFER, Y_INIT_BUTTON,
                300,
                150,
                localSetting.getCurrentOption().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);
        controlsText.addToScreen(this, true);

        //Create button
        Button leftArrow = new Button(X_INIT_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() - 1) % optionCount;
                    if(nextOptionOrdinal < 0) nextOptionOrdinal = 1;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    controlsText.setText(localSetting.getCurrentOption().name());
                });
        leftArrow.addToScreen(this, true);

        int WIDTH_BUTTON = 256;
        Button rightArrow = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() + 1) % optionCount;
                    if(nextOptionOrdinal > 1) nextOptionOrdinal = 0;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    controlsText.setText(localSetting.getCurrentOption().name());
                });
        rightArrow.addToScreen(this, true);

        Button confirm = new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    gameData.setInputSetting(localSetting);
                });
        confirm.addToScreen(this, true);

        Button back = new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (!localSetting.getCurrentOption().equals(gameData.getInputSetting().getCurrentOption())) {
                        screenManager.addScreen(new ConfirmationPopup(screenManager,
                                "Return Without Saving?",
                                ()-> this.setScreenState(ScreenState.TransitionOff)));
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                });
        back.addToScreen(this, true);
    }
    //endregion
}
