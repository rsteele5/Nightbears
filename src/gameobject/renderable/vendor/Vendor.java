package gameobject.renderable.vendor;

import gameengine.MyTimerTask;
import gameengine.gamedata.VendorData;
import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.*;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import main.utilities.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Vendor extends RenderableObject implements Kinematic, Interactable, Serializable {

    //region <Variables>
    private BufferedImage vendorOverworldImage;
    private BufferedImage vendorLevelImage;
    private final String vendorOverworldPath = "/assets/vendor/vendoridleanimation/VendorOverworldForward.png";
    private final String vendorLevelPath = "/assets/vendor/Vendor.png";
    public static TimerTask restockTimer;
    private VendorData vendorData;

    int isSet = 0;
    Player p = null;
    //endregion

    // Default constructor
    public Vendor(int x, int y, VendorData vendorData){
        super(x, y);
        this.imagePath = vendorLevelPath;
        this.drawLayer = DrawLayer.Entity;
        this.vendorData = vendorData;
        restockTimer = new MyTimerTask(vendorData);
        //startRestockTimer();
    }

    @Override
    public void update() {
        isSet++;
        isSet %= 5;
        if(isSet == 4 && p != null){
            p.interaction = false;
            p = null;
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

    //region <Physics methods>
    private PhysicsVector accel = new PhysicsVector(0,1);

    PhysicsVector movement = new PhysicsVector(0,0);

    @Override
    public PhysicsVector getVelocity() {
        int gravSign = PhysicsMeta.Gravity != 0 ? 1 : 0;
        PhysicsVector pV = movement.add(new PhysicsVector(0,gravSign)).mult(accel);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;
        return new PhysicsVector(pV.x,y);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        movement = pv;
    }

    @Override
    public PhysicsVector getAcceleration() {
        return accel;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        accel = pv;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x + (int)(image.getWidth()*.25), y + (int)(image.getHeight()*.25), (int) (image.getWidth()*.5), (int)(image.getHeight()*.5));
    }

    @Override
    public boolean isStatic(){
        return  true;
    }

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

    @Override
    public Rectangle collision() {
        return new Rectangle(x,y,image.getWidth(),image.getHeight());
    }

    @Override
    public boolean action(GameObject g) {
        if(g instanceof Player) {
            ((Player) g).interaction = true;
            p = (Player)g;
        }
        isSet = 0;
        return true;
    }
    //endregion
}

