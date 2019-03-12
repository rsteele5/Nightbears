package gameobject.renderable.vendor;

import gameengine.GameEngine;
import gameengine.MyTimerTask;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.*;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import main.utilities.AssetLoader;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Vendor extends RenderableObject {
    private CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<RenderableObject> rItems = new CopyOnWriteArrayList<>();
    private BufferedImage vendorOverworldImage;
    private BufferedImage vendorLevelImage;
    private final String vendorOverworldPath = "/assets/vendor/vendoridleanimation/VendorOverworldForward.png";
    private final String vendorLevelPath = "/assets/vendor/Vendor.png";
    /* Restock timer */
    public static TimerTask restockTimer;

    // Default constructor
    public Vendor(int x, int y){
        super(x, y);
        this.imagePath = vendorLevelPath;
        this.drawLayer = DrawLayer.Entity;
        initializeItems();
        restockTimer = new MyTimerTask();
        //startRestockTimer();
    }

    private void initializeItems() {
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );
        items.add(new ArmorBuilder()
                .buildArmor()
        );

        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );
        items.add(new WeaponBuilder()
                .buildWeapon()
        );

        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );
        items.add(new ConsumableBuilder()
                .buildConsumable()
        );

        /*items.add(new WeaponBuilder()
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
        );*/

        if (items.size() > 0) {
            items.sort(new ItemComparator());
        }

        for (Item item : items){
            rItems.add((RenderableObject) item);
        }


    }

    @Override
    public void update() {

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

    public CopyOnWriteArrayList<Item> restockItems(){
        CopyOnWriteArrayList<Item> restock = new CopyOnWriteArrayList<>();
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );
        restock.add(new ArmorBuilder()
                .buildArmor()
        );

        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );
        restock.add(new WeaponBuilder()
                .buildWeapon()
        );

        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );
        restock.add(new ConsumableBuilder()
                .buildConsumable()
        );

        if (restock.size() > 0) {
            restock.sort(new ItemComparator());
        }

        rItems.removeAll(rItems);
        for (Item item : restock){
            rItems.add((RenderableObject) item);
        }

        return restock;
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
}

