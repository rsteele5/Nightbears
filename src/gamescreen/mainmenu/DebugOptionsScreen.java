package gamescreen.mainmenu;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.text.TextBox;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class DebugOptionsScreen extends GameScreen {

//    LOGGING_ACTIVE =        true;
//    DRAWING_ACTIVE =        true;
//    TEST_LOG =              true;
//    RENDER_ENGINE =         true;
//    GAME_SCREEN_LOG =       true;
//    RENDERABLE_LOG =        true;
//    BUTTON_LOG =            true;
//    GAME_ENGINE =           false;
//    FPS =                   false;
//    GARBAGE_COLLECTION =    true;
//    FPS_CURRENT =           false;
//    LOADING =               false;
//    PLAYER_STATUS =         true;
//    GAME_DATA =             true;

    private TextBox loggingActiveText;
    private TextBox drawingActiveText;
    private TextBox testLogText;
    private TextBox renderEngineLogText;
    private TextBox gameScreenLogText;
    private TextBox renderableLogText;
    private TextBox buttonLogText;
    private TextBox gameEngineText;
    private TextBox fpsText;
    private TextBox garbageCollectionText;
    private TextBox currentFPSLogText;
    private TextBox loadingText;
    private TextBox playerStatusText;
    private TextBox gameDataText;

    public DebugOptionsScreen(ScreenManager screenManager) {
        super(screenManager, "DebugOptions", true);
    }

    @Override
    protected void initializeScreen() {
        int X_INIT_BUTTON = 64;
        int Y_INIT_BUTTON = 100;
        int WIDTH_BUTTON = 100;
        int X_BUFFER = 48;
        int Y_INCREMENT = 72;
        int Y_BUFFER = 72;

        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-DebugScreen.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        Button leftArrow;
        Button rightArrow;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Create button
        leftArrow = new Button(X_INIT_BUTTON, Y_INIT_BUTTON,
                "/assets/buttons/Button-LeftArrow.png",
                "/assets/buttons/Button-LeftArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    boolean loggingActive = !Boolean.valueOf(loggingActiveText.getText());
                    loggingActiveText.setText(Boolean.toString(loggingActive));
                    DebugEnabler.LOGGING_ACTIVE = loggingActive;
                });
        leftArrow.addToScreen(this, true);

        //Create Text Box
        loggingActiveText = new TextBox(X_INIT_BUTTON + X_BUFFER, Y_INIT_BUTTON,
                100,
                60,
                Boolean.toString(DebugEnabler.LOGGING_ACTIVE),
        new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, true);
        loggingActiveText.addToScreen(this, true);

        rightArrow = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON,
                "/assets/buttons/Button-RightArrow.png",
                "/assets/buttons/Button-RightArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    boolean loggingActive = !Boolean.valueOf(loggingActiveText.getText());
                    loggingActiveText.setText(Boolean.toString(loggingActive));
                    DebugEnabler.LOGGING_ACTIVE = loggingActive;
                });
        rightArrow.addToScreen(this, true);

        TextBox label = new TextBox(X_INIT_BUTTON +  2 * X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON,
                500,
                60,
                "Logging active",
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, false);
        label.addToScreen(this, true);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //drawingActiveText
        //DebugEnabler.DRAWING_ACTIVE

        //Create button
        leftArrow = new Button(X_INIT_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                "/assets/buttons/Button-LeftArrow.png",
                "/assets/buttons/Button-LeftArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    boolean drawinginActive = !Boolean.valueOf(drawingActiveText.getText());
                    drawingActiveText.setText(Boolean.toString(drawinginActive));
                    DebugEnabler.DRAWING_ACTIVE = drawinginActive;
                });
        leftArrow.addToScreen(this, true);

        //Create Text Box
        drawingActiveText = new TextBox(X_INIT_BUTTON + X_BUFFER, Y_INIT_BUTTON + Y_BUFFER,
                100,
                60,
                Boolean.toString(DebugEnabler.DRAWING_ACTIVE),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, true);
        drawingActiveText.addToScreen(this, true);

        rightArrow = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                "/assets/buttons/Button-RightArrow.png",
                "/assets/buttons/Button-RightArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    boolean drawinginActive = !Boolean.valueOf(drawingActiveText.getText());
                    drawingActiveText.setText(Boolean.toString(drawinginActive));
                    DebugEnabler.DRAWING_ACTIVE = drawinginActive;
                });
        rightArrow.addToScreen(this, true);

        TextBox drawingActiveLabel = new TextBox(X_INIT_BUTTON +  2 * X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                500,
                60,
                "Drawing Active",
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, false);
        drawingActiveLabel.addToScreen(this, true);

        Y_BUFFER += Y_INCREMENT;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //testLogText
        //DebugEnabler.TEST_LOG

        //Create button
        leftArrow = new Button(X_INIT_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                "/assets/buttons/Button-LeftArrow.png",
                "/assets/buttons/Button-LeftArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    boolean testLogValue = !Boolean.valueOf(testLogText.getText());
                    testLogText.setText(Boolean.toString(testLogValue));
                    DebugEnabler.TEST_LOG = testLogValue;
                });
        leftArrow.addToScreen(this, true);

        //Create Text Box
        testLogText = new TextBox(X_INIT_BUTTON + X_BUFFER, Y_INIT_BUTTON + Y_BUFFER,
                100,
                60,
                Boolean.toString(DebugEnabler.TEST_LOG),
        new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, true);
        testLogText.addToScreen(this, true);

        rightArrow = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                "/assets/buttons/Button-RightArrow.png",
                "/assets/buttons/Button-RightArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    boolean testLogValue = !Boolean.valueOf(testLogText.getText());
                    testLogText.setText(Boolean.toString(testLogValue));
                    DebugEnabler.TEST_LOG = testLogValue;
                });
        rightArrow.addToScreen(this, true);

        TextBox testLogTextLabel = new TextBox(X_INIT_BUTTON +  2 * X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + Y_BUFFER,
                500,
                60,
                "Test Log",
        new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, false);
        testLogTextLabel.addToScreen(this, true);

        Y_BUFFER += Y_BUFFER;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        //Create button
