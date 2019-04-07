package gameengine;

import gameengine.gamedata.GameData;
import gameengine.gamedata.VendorData;
import gameobject.renderable.vendor.Vendor;
import input.listeners.Key.KeyController;
import input.listeners.MouseController;
import gameengine.physics.PhysicsEngine;
import gameengine.rendering.RenderEngine;
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import javax.swing.JFrame;
import java.awt.Container;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

    private final int FRAMES_PER_SECOND = 60;
    private int frameCounter = 0;



    private GameData gameData;

    private ScreenManager screenManager;
    private RenderEngine renderEngine;
    public Vendor vendor;
    public VendorData vendorData;

    public GameEngine(GameData gameData){
        vendor = new Vendor(0,0, vendorData);
        this.gameData = gameData;
        screenManager = new ScreenManager(gameData);
        renderEngine = new RenderEngine(gameData, screenManager);
        renderEngine.addMouseListener(new MouseController(screenManager));
        renderEngine.addKeyListener(new KeyController(screenManager));
        renderEngine.setFocusTraversalKeysEnabled(false);                   //Lets us utilize TAB in te keyController.
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
            screenManager.update();
            //Render
            renderEngine.draw();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);


            if (sleepTime >= 0) {
                try {
                    if(endTime-startTime > 0 /*&& frameCounter % 60 == 0*/) {
                        Debug.success(DebugEnabler.FPS_CURRENT,"Current FPS: " + 1000 / (endTime - startTime) );
                        renderEngine.setFPS("Current FPS: " + 1000 / (endTime - startTime));
                        TimeUnit.MILLISECONDS.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    Debug.error(DebugEnabler.FPS_WARNING, "Thread Interupted: " + e.toString());
                }
            } else {
                Debug.warning(DebugEnabler.FPS_WARNING,"FPS_WARNING below 60! - current FPS_WARNING: " + 1000 / (endTime - startTime) );
            }
        }
    }
}
