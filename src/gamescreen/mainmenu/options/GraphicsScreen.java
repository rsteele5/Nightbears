package gamescreen.mainmenu.options;

import gameengine.gamedata.GraphicsSetting;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.popup.ConfirmationPopup;
import gamescreen.splashscreen.GraphicsChangeScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import static gameengine.gamedata.GraphicsSetting.Resolution;

import java.awt.*;


public class GraphicsScreen extends GameScreen {

    //region <Variables>
    private Resolution[] options = Resolution.values();
    private int optionCount = options.length;
    private TextBox graphicsText;
    int nextOptionOrdinal;
    int initialOrdinal;
    //endregion

    //region <Construction and Initialization>
    public GraphicsScreen(ScreenManager screenManager) {
        super(screenManager, "GraphicsScreen", true);
        initialOrdinal = (gameData.getGraphicsSettings().getCurrentOption().ordinal()) % optionCount ;
        nextOptionOrdinal = initialOrdinal;
    }

    @Override
    protected void initializeScreen() {

        //Initial position of the first button
        int X_INIT_BUTTON = 64;
        int Y_INIT_BUTTON = 920;
        int WIDTH_BUTTON = 256;
        int X_BUFFER = 48;

        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-GraphicsMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create Text Box
        graphicsText = new TextBox(X_INIT_BUTTON, 800,
                340,
                80,
                "Graphics Setting",
                new Font("NoScary", Font.PLAIN, 72),
                Color.WHITE, true);

        graphicsText.addToScreen(this, true);

        //Create Text Box
        graphicsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON,
                240,
                75,
                options[initialOrdinal].name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, true);

        graphicsText.addToScreen(this, true);

        //Create button
        Button butt;
        //Left Arrow
        butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON,
                "/assets/buttons/Button-LeftArrow.png",
                "/assets/buttons/Button-LeftArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    nextOptionOrdinal = (nextOptionOrdinal + 1) % optionCount ;
                    if(nextOptionOrdinal > 2) nextOptionOrdinal = 0;
                    graphicsText.setText(options[nextOptionOrdinal].name());
                });
        butt.addToScreen(this, true);

        //Right Arrow
        butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON,
                "/assets/buttons/Button-RightArrow.png",
                "/assets/buttons/Button-RightArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    nextOptionOrdinal = (nextOptionOrdinal - 1) % optionCount ;
                    if(nextOptionOrdinal < 0) nextOptionOrdinal = 2;
                    graphicsText.setText(options[nextOptionOrdinal].name());
                });
        butt.addToScreen(this, true);

        //Confirm
        butt = new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                "/assets/buttons/Button-ConfrimPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    if(initialOrdinal != nextOptionOrdinal){
                        gameData.changeResolution(options[nextOptionOrdinal]);
                        gameData.save();
                        screenManager.addScreen(new GraphicsChangeScreen(screenManager));
                        this.setScreenState(ScreenState.TransitionOff);
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                });
        butt.addToScreen(this, true);

        //Back
        butt = new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (initialOrdinal != nextOptionOrdinal) {
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
