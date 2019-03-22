package gamescreen.gameplay;

import gameobject.renderable.player.Player;
import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class VendorDialogBox extends Overlay {

    private final String welcome = "Hey Teddy! Would you like to come in and check out my new wares?";
    private Player player;

    public VendorDialogBox(ScreenManager screenManager, GameScreen parentScreen, int xPos, int yPos, Player p1) {
        super(screenManager, parentScreen, "VendorDialogBox", xPos, yPos, 1f);
        player = p1;
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

        DialogBox diagBox = new DialogBox(10, 10, 355, 160, welcome,
                new Font("NoScary", Font.PLAIN, 40), Color.WHITE, false);
        diagBox.addToScreen(this, true);

        Button button = new gameobject.renderable.button.Button(100,140,
                "/assets/buttons/Button-Yes.png",
                "/assets/buttons/Button-YesPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog Yes");
                    this.exiting = true;
                    screenManager.addScreen(new VendorScreen(screenManager, player));
                });
        button.setSize(75,30);
        button.addToScreen(this,true);

        button = new Button(225,140,
                "/assets/buttons/Button-No.png",
                "/assets/buttons/Button-NoPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog No");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.setSize(75,30);
        button.addToScreen(this,true);
    }
}
