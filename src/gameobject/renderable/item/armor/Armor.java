package gameobject.renderable.item.armor;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import main.utilities.AssetLoader;

import static java.lang.Math.round;

import java.awt.image.BufferedImage;

public class Armor extends RenderableObject implements Item {

    // Item Variables
    private BufferedImage icon;
    protected String name;
    private String description;
    protected int value;

    // Armor Variables
    private int armor;
    //protected FundamentalProperty;
    //TODO check on mass with austin/hunter
    //protected int mass;
    private ArmorType type;
    private String quality;

    protected Armor(int x, int y, String imagePath, DrawLayer layer,
                     String name, int value, ArmorType type, int armor, String quality, String description) {
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.armor = armor;
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
        return name +
                "\nType: " + type.name() +
                "\nArmor Points: " + armor +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Armor;
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

    public int getArmorValue() { return armor; }

    @Override
    public void update() {

    }

    @Override
    public void load() {
        super.load();
        icon = AssetLoader.resizeImage(image, image.getWidth()/2, image.getHeight()/2);
    }
    public void increaseMaxAttribute(int num){
        ArmorBuilder.maxArmor += num;
        ArmorBuilder.minArmor += num;
    }

}
