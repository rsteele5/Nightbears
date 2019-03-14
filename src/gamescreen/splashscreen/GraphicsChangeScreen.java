package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.mainmenu.MainMenuScreen;

import java.awt.*;

public class GraphicsChangeScreen extends GameScreen {

    private int framesToDisplay = 120;
    private int frameCounter = 0;
    private String message = "Working on things... Please wait";
    private String dots = "";
    private TextBox waitText;

    public GraphicsChangeScreen(ScreenManager screenManager) {
        super(screenManager, "GraphicsChangeScreen", 1f);
        screenManager.changeGraphics();
    }

    @Override
    protected void initializeScreen() {
        ImageContainer blackCover = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        blackCover.addToScreen(this, true);
        blackCover.setWidth(3840);
        blackCover.setHeight(2160);
        waitText = new TextBox(350,320, 1980, 900, "Working on things... Please wait", new Font("NoScary", Font.PLAIN, 120), Color.WHITE);
        waitText.addToScreen(this,true);
    }

    @Override
    protected void transitionOn() {
        frameCounter++;
        if(frameCounter == framesToDisplay){
            setScreenState(ScreenState.TransitionOff);
        } else {
            if(frameCounter  == 30)  dots = ".";
            else if(frameCounter == 60)  dots = ". .";
            else if(frameCounter == 90)  dots = ". . .";
        }
        waitText.setText(message + dots);
    }

    @Override
    protected void transitionOff() {
        exiting = true;
        screenManager.addScreen(new MainMenuScreen(screenManager));
    }
}
