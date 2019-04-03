package gameobject.renderable.button;

import gameobject.renderable.item.Item;
import gameobject.renderable.DrawLayer;
import main.utilities.Action;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ItemButton extends Button{

    private Item item;
    private static String notSelectedImagePath = "/assets/buttons/Button-Inventory-Square.png";
    private static String selectedImagePath = "/assets/buttons/Button-Inventory-Selected.png";
    private BufferedImage notSelectedImage;
    private BufferedImage selectedImage;
    /*private enum Slot{
        Helmet,
        OffHand,
        Chest,
        Weapon,
        Legs,
        Feet,
        Item
    }
    private Slot slot= Slot.Item;*///Obsolete

    public ItemButton(int x, int y, DrawLayer drawLayer) {
        super(x, y, notSelectedImagePath, drawLayer);
    }

    public ItemButton() {
        this(0, 0, DrawLayer.Entity);
    }

    public ItemButton(int x, int y, DrawLayer drawLayer, Action handleOnClick) {
        super(x, y, notSelectedImagePath, drawLayer, handleOnClick);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /*public int getSlot() {
        return slot.ordinal();
    }

    public void setSlot(int slot) {
        switch (slot) {
            case 0 : this.slot = Slot.Helmet; break;
            case 1 : this.slot = Slot.OffHand; break;
            case 2 : this.slot = Slot.Chest; break;
            case 3 : this.slot = Slot.Weapon; break;
            case 4 : this.slot = Slot.Legs; break;
            case 5 : this.slot = Slot.Feet; break;
            default: this.slot = Slot.Item; break;
        }
    }*///Obsolete

    public void resetItem() {this.item = null;}

    public void select(){
        Debug.log(true, "Selected");
        setCurrentImage(selectedImage);
    }

    @Override
    public void setCurrentImage(BufferedImage image) {
        this.image = image;
    }

    public void deSelect(){
        Debug.log(true, "Deselected");
        setCurrentImage(notSelectedImage);
    }

    @Override
    public void update() {
        if(isClicked) {
            select();
            isClicked = false;
            onClick.doIt();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,graphics,
                new Rectangle2D.Double(position.x, position.y, width, height));
        graphics.drawImage(image, position.x , position.y, width, height, null);
        //If the image is not null draw it offset in the center of the button
        if(item != null)
            graphics.drawImage(item.getIcon(), position.x +7, position.y + 7, width -14, height -14, null);
    }

    @Override
    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            notSelectedImage = image;
            selectedImage = AssetLoader.load(selectedImagePath);
            if(width == 0 && height == 0) {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }


}
