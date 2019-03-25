package input.listeners;

import gamescreen.ScreenManager;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The mouse controller listens for mouse events on the
 * RenderEngine and sends them to the ScreenManager to
 * be handled.
 */
public class MouseController implements MouseListener {

    private ScreenManager screenManager;
    private Clickable pressTarget;

    /**
     * Constructs a MouseController.
     * @param screenManager The ScreenManager associated with the controller
     */
    public MouseController(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    /**
     * Intercepts a MouseClicked event from the RenderEngine and sends it
     * to the ScreenManager
     * @param event The mouse event associated with the action
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        //screenManager.clickEventAtLocation(event.getX(), event.getY());
    }

    /**
     * Intercepts a MousePressed event from the RenderEngine and sends it
     * to the ScreenManager
     * @param event The mouse event associated with the action
     */
    @Override
    public void mousePressed(MouseEvent event) {
        screenManager.mousePressedAtLocation(this, event.getX(), event.getY());
    }

    /**
     * Intercepts a MouseReleased event from the RenderEngine and sends it
     * to the ScreenManager
     * @param event The mouse event associated with the action
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        screenManager.mouseReleasedAtLocation(this, event.getX(), event.getY());
    }

    /**
     * Intercepts a MouseEntered event from the RenderEngine and sends it
     * to the ScreenManager
     * @param event The mouse event associated with the action
     */
    @Override
    public void mouseEntered(MouseEvent event) {

    }

    /**
     * Intercepts a MouseExited event from the RenderEngine and sends it
     * to the ScreenManager
     * @param event The mouse event associated with the action
     */
    @Override
    public void mouseExited(MouseEvent event) {

    }

    public Clickable getPressTarget() {
        return pressTarget;
    }

    public void setPressTarget(Clickable target){
        this.pressTarget = target;
        Debug.success(DebugEnabler.GAME_SCREEN_LOG, "MouseController -> Setting press target");
    }

    public void clearPressTarget() {
        this.pressTarget = null;
    }
}
