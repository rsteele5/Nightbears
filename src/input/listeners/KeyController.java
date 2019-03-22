package input.listeners;

import gamescreen.ScreenManager;

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
        screenManager.handleKeyPressed(e);

//        //TODO make this shutdown gracefully
//        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
//            System.exit(0);
//        //TODO: Implement multiple players.
//        GameEngine.players.get(0).move(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        screenManager.handleKeyReleased(e);

//        //TODO: Implement multiple players.
//        GameEngine.players.get(0).moveRelease(e);
    }
}
