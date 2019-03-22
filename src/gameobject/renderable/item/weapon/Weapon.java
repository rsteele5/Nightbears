package gameobject.renderable.item.weapon;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import gameobject.renderable.item.ItemMeta;
import main.utilities.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import static java.lang.Math.round;

public class Weapon extends Item implements Kinematic, Serializable {
    //region <Variables>

    /** General item variables **/
    private BufferedImage icon;
    protected String name;
    private String description;
    protected int value;

    /** Weapon variables pertain only to weapon items **/
    protected WeaponType type;
    private int maxDamage;
    private int minDamage;
    private int critChance;
    private String quality;
    //endregion

    //region <Constructors>
    /**
     * Weapon constructor is used only by the WeaponBuilder
     * @param x is x-location
     * @param y is y-location
     * @param imagePath is the main image associated with the item
     * @param layer is the draw layer
     * @param name is the name assigned to the item
     * @param value is the item's worth
     * @param type is the type of weapon (sword, club, spear)
     * @param minDamage is the minimum damage value
     * @param maxDamage is the maximum damage value
     * @param critChance is the possibility of a critical hit
     * @param quality describes the quality (good, better, best)
     * @param description is a full description of the weapon attributes
     */
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
    //endregion

    //region <Getters>

    /**
     * Getter
     * @return icon image
     */
    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * Getter
     * @return name assigned to weapon item
     */
    @Override
    public String getItemName() {
        return name;
    }

    /**
     * Getter for the vendor and inventory screen
     * @param desc is the item description created by the Description Assistant
     * @return full description that includes the weapon type, damage, critChance, and value
     */
    @Override
    public String getDescription(boolean desc) {
        return  name +
                "\nType: " + type.name() +
                "\nDamage: " + minDamage + "-" + maxDamage +
                "\nCrit Chance: " + critChance + "%" +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    /**
     * Getter
     * @return always returns Weapon
     */
    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Weapon;
    }

    /**
     * Getter
     * @return item type (sword, club, spear)
     */
    @Override
    public int getType() {
        return type.ordinal();
    }

    /**
     * Getter
     * @return value of weapon item
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Getter
     * @return item's quality
     */
    public String getQuality(){return quality;}

    /**
     * Getter
     * @return item's minimum damage value
     */
    public int getMinDamage() { return minDamage;}

    /**
     * Getter
     * @return item's maximum damage value
     */
    public int getMaxDamage() { return maxDamage;}

    /**
     * Getter
     * @return item's critical hit possibility
     */
    public int getCritChance() { return critChance;}
    //endregion

    /**
     * Sets description attribute. Description is intended to be one or two sentences.
     * @param myDescription is a string description for the item.
     */
    private void setDescription(String myDescription) {
        description = "\n" + myDescription;
    }

    /**
     * Sets the value of the item
     * @param value is the new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void update() {

    }

    /**
     * All images are 100 x 100. This function reduces image size by half.
     */
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
}


