package main;

import gameengine.gamedata.GameData;
import gamescreen.ScreenManager;
import input.listeners.Key.KeyController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    public GameWindow() {
        addKeyListener(new KeyController(new ScreenManager(new GameData())));
//        setBackground(Color.DARK_GRAY);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        //TODO make this shutdown gracefully
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
//        //TODO: Implement multiple players.
//         GameEngine.players.get(0).handleKeyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
//        //TODO: Implement multiple players.
//        GameEngine.players.get(0).handleKeyReleased(e);
    }
}


