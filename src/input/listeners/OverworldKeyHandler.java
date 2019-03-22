package input.listeners;

import main.utilities.Debug;

import java.awt.event.KeyEvent;

public class OverworldKeyHandler implements KeyHandler{

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Debug.success(true, "Key is pressed! " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Debug.success(true, "Key is released! " + e.getKeyCode());
    }
}
