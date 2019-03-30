package input.listeners.Key;

import main.utilities.Clickable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static input.listeners.Key.KeyCodeMeta.*;

public class ClickableKeyHandler implements KeyHandler {

    private ArrayList<Clickable> clickables;
    private Clickable target;

    public ClickableKeyHandler(ArrayList<Clickable> clickables){
        this.clickables = clickables;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == NEXT) {
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

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == SELECT){
            target.onClick();
        }
    }
}
