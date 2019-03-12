package gameengine;

import static gameengine.GameSettings.*;

import gameengine.physics.OverworldEngine;
import gameobject.renderable.player.Player;
import gameobject.renderable.vendor.Vendor;
import gameobject.renderable.DrawLayer;
import input.listeners.MouseController;
import gameengine.physics.PhysicsEngine;
import gameengine.rendering.RenderEngine;
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Container;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

    private final int FRAMES_PER_SECOND = 60;
    private int frameCounter = 0;



    private GameSettings gameSettings;

    private ScreenManager screenManager;
    private PhysicsEngine physicsEngine;
    private RenderEngine renderEngine;
    private OverworldEngine overworldEngine;
    public static ArrayList<Player> players;
    private static Player p1,p2;
    public static Vendor vendor;

    public GameEngine(){
        p1 = new Player(0,0, "/assets/player/overworld/teddyidleanimation/Overworld-Teddy-Center.png", DrawLayer.Entity);
        p2 = new Player(0,0,"/assets/testAssets/square2.png", DrawLayer.Entity);
        vendor = new Vendor(0,0);
        gameSettings = new GameSettings(this);
        screenManager = new ScreenManager(gameSettings);
        renderEngine = new RenderEngine(screenManager);
        physicsEngine = new PhysicsEngine(screenManager);
        renderEngine.addMouseListener(new MouseController());
        players = new ArrayList<>(){{
            add(p1);
            add(p2);
        }};
        overworldEngine = new OverworldEngine(screenManager);
    }

    public Graphics getGraphics(){
        return renderEngine.getGraphics();
    }

    public void initializeWindow(JFrame gameWindow){
        Container contentPane = gameWindow.getContentPane();
        contentPane.add(renderEngine);

    }

    @Override
    public void run() {
        while(true){
            frameCounter++;
            long startTime = System.currentTimeMillis();
            //Update
            switch (players.get(0).getState()){
                case sideScroll:
                    physicsEngine.update();
                    break;
                case overWorld:
                    overworldEngine.update();
                    break;
            }
            screenManager.update();
            //Render
            renderEngine.draw();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime >= 0) {
                try {
                    if(endTime-startTime > 0 /*&& frameCounter % 60 == 0*/)
                    Debug.success(DebugEnabler.FPS_CURRENT,"Current FPS: " + 1000 / (endTime - startTime) );
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Debug.error(DebugEnabler.FPS, "Thread Interupted: " + e.toString());
                }
            } else {
                Debug.warning(DebugEnabler.FPS,"FPS below 60! - current FPS: " + 1000 / (endTime - startTime) );
            }
        }
    }
}
