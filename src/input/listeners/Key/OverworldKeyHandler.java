package input.listeners.Key;

import gameobject.renderable.player.Player;
import main.utilities.Clickable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static input.listeners.Key.KeyCodeMeta.*;

public class OverworldKeyHandler implements KeyHandler {

    private Player player;
    private ArrayList<Clickable> clickables;
    private Clickable target;
    private Clickable pause;

    public OverworldKeyHandler(Player p1, ArrayList<Clickable> clickables, Clickable pauseBtn){
        player = p1;
        this.clickables = clickables;
        pause = pauseBtn;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.move(e);
        if(e.getKeyCode() == NEXT)
            nextClickable();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.moveRelease(e);
        if(e.getKeyCode() == SELECT){
            target.onClick();
        }else if(e.getKeyCode() == PAUSE){
            pause.onClick();
        }else if(e.getKeyCode() == INTERACT){
            player.setRequesting(true);
        }
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
