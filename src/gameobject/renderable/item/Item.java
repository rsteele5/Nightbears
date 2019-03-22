package gameobject.renderable.item;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.armor.ArmorBuilder;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public abstract class Item extends RenderableObject {

    public Item(int x, int y, String imagePath, DrawLayer layer) {
        super(x,y,imagePath,layer);
    }

    public abstract BufferedImage getIcon();
    public abstract String getItemName();
    public abstract String getDescription(boolean desc);
    public abstract ItemCategory getCategory();
    public abstract int getType();  //TODO: find best way of sending subtype
    public abstract int getValue();
    public abstract void setValue(int n);
    public int depreciate(int value){
        return round((float)(value * (ItemMeta.depreciator)));
    }

}
