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
        p1 = new Player(0,0, "/assets/player/teddyIdleAnimation/Overworld-Teddy-Center.png", DrawLayer.Entity);
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
        //TODO: stuff
        while(true){
            frameCounter++;
            long startTime = System.currentTimeMillis();

            //TODO: Proposed changes
            /* Game engine gets the data from the splashscreen manager then sends it to the
             * physics engine. After its run its course then we send it to the renderEngine
             * So the call would look like:
             * Data data = screenManager.getData();
             * physicsEngine.update(data);
             * renderEngine.draw(data);
             * We just decoupled the screenManager from both the phys engine and the renderer
             * The game data is the fuel to make the engine run lol
             */

                //Update
            switch (players.get(0).getState()){
                case sideScroll:
                    physicsEngine.update();
                    break;
                case overWorld:
                    overworldEngine.update();
                    break;
            }
            //Render
            screenManager.update();
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

                }
            } else {
                Debug.warning(DebugEnabler.FPS,"FPS below 60! - current FPS: " + 1000 / (endTime - startTime) );
            }
        }
    }

    public void changeInputMethod(InputMethod controller) {
        //TODO: Implement changing controllers and stuff here
    }

    public void changeGraphicsOption(GraphicsOption graphicsOption) {
        //TODO: Implement changing graphics and stuff
    }

    public void changeSoundOption(SoundOption soundOption) {
        //TODO: Implement changing sound
    }
}
