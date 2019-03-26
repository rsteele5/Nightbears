package input.listeners.Key;

import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    private ScreenManager screenManager;

    public KeyController(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Debug.log(DebugEnabler.KEY_EVENTS,"KeyController - ScreenManager handle key pressed: "+e.getKeyCode());
        screenManager.handleKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Debug.log(DebugEnabler.KEY_EVENTS,"KeyController - ScreenManager handle key release: "+e.getKeyCode());
        screenManager.handleKeyReleased(e);
    }
}
