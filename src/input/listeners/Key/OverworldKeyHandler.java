package input.listeners.Key;

import gameobject.renderable.player.Player;
import gamescreen.Overlay;
import main.utilities.Clickable;
import main.utilities.Debug;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class OverworldKeyHandler implements KeyHandler {

    private Player player;
    private final int CYCLE = 9;
    private final int SELECT = 10;
    private ArrayList<Clickable> clickables;
    private Clickable target;

    public OverworldKeyHandler(Player p1, ArrayList<Clickable> clickables){
        player = p1;
        this.clickables = clickables;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.move(e);
        if(e.getKeyCode() == CYCLE){
            nextClickable();
        }else if(e.getKeyCode() == SELECT){
            target.onClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.moveRelease(e);
    }

    private void nextClickable(){
        if(target == null){
            target = clickables.get(0);
            target.setPressed(true);
        } else {
            int next = clickables.size() <= clickables.indexOf(target)+1 ? 0 : clickables.indexOf(target)+1;
            target.setPressed(false);
            target = clickables.get(next);
            target.setPressed(true);
        }
    }
}
