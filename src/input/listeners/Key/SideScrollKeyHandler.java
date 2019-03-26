package input.listeners.Key;

import gameobject.renderable.player.Player;

import java.awt.event.KeyEvent;

public class SideScrollKeyHandler implements KeyHandler {
    Player p;
    public SideScrollKeyHandler(Player p){this.p = p;}

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        p.move(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        p.moveRelease(e);
    }
}
