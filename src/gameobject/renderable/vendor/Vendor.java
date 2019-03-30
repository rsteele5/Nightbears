package gameobject.renderable.vendor;

import gameengine.MyTimerTask;
import gameengine.gamedata.VendorData;
import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameengine.rendering.animation.Animator;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.house.overworld.OverworldMeta;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import main.utilities.Action;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Vendor extends RenderableObject implements Kinematic, Interactable, Serializable {

    //region <Variables>
    private BufferedImage vendorOverworldImage;
    private BufferedImage vendorLevelImage;
    private final String vendorOverworldPath = "/assets/vendor/vendoridleanimation/VendorOverworldForward.png";
    private final String vendorLevelPath = "/assets/vendor/Vendor.png";
    public static TimerTask restockTimer;
    private VendorData vendorData;
    private VendorState vendorState;
    private int endCrawl;
    private Action playerInteractionOW;

    private static String firstNotice = "I created lots of goodies that might help you defeat those monsters. Come see what I have!";
    private static String subsequentNotices = "I have all NEW items that are even more powerful than before! Come see what I have!";
    private static String firstLevel = "Whew! That was a super scary monster!";


    int isSet = 0;
    Player p = null;

    //endregion

    /**
     * VendorState specifies when vendor is active or inactive
     */
    public enum VendorState {
        hiding,
        crawling,
        sittingup,
        idle
    }

    // Default constructor
    public Vendor(int x, int y, VendorData vendorData){
        super(x, y);
        vendorState = VendorState.hiding;
        this.imagePath = vendorLevelPath;
        this.drawLayer = DrawLayer.Entity;
        this.vendorData = vendorData;
        restockTimer = new MyTimerTask(vendorData);
        //startRestockTimer();

        animator = new Animator(this);
        animator.addAnimation("Crawling", new VendorCrawlingAnimation());
        animator.addAnimation("SittingUp", new VendorSittingUpAnimation());
        animator.addAnimation("Idle", new VendorIdleAnimation());
    }
    //endregion

    @Override
    public void update() {
        isSet++;
        isSet %= 5;
        if(isSet == 4 && p != null){
            p.interaction = false;
            p = null;
        }

        if (vendorState == VendorState.crawling) {
            if (getX() <= endCrawl)
                this.translate(5, 0);
            else this.setState(VendorState.sittingup);
        }
        else if (vendorState == VendorState.sittingup){
            if (this.animator.getCurrentAnimation().getFrameToDisplay() == 7){
                this.setState(VendorState.idle);
            }
        }
    }

    public void setImage(String imagePath){ this.imagePath = imagePath; }

    public VendorData getVendorData(){
        return vendorData;
    }

    public BufferedImage getOverworldImage(){
        return vendorOverworldImage;
    }

    public BufferedImage getLevelImage(){
        return vendorLevelImage;
    }

    @Override
    public void load() {
        if(vendorLevelImage == null || vendorOverworldImage == null){
            vendorLevelImage = AssetLoader.load(vendorLevelPath);
            vendorOverworldImage = AssetLoader.load(vendorOverworldPath);
            image = vendorOverworldImage;
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }

    public boolean setState(VendorState vs) {
        //TODO: Implement error checking
        switch (vs) {
            case hiding:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Vendor-State: hidden");
                vendorState = vs;
                return true;
            case crawling:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Vendor-State: crawling");
                width = 200;
                height = 200;

                endCrawl = getX() + OverworldMeta.TileSize;
                animator.setAnimation("Crawling");
                vendorState = vs;
                return true;
            case sittingup:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Vendor-State: sitting up");
                animator.setAnimation("SittingUp");
                vendorState = vs;
                return true;
            case idle:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Vendor-State: idle");
                width = 200;
                height = 200;
                animator.setAnimation("Idle");
                vendorState = vs;
                return true;
        }
        return false;
    }

    public VendorState getState() { return vendorState;}

    //TODO: Don't think I need this anymore
    public void startRestockTimer(){
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(restockTimer, 0, 1000*1000);
        //cancel after sometime to avoid overlap
        /*try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void setPlayerInteractionOW(Action playerInteractionOW) {
        this.playerInteractionOW = playerInteractionOW;
    }

    //region <Kinematic>
    @Override
    public PhysicsVector getVelocity() {
        return PhysicsVector.ZERO;
    }

    @Override
    public void setVelocity(PhysicsVector pv) { }

    @Override
    public PhysicsVector getAcceleration() {
        return PhysicsVector.ZERO;
    }

    @Override
    public boolean isStatic(){
        return  true;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x + (int)(image.getWidth()*.25), y + (int)(image.getHeight()*.25),
                (int) (image.getWidth()*.5), (int)(image.getHeight()*.5));
    }
    //endregion

    //region <Interactable>
    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x-20,y-20,image.getWidth()+20,image.getHeight()+20);
    }

    @Override
    public void setRequesting(boolean isRequesting) { }

    @Override
    public boolean isRequesting() { return false; }

    @Override
    public boolean action(GameObject g) {
        if(g instanceof Player) {
            if(((Player)g).getState() == Player.PlayerState.overWorld && playerInteractionOW != null)
            playerInteractionOW.doIt();
            return true;
        }return false;

    }
    //endregion

    //region <GameScreen Methods>
    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            screen.kinematics.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.kinematics.remove(this);
        if(isActive) {
            screen.kinematics.add(this);
        }
    }
    //endregion
}

