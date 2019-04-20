package gameobject.renderable.item.consumable;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.AffectType;
import gameobject.renderable.item.DescriptionAssistant;
import gameobject.renderable.item.ItemMeta;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.Random;

public class ConsumableBuilder {
    //region <Variables>

    /**
     * Static global variables store and manipulate the min and max consumable points throughout the game
     */
    //TODO: Might not need to be static once the player and vendor classes are fixed
    private int maxConsumable = (int)(ItemMeta.maxConsumable * ItemMeta.amplifier);
    private int minConsumable = (int)(ItemMeta.minConsumable * ItemMeta.amplifier);

    // Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = DrawLayer.Entity;
    //TODO: Create more consumable images and separate into type arrays
    private String [] potionImg = {"/assets/Items/redPotion.png", "/assets/Items/bluePotion.png", "/assets/Items/cookie.png", "/assets/Items/broccoli.png"};

    // Consumable requirements
    /** Random allows the builder to assign random attributes to each item **/
    private Random rand = new Random();
    private ConsumableType _type;
    private AffectType _affect;
    private int _maxAffect = 0;
    private int _minAffect = 0;
    private String _quality = "";

    // Item requirements
    /** DescriptionAssistant is utilized to generate the name and description for each item **/
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = "";
    private String _description = "";
    private int _value = 0;
    //endregion

    /**
     * This function can be called with or without assigning attributes. If no attributes
     * are assigned, then random attributes will be assigned.
     * @return the newly created item
     */
    public Consumable buildConsumable() {
        type(this._type);
        affect(this._affect);
        imagePath(this._imagePath);
        minmaxAffect(this._minAffect, this._maxAffect);
        quality(this._quality);
        value(this._value);
        name(this._name);
        description(this._description);
        return new Consumable(_x, _y, _imagePath, _layer, _name, _value, _type, _affect, _maxAffect, _minAffect, _quality, _description);
    }

    //region <Setters>

    /**
     * Sets the position of the consumable item
     * @param x is the x-location
     * @param y is the y-location
     * @return the ConsumableBuilder
     */
    public ConsumableBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    /**
     * Sets the draw layer of the consumable item
     * @param _layer is the draw layer
     * @return the ConsumableBuilder
     */
    public ConsumableBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    /**
     * Sets the type of consumable. If no type was assigned, then a random type will be assigned to the
     * consumable item.
     * @param _type consumable type (edible, throwable, spell, ammunition)
     * @return the ConsumableBuilder
     */
    public ConsumableBuilder type(ConsumableType _type) {
        if (_type == null){
            this._type = ConsumableType.values()[rand.nextInt(ConsumableType.values().length)];
        } else {
            this._type = _type;
        }
        return this;
    }

    /**
     * Sets the affect type according to the consumable type. If no affect type was assigned, then a random
     * affect type will be assigned.
     * @param _affect affect type (fire, puncture, healthBoost, healthLevel, enchant)
     */
    public ConsumableBuilder affect(AffectType _affect) {
        if (_affect == null){
            // Set a temporary effect
            this._affect = AffectType.healthBoost;
            if (_type == ConsumableType.edible ){
                int rand = getRandomNumber(1,2);
                if (rand == 1) this._affect = AffectType.healthBoost;
                else this._affect = AffectType.healthLevel;
            }else if (_type == ConsumableType.throwable ){
                this._affect = AffectType.fire;
            } else if (_type == ConsumableType.spell) {
                this._affect = AffectType.enchant;
            }
        } else {
            this._affect = _affect;
        }
        return this;
    }

    /**
     * Sets the imagePath of the consumable item. If no imagePath was assigned, then a random image will be
     * assigned according to the type and effect of the consumable.
     * @param _imagePath is the main imagePath
     * @return the ConsumableBuilder
     */
    //TODO: Add function for multiple image arrays according to type and affect
    public ConsumableBuilder imagePath(String _imagePath) {
        if (_imagePath.equals("")){
            if (this._affect == AffectType.healthBoost) this._imagePath = potionImg[2];
            else if (this._affect == AffectType.healthLevel) this._imagePath = potionImg[3];
            else if (this._affect == AffectType.enchant) this._imagePath = potionImg[1];
            else this._imagePath = potionImg[0];
        } else {
            this._imagePath = _imagePath;
        }
        return this;
    }

    /**
     * Sets the min and max affect of the item. If no values are assigned, then random values
     * will be assigned according to the minimum and maximum affect of consumable items.
     * @param _minAffect is the minimum affect value
     * @param _maxAffect is the maximum affect value
     * @return the ConsumableBuilder
     */
    public ConsumableBuilder minmaxAffect(int _minAffect, int _maxAffect) {
        if (_maxAffect == 0){
            int num1 = getRandomNumber(minConsumable, maxConsumable);
            int num2 = getRandomNumber(minConsumable, maxConsumable);
            if(num1 == num2 && num1 > minConsumable) num1--;
            else if(num1 == num2 && num1 < maxConsumable) num2++;
            this._maxAffect = (num1 >= num2)? num1 : num2;
            this._minAffect = (num1 <= num2)? num1 : num2;
        } else {
            this._maxAffect = _maxAffect;
            this._minAffect = _minAffect;
        }
        return this;
    }

    /**
     * Determines the quality of the item by dividing the range of the min and max affect values
     * by 3 and assigning a string descriptor to the category the affect value falls within. This attribute
     * cannot be assigned a string value (it is private); it can only be determined within this function.
     * @param _quality is the quality descriptor
     */
    private void quality(String _quality){
        if (_quality.equals("")){
            int partition = (int) (Math.ceil(maxConsumable +1 - minConsumable) / 3);  //Three possible qualities: good, better, best
            if (_maxAffect <= (minConsumable + partition)) this._quality = "good";
            else if (_maxAffect > (maxConsumable - partition)) this._quality =  "best";
            else this._quality =  "better";
        } else {
            this._quality = _quality;
        }
    }

    /**
     * Sets the value of the item. If no value was assigned, then a random value will be
     * assigned according to the minimum and maximum affect of the armor item.
     * @param _value is the item's value
     * @return the ConsumableBuilder
     */
    public ConsumableBuilder value(int _value) {
        if (_value == 0){
            this._value = getRandomNumber(_minAffect, _maxAffect) * 4;
        } else {
            this._value = _value;
        }
        return this;
    }

    /**
     * Sets the name of the item. If no name was assigned, then a random name will be
     * assigned according to the type and quality of the item.
     * @param _name is the item's name
     * @return the ArmorBuilder
     */
    public ConsumableBuilder name(String _name) {
        if (_name.equals("")){
            this._name = assistant.getConsumableName(_type, _quality);
        } else {
            this._name = _name;
        }
        return this;
    }

    /**
     * Sets the description of the consumable item. If no description was assigned, then a random description will be
     * assigned according to the type, affect, quality, and name of the consumable item.
     * @param _description is the consumable's description
     */
    private void description(String _description) {
        if (_description.equals("")){
            this._description = assistant.getConsumableDescription(_type, _affect, _quality);
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
