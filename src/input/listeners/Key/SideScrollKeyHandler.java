package input.listeners.Key;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.player.Player;

import java.awt.event.KeyEvent;

import static input.listeners.Key.KeyCodeMeta.INTERACT;

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
        player.attack(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.moveRelease(e);
        if(e.getKeyCode() == INTERACT){
            player.setRequesting(true);
        }
    }
}
