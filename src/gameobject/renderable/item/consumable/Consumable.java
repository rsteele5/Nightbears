package gameobject.renderable.item.consumable;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.AffectType;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import gameobject.renderable.item.ItemMeta;
import main.utilities.AssetLoader;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import static java.lang.Math.round;

public class Consumable extends Item implements Serializable {

    //region <Variables>
    /** General item variables **/
    private transient BufferedImage icon;
    protected String name;
    private String description;
    protected int value;

    /** Consumable variables pertain only to consumable items **/
    private AffectType affect;
    protected ConsumableType type;
    private int minAffect;
    private int maxAffect;
    private String quality;
    //endregion

    //region <Constructors>

    /**
     * Consumable constructor is used only by the ConsumableBuilder
     * @param x is x-location
     * @param y is y-location
     * @param imagePath is the main image associated with the consumable
     * @param layer is the draw layer
     * @param name is the name assigned to the item
     * @param value is the item's worth
     * @param type is the type of consumable (edible, throwable, spell, or ammunition)
     * @param affect is the affect type (fire, puncture, healthBoost, healthLevel, enchant)
     * @param maxAffect is the maximum effect value
     * @param minAffect is the minimum effect value
     * @param quality describes the quality (good, better, best)
     * @param description is a full description of the consumable attributes
     */
    Consumable(int x, int y, String imagePath, DrawLayer layer,
               String name, int value, ConsumableType type,
               AffectType affect, int maxAffect, int minAffect, String quality, String description){
        super(x, y, imagePath, layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.affect = affect;
        this.maxAffect = maxAffect;
        this.minAffect = minAffect;
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
     * @return name assigned to consumable item
     */
    @Override
    public String getItemName() {
        return name;
    }

    /**
     * Getter for the vendor and inventory screen
     * @param desc is the item description created by the Description Assistant
     * @return full description that includes the affect type, effectiveness, and value
     */
    @Override
    public String getDescription(boolean desc) {
        return name +
                "\nAffect: " + affect.name() +
                "\nEffectiveness: " + minAffect + "-" + maxAffect +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    /**
     * Getter
     * @return always returns Consumable
     */
    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Consumable;
    }

    /**
     * Getter
     * @return item type (consumable, weapon, or armor)
     */
    @Override
    public int getType() {
        return type.ordinal();
    }

    /**
     * Getter
     * @returns subtype (edible, throwable, enchant)
     */
    public AffectType getAffectType(){
        return affect;
    }

    /**
     * Getter
     * @return affect type (fire, puncture, healthBoost, healthLevel, enchant)
     */
    public int getAffect() {
        return affect.ordinal();
    }

    /**
     * Getter
     * @return value of consumable item
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Getter
     * @return minimum affect value
     */
    public int getMinAffect() { return minAffect;}

    /**
     * Getter
     * @return maximum affect value
     */
    public int getMaxAffect() { return maxAffect;}
    //endregion

    //region <Setters>
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
    //endregion

    //TODO: Consumables are one-time use items. Add item update here.
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



}
