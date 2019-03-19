package main;

import gameengine.GameEngine;
import gameengine.gamedata.GameData;
import main.utilities.Debug;

import javax.swing.JFrame;
import java.awt.*;

/**
 * This is the entry point of our Game. The Game class instantiates
 * everything it needs to create the game. It creates a window, sets
 * the window properties. Then instantiates an instance of GameData
 * (the data is read from disk in the GameData constructor). Finally
 * it creates an instance of the GameEngine, creates a thread and
 * runs the GameEngine in that thread.
 */
public class Game {

    public static void main(String[] args) {
        Debug.startLog();

        //Initialize and display the renderable portion
        JFrame gameWindow = new GameWindow();
        gameWindow.setTitle("Nightbears");
        gameWindow.setSize(1920, 1080);
        gameWindow.setLocation(0,0);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.getContentPane().setBackground(Color.DARK_GRAY);
        GameData gameData = new GameData();
        GameEngine gameEngine = new GameEngine(gameData);
        gameEngine.initializeWindow(gameWindow);
        gameWindow.setUndecorated(true);
        gameWindow.setVisible(true);
        new Thread(gameEngine).start();
    }
}