//        leftArrow = new Button(X_INIT_BUTTON, Y_INIT_BUTTON,
//                "/assets/buttons/Button-LeftArrow.png",
//                DrawLayer.Entity,
//                () -> {
//                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
//                    boolean ||||| = !Boolean.valueOf(|||||.getText());
//                    |||||.setText(Boolean.toString(|||||));
//                    DebugEnabler.||||| = |||||;
//                });
//        leftArrow.addToScreen(this, true);
//
//        //Create Text Box
//        ||||| = new TextBox(X_INIT_BUTTON + X_BUFFER, Y_INIT_BUTTON,
//                100,
//                60,
//                Boolean.toString(DebugEnabler.|||||),
//        new Font("NoScary", Font.PLAIN, 60),
//                Color.WHITE, true);
//        |||||.addToScreen(this, true);
//
//        rightArrow = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON,
//                "/assets/buttons/Button-RightArrow.png",
//                DrawLayer.Entity,
//                () -> {
//                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
//                    ||||||||||||||||
//                });
//        rightArrow.addToScreen(this, true);
//
//        TextBox ||||| = new TextBox(X_INIT_BUTTON +  2 * X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON,
//                500,
//                60,
//                |||||||,
//                new Font("NoScary", Font.PLAIN, 60),
//                Color.WHITE, false);
//        |||||.addToScreen(this, true);
//
//        Y_BUFFER += Y_BUFFER;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button confirm = new gameobject.renderable.button.Button(1296, 920,
                "/assets/buttons/Button-Confirm.png",
                "/assets/buttons/Button-ConfrimPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
//                    gameData.setInputSetting(localSetting);
                });
        confirm.addToScreen(this, true);

        Button back = new Button(1600,920,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                        setScreenState(ScreenState.TransitionOff);
                });
        back.addToScreen(this, true);
    }
}
