package gameobject.renderable.vendor;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.*;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Vendor extends RenderableObject implements Kinematic , Interactable {
    private CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<RenderableObject> rItems = new CopyOnWriteArrayList<>();
    private BufferedImage vendorOverworldImage;
    private BufferedImage vendorLevelImage;
    private final String vendorOverworldPath = "/assets/vendor/vendoridleanimation/VendorOverworldForward.png";
    private final String vendorLevelPath = "/assets/vendor/Vendor.png";
    int isSet = 0;
    Player p = null;
    // Default constructor
    public Vendor(int x, int y){
        super(x, y);
        this.imagePath = vendorLevelPath;
        this.drawLayer = DrawLayer.Entity;
        initializeItems();
    }

    private void initializeItems() {
        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/club1.png")
                .name("My Fwirst Club")
                .type(WeaponType.Club)
                .value(10)
                .minDamage(1)
                .maxDamage(4)
                .critChance(6)
                .buildWeapon());

        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/spear1.png")
                .name("My Fwirst Spear")
                .type(WeaponType.Spear)
                .value(9)
                .minDamage(2)
                .maxDamage(6)
                .critChance(4)
                .buildWeapon());

        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword")
                .type(WeaponType.Sword)
                .value(11)
                .minDamage(4)
                .maxDamage(12)
                .critChance(3)
                .buildWeapon());

        items.add(new ConsumableBuilder()
                .imagePath("/assets/Items/bluepotion.png")
                .name("My Fwirst Spell Potion")
                .type(ConsumableType.spell)
                .affect(AffectType.enchant)
                .value(25)
                .minAffect(10)
                .maxAffect(15)
                .buildConsumable());

        items.add(new ArmorBuilder()
                .imagePath("/assets/Items/helmet1.png")
                .name("My Fwirst Helmet")
                .type(ArmorType.Head)
                .value(11)
                .armorPoints(12)
                .buildArmor());

        items.add(new ConsumableBuilder()
                .imagePath("/assets/Items/redpotion.png")
                .name("My Fwirst Fire Potion")
                .type(ConsumableType.throwable)
                .affect(AffectType.fire)
                .value(20)
                .minAffect(12)
                .maxAffect(16)
                .buildConsumable()
        );

        if (items.size() > 0) {
            items.sort(new ItemComparator());
        }

        for (Item item : items){
            rItems.add((RenderableObject) item);
        }


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

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }

    public CopyOnWriteArrayList<RenderableObject> getRenderables() {
        return rItems;
    }

    public void setImage(String imagePath){ this.imagePath = imagePath; }

    public void addItem(Item item){
        items.add(item);
        rItems.add((RenderableObject) item);
    }

    public void removeItem(Item item){
        items.remove(item);
        rItems.remove(item);
    }

    // Needed for vendor splashscreen
    public void replaceList(CopyOnWriteArrayList<Item> updatedItems){
        this.items = updatedItems;
        rItems.removeAll(rItems);
        for (Item item : items){
            rItems.add((RenderableObject) item);
        }
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

        if(isActive) {
            screen.kinematics.add(this);
        }
    }

    @Override
    public Rectangle hitbox() {
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
}

