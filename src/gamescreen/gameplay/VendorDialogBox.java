package gamescreen.gameplay;

import gameobject.renderable.text.DialogBox;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import input.listeners.Key.VendorDialogKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class VendorDialogBox extends Overlay {

    //region <Variables>
    private String message;
    private final String firstNotice = "Warebear: I created lots of goodies that might help you defeat those monsters. Come see what I have!";
    private final String subsequentNotices = "Warebear: I have all NEW items that are even more powerful than before! Come see what I have!";
    private final String firstLevel = "Warebear: Whew! That was a super scary monster!";
    private int visitedCount;
    //endregion

    /**
     * Creates the vendor dialog box
     * @param screenManager screen manager
     * @param xPos x-location for screen
     * @param yPos y-location for screen
     */
    public VendorDialogBox(ScreenManager screenManager, GameScreen parentScreen, int xPos, int yPos, int visitedCount) {
        super(screenManager, parentScreen, "VendorDialogBox", xPos, yPos, 0f);
        isExclusive = true;
        this.visitedCount = visitedCount;
        if(visitedCount > 0){
            if(visitedCount > 1) {
                message = subsequentNotices;
                gameData.getVendorData().restockItems();
            } else message = firstNotice;
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

        setKeyHandler(new VendorDialogKeyHandler(this));
    }

    public int getVisitCount(){
        return visitedCount;
    }

    @Override
    protected void transitionOff() {
        exiting = true;
        if(visitedCount == 0){
            parentScreen.addOverlay(new VendorDialogBox(screenManager, parentScreen, 0, 0, 1));
        }
    }
}
