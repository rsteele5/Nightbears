package gamescreen.mainmenu.options;

import gameengine.GameSettings;
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

import static gameengine.GameSettings.GraphicsOption.*;


public class GraphicsScreen extends GameScreen {

    //region <Variables>
    private GameSettings.GraphicsOption localGraphicsSetting;
    private TextBox graphicsText;




    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public GraphicsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);
        localGraphicsSetting = screenManager.getGameSettings().getGraphicsOption();
    }

    @Override
    protected void initializeScreen() {

        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-GraphicsMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create Text Box
        graphicsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON,
                300,
                150,
                screenManager.getGameSettings().getGraphicsOption().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);

        graphicsText.addToScreen(this, true);

        //Create button
        Button butt;
        //Left Arrow
        butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    switch (exitSetting){
                        case High: exitSetting = Medium; break;
                        case Medium: exitSetting = Low; break;
                        case Low: exitSetting = High; break;
                    }
                    graphicsText.setText(exitSetting.name());
                });
        butt.addToScreen(this, true);

        //Right Arrow
        butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    switch (exitSetting){
                        case High: exitSetting = Low; break;
                        case Medium: exitSetting = High; break;
                        case Low: exitSetting = Medium; break;
                    }
                    graphicsText.setText(exitSetting.name());
                });
        butt.addToScreen(this, true);

        //Confirm
        butt = new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    graphicsSetting = exitSetting;
                    screenManager.getGameSettings().setGraphicsOption(graphicsSetting);
                });
        butt.addToScreen(this, true);

        //Back
        butt = new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (!exitSetting.equals(graphicsSetting)) {
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
