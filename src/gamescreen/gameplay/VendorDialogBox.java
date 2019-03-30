package gamescreen.gameplay;

import gameobject.renderable.player.Player;
import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class VendorDialogBox extends Overlay {

    //region <Variables>
    private static final String welcome = "Hey Teddy! Would you like to come in and check out my new wares?";
    private Player player;
    private String message;
    //endregion

    /**
     * Creates the vendor dialog box
     * @param screenManager screen manager
     * @param parentScreen parent screen
     * @param xPos x-location for screen
     * @param yPos y-location for screen
     */
    public VendorDialogBox(ScreenManager screenManager, GameScreen parentScreen, int xPos, int yPos, Player p1, String message) {
        super(screenManager, parentScreen, "VendorDialogBox", xPos, yPos, 1f);
        player = p1;
        this.message = message;
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {
        ImageContainer cover = new ImageContainer(0,0, "/assets/backgrounds/BG-VendorDialog.png", DrawLayer.Background);
        cover.setSize(375, 180);
        cover.setAlpha(1f);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(10, 10, 355, 160, message,
                new Font("NoScary", Font.PLAIN, 40), Color.BLACK, false);
        diagBox.addToScreen(this, true);
        Debug.log(DebugEnabler.TEST_LOG, "Vendor dialog box added");
    }

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the vendor dialog box screen");
        setScreenState(ScreenState.TransitionOff);
        return true;
    }


    @Override
    protected void transitionOn() {
        this.setScreenState(ScreenState.Active);
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }
}
