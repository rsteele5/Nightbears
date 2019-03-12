package gamescreen.mainmenu.options;

import gameengine.gamedata.GameData;
import gameengine.gamedata.GraphicsSetting;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.popup.ConfirmationPopup;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import static gameengine.gamedata.GraphicsSetting.GraphicsOption;

import java.awt.*;



public class GraphicsScreen extends GameScreen {

    //region <Variables>
    GraphicsOption[] options = GraphicsOption.values();
    int optionCount = options.length;

    private GraphicsSetting localSetting;
    private TextBox graphicsText;

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public GraphicsScreen(ScreenManager screenManager) {
        super(screenManager, "GraphicsScreen", true);

    }

    @Override
    protected void initializeScreen() {

        localSetting = new GraphicsSetting(gameData.getGraphicsSettings().getCurrentOption());
        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-GraphicsMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create Text Box
        graphicsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON,
                300,
                150,
                localSetting.getCurrentOption().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);

        graphicsText.addToScreen(this, true);

        //Create button
        Button butt;
        //Left Arrow
        butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() + 1) % optionCount ;
                    if(nextOptionOrdinal > 2) nextOptionOrdinal = 0;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    graphicsText.setText(localSetting.getCurrentOption().name());
                });
        butt.addToScreen(this, true);

        //Right Arrow
        butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    int nextOptionOrdinal = (localSetting.getCurrentOption().ordinal() - 1) % optionCount;
                    if(nextOptionOrdinal < 0) nextOptionOrdinal = 2;
                    localSetting.setCurrentOption(options[nextOptionOrdinal]);
                    graphicsText.setText(localSetting.getCurrentOption().name());
                });
        butt.addToScreen(this, true);

        //Confirm
        butt = new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    gameData.setGraphicsSetting(localSetting);
                });
        butt.addToScreen(this, true);

        //Back
        butt = new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (!localSetting.getCurrentOption().equals(gameData.getGraphicsSettings().getCurrentOption())) {
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
