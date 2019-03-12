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
    protected BufferedImage icon;
    protected String name;
    protected String description;
    protected int value;

    // Armor Variables
    protected int armor;
    //protected FundamentalProperty;
    //TODO check on mass with austion/hunter
    //protected int mass;
    protected ArmorType type;

    protected Armor(int x, int y, String imagePath, DrawLayer layer,
                     String name, int value, ArmorType type, int armor) {
        super(x,y,imagePath,layer);
        this.name = name;
        this.value = value;
        this.type = type;
        this.armor = armor;
        setDescription("Testing the armor description field now. aaaaaaaaa aaaaaaaaaaa aaaaaaaaa aaaaaaa");
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
        //TODO make this general for all armor
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

    public int getArmorValue() { return armor; }

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
    public void increaseMaxAttribute(int num){
        ArmorBuilder.maxArmor += num;
        ArmorBuilder.minArmor += num;
    }

}
