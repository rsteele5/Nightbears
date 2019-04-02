package gamescreen.gameplay.level;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsObject;
import gameengine.rendering.Camera;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.SideScrollKeyHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class SideScroll extends GameScreen {
    private CopyOnWriteArrayList<GameObject> onScreen;
    private CopyOnWriteArrayList<Kinematic> kinematicObjects;
    private SideScrollUI UI;
    final int xOFF = 2500;
    Player player;
    final int yOFF = 1500;
    public SideScroll(ScreenManager screenManager) {
        super(screenManager, "level", true, 1f);


    }

    /**
     * Initializes all of the stuff you want on your splashscreen
     */
    @Override
    protected void initializeScreen() {

        String bg = "assets/backgrounds/mountains.jpg";
        String path = "/assets/testAssets/square.png";
        ImageContainer background;
        background = (new ImageContainer(0,0, bg, DrawLayer.Background));
        background.addToScreen(this,true);


        PhysicsObject physicsObject;

        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                physicsObject = new PhysicsObject(xOFF + x1 * 75 + 100,yOFF + y1*75,path,DrawLayer.Entity);
                physicsObject.addToScreen(this, true);
            }
        }


        for(int x1 = 4; x1 > 0; x1--){
            for(int y1 = 0; y1 < x1; y1++){
                physicsObject = new PhysicsObject(xOFF - x1 * 75,yOFF + y1*75,path,DrawLayer.Entity);
                physicsObject.addToScreen(this, true);
            }
        }

//        PhysicsObjectStatic physicsObjectStatic = new PhysicsObjectStatic(xOFF - 300,yOFF + 300, "/assets/testAssets/obsidian.jpg", DrawLayer.Entity);
//        PhysicsObjectStatic physicsObjectStatic2 = new PhysicsObjectStatic(xOFF - 300,yOFF - 800, "/assets/testAssets/obsidian.jpg", DrawLayer.Entity);
//        PhysicsObjectStatic physicsObjectStatic1 = new PhysicsObjectStatic(xOFF +450,yOFF - 300, "/assets/testAssets/obsidian.jpg", DrawLayer.Entity);
//        PhysicsObjectStatic f;
//        for(int i = 0; i < 19; i++){
//            int offset = (i % 2 == 0) ? 50 : -50;
//            f = new PhysicsObjectStatic(xOFF - 425 + offset, yOFF + 200 - 50 * i,"/assets/testAssets/obsidian.jpg", DrawLayer.Entity);
//            f.setWidth(40);
//            f.setHeight(20);
//            f.addToScreen(this, true);
//        }
//        physicsObjectStatic.setWidth(2000);
//        //physicsObjectStatic.setHeight(200);
//        physicsObjectStatic.setHeight(500);
//        //physicsObjectStatic1.setWidth(50);
//        physicsObjectStatic1.setWidth(1000);
//        physicsObjectStatic1.setHeight(600);
//        physicsObjectStatic2.setWidth(1000);
//        physicsObjectStatic2.setHeight(600);
//        physicsObjectStatic.addToScreen(this, true);
//        physicsObjectStatic1.addToScreen(this, true);
//        physicsObjectStatic2.addToScreen(this, true);
        Player player = new Player(xOFF,yOFF,DrawLayer.Entity, gameData.getPlayerData());
        player.setState(Player.PlayerState.sideScroll);
        player.addToScreen(this,true);

        UI = new SideScrollUI(screenManager, this, player);
        addOverlay(UI);
        setCamera(new Camera(screenManager,this, player));
        setKeyHandler(new SideScrollKeyHandler(player));
    }


    @Override
    protected void transitionOff(){
        exiting = true;
    }
}
