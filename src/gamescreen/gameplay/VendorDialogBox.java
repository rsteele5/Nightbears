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

        ImageContainer cover = new ImageContainer(300,730, "/assets/backgrounds/BG-DialogBox.png", DrawLayer.Background);
        cover.setSize(1400, 300);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(320, 750, 1360, 260, welcome,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, false);
        diagBox.addToScreen(this, true);
        Debug.log(DebugEnabler.TEST_LOG, "Vendor dialog box added");

        Button button = new gameobject.renderable.button.Button(760,880,
                "/assets/buttons/Button-Yes.png",
                "/assets/buttons/Button-YesPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog Yes");
                    this.exiting = true;
                    screenManager.addScreen(new VendorScreen(screenManager, player));
                });

        button.addToScreen(this,true);

        button = new Button(1160,880,
                "/assets/buttons/Button-No.png",
                "/assets/buttons/Button-NoPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog No");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this,true);
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }
}
