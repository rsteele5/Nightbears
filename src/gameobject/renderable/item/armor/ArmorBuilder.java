package gameobject.renderable.item.armor;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.DescriptionAssistant;
import gameobject.renderable.item.ItemMeta;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.Random;
import static gameobject.renderable.DrawLayer.Entity;

public class ArmorBuilder {
    //region <Variables>

    /**
     * Static global variables store and manipulate the min and max armor points throughout the game
     */
    //TODO: Might not need to be static once the player and vendor classes are fixed
    private int maxArmor = (int)(ItemMeta.maxArmor * ItemMeta.amplifier);
    private int minArmor = (int)(ItemMeta.minArmor * ItemMeta.amplifier);

    //Renderable requirements (default)
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = Entity;
    private String [] helmetImg = {"/assets/Items/helmet1.png", "/assets/Items/helmet2.png", "/assets/Items/helmet3.png"};
    private String [] chestImg = {"/assets/Items/chest1.png", "/assets/Items/chest2.png"};
    private String [] pantImg = {"/assets/Items/pants1.png", "/assets/Items/pants2.png"};
    private String [] feetImg = {"/assets/Items/feet1.png", "/assets/Items/feet1.png"};
    private String [] offhandImg = {"/assets/Items/cape1.png", "/assets/Items/cape2.png", "/assets/Items/cape3.png",
            "/assets/Items/cape4.png"};

    //Armor Requirements
    /** Random allows the builder to assign random attributes to each item **/
    private Random rand = new Random();
    private ArmorType _type;
    private int _armor = 0;
    private String _quality = "";

    //Item requirements (defaults)
    /** DescriptionAssistant is utilized to generate the name and description for armor item **/
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = "";
    private String _description = "";
    private int _value = 0;

    //TODO check on mass with Austin
    //private int mass;
    //endregion

    /**
     * This function can be called with or without assigning armor attributes. If no attributes
     * are assigned, then random attributes will be assigned.
     * @return armor item
     */
    public Armor buildArmor(){
        type(this._type);
        imagePath(this._imagePath);
        armorPoints(this._armor);
        quality(this._quality);
        value(this._value);
        name(this._name);
        description(this._description);
        return new Armor(_x, _y, _imagePath, _layer, _name, _value, _type, _armor, _quality, _description);
    }

    //region <Setters>
    /**
     * Sets the position of the armor item
     * @param x is the x-position
     * @param y is the y-position
     * @return the ArmorBuilder
     */
    public ArmorBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    /**
     * Sets the draw layer of the armor item
     * @param _layer is the draw layer
     * @return the ArmorBuilder
     */
    public ArmorBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    /**
     * Sets the type of armor. If no type was assigned, then a random type will be assigned to the
     * armor item.
     * @param _type armor type (head, chest, leg, foot, magic)
     * @return the ArmorBuilder
     */
    public ArmorBuilder type(ArmorType _type) {
        if (_type == null){
            this._type = ArmorType.values()[rand.nextInt(ArmorType.values().length)];
        } else {
            this._type = _type;
        }
        return this;
    }

    /**
     * Sets the imagePath of the armor item. If no imagePath was assigned, then a random image will be
     * assigned according to the type of armor.
     * @param _imagePath is the main imagePath
     * @return the ArmorBuilder
     */
    public ArmorBuilder imagePath(String _imagePath) {
        if (_imagePath.equals("")){
            switch (_type) {
                case Head:
                    this._imagePath = helmetImg[rand.nextInt(helmetImg.length)];
                    break;
                case Chest:
                    this._imagePath = chestImg[rand.nextInt(chestImg.length)];
                    break;
                case Leg:
                    this._imagePath = pantImg[rand.nextInt(pantImg.length)];
                    break;
                case Foot:
                    this._imagePath = feetImg[rand.nextInt(feetImg.length)];
                    break;
                default:
                    this._imagePath = offhandImg[rand.nextInt(offhandImg.length)];
                    break;
            }
        } else {
            this._imagePath = _imagePath;
        }
        return this;
    }

    /**
     * Sets the protection value of the armor item. If no armor point value was assigned, then a random
     * armor point value will be assigned according to the minimum armor and the maximum armor points
     * of the armor item.
     * @param _armor is the protection value
     * @return the ArmorBuilder
     */
    public ArmorBuilder armorPoints(int _armor) {
        if (_armor == 0){
            this._armor =  getRandomNumber(minArmor, maxArmor);
        } else {
            this._armor = _armor;
        }
        return this;
    }

    /**
     * Determines the quality of the item by dividing the range of the min and max armor values
     * by 3 and assigning a string descriptor to category the armor point value falls within. This attribute
     * cannot be assigned a string value (it is private); it can only be determined within this function.
     * @param _quality is the quality descriptor
     */
    private void quality(String _quality){
        if (_quality.equals("")){
            int partition = (int) (Math.ceil(maxArmor +1 - minArmor) / 3);  //Three possible qualities: good, better, best
            if (_armor < (minArmor + partition)) this._quality =  "good";
            else if (_armor > (maxArmor - partition)) this._quality =   "best";
            else this._quality =   "better";
        } else {
            this._quality = _quality;
        }
    }

    /**
     * Sets the value of the armor item. If no value was assigned, then a random value will be
     * assigned according to the minimum armor and the actual armor points of the armor item.
     * @param _value is the armor item's value
     * @return the ArmorBuilder
     */
    public ArmorBuilder value(int _value) {
        if (_value == 0){
            this._value = getRandomNumber(minArmor, _armor) * 2;
        } else{
            this._value = _value;
        }
        return this;
    }

    /**
     * Sets the name of the armor item. If no name was assigned, then a random name will be
     * assigned according to the type and quality of the armor item.
     * @param _name is the armor item's name
     * @return the ArmorBuilder
     */
    public ArmorBuilder name(String _name) {
        if (_name.equals("")){
            this._name = assistant.getArmorName(_type, _quality);
        } else {
            this._name = _name;
        }
        return this;
    }

    /**
     * Sets the description of the armor item. If no description was assigned, then a random description will be
     * assigned according to the type, quality, and name of the armor item.
     * @param _description is the armor's description
     */
    private void description(String _description) {
        if (_description.equals("")){
            this._description = assistant.getArmorDescription(_type, _quality, _name);
        }else {
            this._description = _description;
        }
    }
    //endregion

    /**
     * Provides a random number between two values
     * @param min is the minimum value
     * @param max is the maximum value
     * @return the random int value
     */
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }
}
