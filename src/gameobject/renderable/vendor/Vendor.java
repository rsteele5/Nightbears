package gameobject.renderable.vendor;

import gameengine.MyTimerTask;
import gameengine.gamedata.GameData;
import gameengine.gamedata.VendorData;
import gameengine.physics.*;
import gameengine.rendering.animation.Animator;
import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.house.overworld.OverworldMeta;
import gameobject.renderable.player.Player;
import gameobject.renderable.text.DialogBox;
import gamescreen.GameScreen;
import gamescreen.gameplay.GamePlayScreen;
import main.utilities.Action;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Vendor extends CollidableRenderable implements Interactable, Serializable {

    //region <Variables>
    private BufferedImage vendorOverworldImage;
    private BufferedImage vendorLevelImage;
    private final String vendorOverworldPath = "/assets/vendor/vendoridleanimation/VendorOverworldForward.png";
    private final String vendorLevelPath = "/assets/vendor/Vendor.png";
    public static TimerTask restockTimer;
    private VendorData vendorData;
    private VendorState vendorState;
    private int endCrawl;
    private Action intro;
    private Action playerInteractionOW;
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
        super(x, y, "",DrawLayer.Entity, 1f);
        vendorState = VendorState.hiding;
        this.imagePath = vendorLevelPath;
        this.vendorData = vendorData;
        restockTimer = new MyTimerTask(vendorData);
        //startRestockTimer();

        animator = new Animator(this);
        animator.addAnimation("Wait", new VendorUnderAnimation());
        animator.addAnimation("Crawling", new VendorCrawlingAnimation());
        animator.addAnimation("SittingUp", new VendorSittingUpAnimation());
        animator.addAnimation("Idle", new VendorIdleAnimation());
    }
    //endregion

    @Override
    public void update() {
        if (vendorState == VendorState.crawling) {
            if (animator.getCurrentAnimationName().equals("Wait") && animator.getCurrentAnimation().getFrameToDisplay() > 0)
                animator.setAnimation("Crawling");
            else if (animator.getCurrentAnimationName().equals("Crawling")) {
                if(getX() <= endCrawl) this.translate(2, 0);
                else this.setState(VendorState.sittingup);
            }
        }
        else if (vendorState == VendorState.sittingup){
            if (this.animator.getCurrentAnimation().getFrameToDisplay() >= 7){
                this.setState(VendorState.idle);
                intro.doIt();
            }
        }
    }

    public void setImage(String imagePath){ this.imagePath = imagePath; }

    public void setIntroduction(Action intro) { this.intro = intro; }

    public VendorState getVendorState() { return vendorState; }

    public Animator getAnimator() { return animator; }

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
                endCrawl = getX() + OverworldMeta.TileSize*2;
                animator.setAnimation("Wait");
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

    //Collidable
    /**
     * @return the collision box of the Collidable
     */
    @Override
    public Rectangle getCollisionBox() {
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
    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.interactables.add(this);
            return true;
        }return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.interactables.remove(this);
            return true;
        }return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.interactables.remove(this);
        if(isActive){
            screen.interactables.add(this);
        }
    }
    //endregion
}

