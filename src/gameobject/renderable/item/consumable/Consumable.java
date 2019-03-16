package gameobject.renderable.item.consumable;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.AffectType;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemCategory;
import main.utilities.AssetLoader;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Consumable extends RenderableObject implements Item {

    // Item Variables
    protected BufferedImage icon;
    protected String name;
    protected String description;
    protected int value;

    // Consumable variables
    protected AffectType affect;
    protected ConsumableType type;
    protected int minAffect;
    protected int maxAffect;
    private String quality;


    protected Consumable(int x, int y, String imagePath, DrawLayer layer,
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
                "\nAffect: " + affect.name() +
                "\nEffectiveness: " + minAffect + "-" + maxAffect +
                "\nValue: " + value + " gold" +
                (desc ? description : "");
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.Consumable;
    }

    @Override
    public int getType() {
        return type.ordinal();
    }

    public int getAffect() {
        return affect.ordinal();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void depreciate() {
        this.value = round((float)(value * (0.9)));
    }

    public int getMinAffect() { return minAffect;}

    public int getMaxAffect() { return maxAffect;}

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
        ConsumableBuilder.minConsumable += num;
        ConsumableBuilder.maxConsumable += num;
    }

}
