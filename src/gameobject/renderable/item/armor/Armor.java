package gameobject.renderable.item.armor;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import gameobject.renderable.item.ItemMeta;
import main.utilities.AssetLoader;

import static java.lang.Math.round;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Armor extends Item implements Serializable {

    //region <Variables>
    /** General item variables **/
    private transient BufferedImage icon;
    protected String name;
    private String description;
    protected int value;
    private String quality;

    //protected FundamentalProperty;
    //TODO check on mass with austin/hunter
    //protected int mass;

    /** Armor variables pertain only to armor items **/
    private int armor;
    private ArmorType type;
    //endregion

    //region <Constructors>
    /**
     * Armor constructor is utilized only by the ArmorBuilder
     * @param x is x-location
     * @param y is y-locatioin
     * @param imagePath is the main image associated with the item
     * @param layer is the draw layer
     * @param name is the name assigned to the item
     * @param value is the item's worth
     * @param type is the type of armor (head, chest, leg, foot, or magic)
     * @param armor is the protection value
     * @param quality describes the quality (good, better, best)
     * @param description is a full description of armor attributes
     */
    Armor(int x, int y, String imagePath, DrawLayer layer,
          String name, int value, ArmorType type, int armor, String quality, String description) {
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.armor = armor;
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
     * @return name assigned to armor item
     */
    @Override
    public String getItemName() {
        return name;
    }

    /**
     * Getter for the vendor and inventory screen
     * @param desc is the item description created by the Description Assistant
     * @return full description that includes the type, armor points, and value
     */
    @Override
    public String getDescription(boolean desc) {
        return name +
                "\nType: " + type.name() +
                "\nArmor Points: " + armor +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    /**
     * Getter
     * @return always returns Armor
     */
    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Armor;
    }

    /**
     * Getter
     * @return item type (head, chest, leg, foot, or magic)
     */
    @Override
    public int getType() {
        return type.ordinal();
    }

    /**
     * Getter
     * @return value of armor item
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Getter
     * @return item's quality (good, better, best)
     */
    public String getQuality(){return quality;}

    /**
     * Getter
     * @return item's value
     */
    public int getArmorValue() { return armor; }
    //endregion

    /**
     * Sets the value of the item
     * @param value is the new value
     */
    public void setValue(int value) {
        this.value = value;
    }


    //TODO: Armor may be damaged or upgraded. Add item update here.
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
