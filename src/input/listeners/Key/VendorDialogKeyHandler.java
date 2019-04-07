package input.listeners.Key;

import gamescreen.GameScreen;
import gamescreen.gameplay.VendorDialogBox;

import java.awt.event.KeyEvent;

import static input.listeners.Key.KeyCodeMeta.DIALOG_SKIP;

public class VendorDialogKeyHandler implements KeyHandler {

    private VendorDialogBox dialogBox;

    public VendorDialogKeyHandler(VendorDialogBox dialogBox){
        this.dialogBox = dialogBox;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == DIALOG_SKIP){
            dialogBox.setScreenState(GameScreen.ScreenState.TransitionOff);
        }
    }
}
