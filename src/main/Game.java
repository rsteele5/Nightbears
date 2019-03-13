package main;

import gameengine.GameEngine;
import gameengine.gamedata.GameData;
import main.utilities.Debug;

import javax.swing.JFrame;
import java.awt.*;

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
        gameWindow.setBackground(Color.BLACK);
        GameData gameData = new GameData();
        GameEngine gameEngine = new GameEngine(gameData);
        gameEngine.initializeWindow(gameWindow);
        gameWindow.setUndecorated(true);
        gameWindow.setVisible(true);
        new Thread(gameEngine).start();
    }
}
