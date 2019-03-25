package input.listeners.Key;

import java.awt.event.KeyEvent;

public interface KeyHandler {
    void keyTyped(KeyEvent e);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}
