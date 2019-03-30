package gamescreen.gameplay;

import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import main.utilities.Action;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class VendorDialogBox extends Overlay {

    //region <Variables>
    private String message;
    public String firstNotice = "Warebear: I created lots of goodies that might help you defeat those monsters. Come see what I have!";
    public String subsequentNotices = "Warebear: I have all NEW items that are even more powerful than before! Come see what I have!";
    public String firstLevel = "Warebear: Whew! That was a super scary monster!";
    private Action yes;
    private boolean yesActive;
    //endregion

    /**
     * Creates the vendor dialog box
     * @param screenManager screen manager
     * @param xPos x-location for screen
     * @param yPos y-location for screen
     */
    public VendorDialogBox(ScreenManager screenManager, GameScreen parentScreen, int xPos, int yPos, int visitedCount) {
        super(screenManager, parentScreen, "VendorDialogBox", xPos, yPos, 0f);
        if(visitedCount > 0){
            if(visitedCount > 1) {
                message = subsequentNotices;
                gameData.getVendorData().restockItems();
            } else message = firstNotice;
            yesActive = true;
            yes = () ->{
                Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog Yes");
                this.exiting = true;
                parentScreen.addOverlay(new VendorScreen(screenManager, parentScreen));
            };
        } else message = firstLevel;
    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        ImageContainer cover = new ImageContainer(300,730, "/assets/backgrounds/BG-DialogBox.png", DrawLayer.Background);
        cover.setSize(1400, 300);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(320, 750, 1360, 260, message,
                new Font("NoScary", Font.PLAIN, 72), Color.WHITE, false);
        diagBox.addToScreen(this, true);
        Debug.log(DebugEnabler.TEST_LOG, "Vendor dialog box added");

        Button button = new gameobject.renderable.button.Button(760,880,
                "/assets/buttons/Button-Yes.png",
                "/assets/buttons/Button-YesPressed.png",
                DrawLayer.Entity,
                yes
            );
        button.addToScreen(this,yesActive);

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
