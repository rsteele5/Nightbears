package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gamescreen.mainmenu.MainMenuScreen;

import java.awt.Font;
import java.awt.Color;

/**
 * This is a temporary screen that hides the uglyness of
 * changing the graphics of our game. It appears for exactly
 * two seconds then disappears.
 */
public class GraphicsChangeScreen extends GameScreen {

    private int framesToDisplay = 120;
    private int frameCounter = 0;
    private String message = "Please wait";
    private String dots = "";
    private TextBox waitText;
    private int randomMessage = 0;
    private String[] waitMessages;

    /**
     * Constructs a GraphicsChangeScreen.
     * @param screenManager The ScreenManager that controls this screen.
     */
    public GraphicsChangeScreen(ScreenManager screenManager) {
        super(screenManager, "GraphicsChangeScreen", 1f);
        screenManager.changeGraphics();
    }

    @Override
    protected void initializeScreen() {
        waitMessages = new String[] {
                "Working on things",
                "Trying to back up the AGP bandwidth",
                "Attaching the visual protocol",
                "Inputting the back-end transmitter",
                "Initializing the wireless MAC array",
                "Synthesizing the solid state alarm",
                "Disconnecting the haptics",
                "Transmitting the multi-byte CPU protocol",
                "Networking the fiber optic mainframe",
                "Overriding the ethernet HTTP microchip",
                "Backing up the capacitors",
                "Inputting the cross-platform internet",
                "Rebooting the ASCII Bus circuit",
                "Bypassing the input back-end",
                "Parsing the USB matrices",
                "Iterating the bluetooth mainframe",
                "Quantifying protocols",
                "Bypassing the BIOS sensor",
                "Greg is super awesome",
                "Iterating auxiliary HHD circuits",
                "Compressing neural networks"
        };

        ImageContainer blackCover = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        blackCover.addToScreen(this, true);

        randomMessage = (int)(Math.random() * (waitMessages.length));

        waitText = new TextBox(0,480,
                1980, 900,
                "Please wait",
                new Font("NoScary", Font.PLAIN, 120),
                Color.WHITE,
                true);

        TextBox waitMessage = new TextBox(
                0, 320,
                1980, 900,
                waitMessages[randomMessage],
                new Font("NoScary", Font.PLAIN, 120),
                Color.WHITE,
                true);
        waitMessage.addToScreen(this,true);

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