package input.listeners;

import gameengine.rendering.RenderEngine;
import gamescreen.ScreenManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {

    private ScreenManager screenManager;

    public MouseController(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        screenManager.clickEventAtLocation(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
