package input.listeners.Key;

import gameobject.renderable.player.Player;

import java.awt.event.KeyEvent;

public class SideScrollKeyHandler implements KeyHandler {

    private Player player;

    public SideScrollKeyHandler(Player player){
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.move(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.moveRelease(e);
    }
}
