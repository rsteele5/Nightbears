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

    InputMethod[] options = InputMethod.values();
    int optionCount = options.length;

    private InputSetting localSetting;

    private TextBox controlsText;

    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public ControlsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);

    }

    @Override
    protected void initializeScreen() {

        localSetting = new InputSetting(gameData.getInputSetting().getCurrentOption());

        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/backgrounds/BG-ControlsMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create Text Box
        controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON,
                300,
                150,
                localSetting.getCurrentOption().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);

        controlsText.addToScreen(this, true);

        //Create button
        Button butt;

        butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() - 1) % optionCount;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    controlsText.setText(localSetting.getCurrentOption().name());
                });
        butt.addToScreen(this, true);

        butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() + 1) % optionCount;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    controlsText.setText(localSetting.getCurrentOption().name());
                });
        butt.addToScreen(this, true);

        butt = new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    gameData.setInputSetting(localSetting);
                });
        butt.addToScreen(this, true);

        butt = new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
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
        butt.addToScreen(this, true);
    }
    //endregion
}
