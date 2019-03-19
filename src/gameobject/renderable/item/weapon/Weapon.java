package gameobject.renderable.item.weapon;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import main.utilities.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Weapon extends RenderableObject implements Item, Kinematic {
    // Item Variables
    private BufferedImage icon;
    protected String name;
    private String description;
    protected int value;

    // Weapon Variables
    protected WeaponType type;
    private int maxDamage;
    private int minDamage;
    private int critChance;
    private String quality;

    Weapon(int x, int y, String imagePath, DrawLayer layer,
           String name, int value, WeaponType type, int minDamage, int maxDamage,
           int critChance, String quality, String description){
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.critChance = critChance;
        this.quality = quality;
        this.description = description;
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getDescription(boolean desc) {
        return  name +
                "\nType: " + type.name() +
                "\nDamage: " + minDamage + "-" + maxDamage +
                "\nCrit Chance: " + critChance + "%" +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Weapon;
    }

    @Override
    public int getType() {
        return type.ordinal();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void depreciate() {
        this.value = round((float)(value * (0.9)));
    }

    public String getQuality(){return quality;}

    public int getMinDamage() { return minDamage;}

    public int getMaxDamage() { return maxDamage;}

    public int getCritChance() { return critChance;}

    private void setDescription(String myDescription) {
        description = "\n" + myDescription;
    }

    @Override
    public void update() {

    }

    @Override
    public void load() {
        super.load();
        icon = AssetLoader.resizeImage(image, image.getWidth()/2, image.getHeight()/2);
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {

    }

    @Override
    public PhysicsVector getAcceleration() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {

    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public void increaseMaxAttribute(int num){
        WeaponBuilder.maxWeapon += num;
        WeaponBuilder.minWeapon += num;
    }

}


