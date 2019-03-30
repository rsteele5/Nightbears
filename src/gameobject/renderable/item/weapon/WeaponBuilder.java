package gameobject.renderable.item.weapon;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.DescriptionAssistant;
import gameobject.renderable.item.ItemMeta;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.Random;

public class WeaponBuilder {
    //region <Variables>

    /**
     * Static global variables store and manipulate the min and max  points throughout the game
     */
    //TODO: Might not need to be static once the player and vendor classes are fixed
    private int maxWeapon = (int)(ItemMeta.maxWeapon * ItemMeta.amplifier);
    private int minWeapon = (int)(ItemMeta.minWeapon * ItemMeta.amplifier);

    //Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = DrawLayer.Entity;
    private String [] spearImg = {"/assets/Items/spear1.png", "/assets/Items/spear2.png", "/assets/Items/spear3.png"};
    private String [] swordImg = {"/assets/Items/sword1.png", "/assets/Items/sword2.png", "/assets/Items/sword3.png"};
    private String [] clubImg = {"/assets/Items/club1.png", "/assets/Items/club2.png", "/assets/Items/club3.png"};

    //Weapon requirements
    /** Random allows the builder to assign random attributes to each item **/
    private Random rand = new Random();
    private WeaponType _type;
    private int _maxDamage = 0;
    private int _minDamage = 0;
    private int _critChance = 0;
    private String _quality = "";

    //Item requirements
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
    public Weapon buildWeapon(){
        type(this._type);
        imagePath(this._imagePath);
        minmaxDamage(this._minDamage, this._maxDamage);
        critChance(this._critChance);
        quality(this._quality);
        value(this._value);
        name(this._name);
        description(this._description);
        return new Weapon(_x,_y,_imagePath,_layer,_name,_value,_type, _minDamage, _maxDamage, _critChance, _quality, _description);
    }

    //region <Setters>

    /**
     * Sets the position of the weapon item
     * @param x is the x-location
     * @param y is the y-location
     * @return the WeaponBuilder
     */
    public WeaponBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    /**
     * Sets the draw layer of the weapon item
     * @param _layer is the draw layer
     * @return the WeaponBuilder
     */
    public WeaponBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }


    /**
     * Sets the type of weapon. If no type was assigned, then a random type will be assigned.
     * @param _type weapon type (sword, club, spear)
     * @return the WeaponBuilder
     */
    public WeaponBuilder type(WeaponType _type){
        if (_type == null){
            this._type = WeaponType.values()[rand.nextInt(WeaponType.values().length-1)];
        } else {
            this._type = _type;
        }
        return this;
    }

    /**
     * Sets the imagePath of the weapon item. If no imagePath was assigned, then a random image will be
     * assigned according to the type of the weapon.
     * @param _imagePath is the main imagePath
     * @return the WeaponBuilder
     */
    public WeaponBuilder imagePath(String _imagePath) {
        if (_imagePath.equals("")) switch (_type) {
            case Sword:
                this._imagePath = swordImg[rand.nextInt(swordImg.length)];
                break;
            case Spear:
                this._imagePath = spearImg[rand.nextInt(spearImg.length)];
                break;
            case Club:
                this._imagePath = clubImg[rand.nextInt(clubImg.length)];
                break;
            case Misc:
                this._imagePath = "/assets/testAssets/Error-MissingImage.png";
                break;
        } else {
            this._imagePath = _imagePath;
        }
        return this;
    }

    /**
     * Sets the min and max affect of the item. If no values are assigned, then random values
     * will be assigned according to the minimum and maximum weapon values of the item.
     * @param _minDamage is the minimum affect value
     * @param _maxDamage is the maximum affect value
     * @return the WeaponBuilder
     */
    public WeaponBuilder minmaxDamage(int _minDamage, int _maxDamage) {
        if (_maxDamage == 0) {
            int num1 = getRandomNumber(minWeapon, maxWeapon);
            int num2 = getRandomNumber(minWeapon, maxWeapon);
            if(num1 == num2 && num1 > minWeapon) num1--;
            else if(num1 == num2 && num1 < maxWeapon) num2++;
            this._maxDamage = (num1 >= num2)? num1 : num2;
            this._minDamage = (num1 <= num2)? num1 : num2;
        } else {
            this._minDamage = _minDamage;
            this._maxDamage = _maxDamage;
        }
        return this;
    }

    /**
     * Sets the critical hit chance of the item. If no values are assigned, then random values
     * will be assigned according to the minimum and maximum weapon values of the item.
     * @param _critChance is the critical hit chance of the item
     * @return the WeaponBuilder
     */
    public WeaponBuilder critChance(int _critChance) {
        if (_critChance == 0){
            this._critChance = getRandomNumber(minWeapon, maxWeapon);
        } else {
            this._critChance = _critChance;
        }
        return this;
    }

    /**
     * Determines the quality of the item by dividing the range of the min and max damage values
     * by 3 and assigning a string descriptor to the category the damage value falls within. This attribute
     * cannot be assigned a string value (it is private); it can only be determined within this function.
     * @param _quality is the quality of the items (good, better, best)
     */
    private void quality(String _quality){
        if (_quality.equals("")){
            int partition = (int) (Math.ceil(maxWeapon +1 - minWeapon) / 3);  //Three possible qualities: good, better, best
            if (_maxDamage < (minWeapon + partition)) this._quality = "good";
            else if (_maxDamage > (maxWeapon - partition)) this._quality = "best";
            else this._quality = "better";
        } else {
            this._quality = _quality;
        }
    }

    /**
     * Sets the value of the item. If no value was assigned, then a random value will be
     * assigned according to the minimum and maximum damage of the armor item.
     * @param _value is the value of the item
     * @return the WeaponBuilder
     */
    public WeaponBuilder value(int _value) {
        if (_value == 0) {
            this._value = getRandomNumber(_minDamage, _maxDamage) * 2;
        } else {
            this._value = _value;
        }
        return this;
    }

    /**
     * Sets the name of the item. If no name was assigned, then a random name will be
     * assigned according to the type and quality of the item.
     * @param _name is the item's name
     * @return the WeaponBuilder
     */
    public WeaponBuilder name(String _name) {
        if (_name.equals("")){
            this._name = assistant.getWeaponName(_type, _quality);
        } else {
            this._name = _name;
        }
        return this;
    }

    /**
     * Sets the description of the consumable item. If no description was assigned, then a random description will be
     * assigned according to the type, quality, and name of the item.
     * @param _description is the consumable's description
     */
    private void description(String _description){
        if (_description.equals("")){
            this._description = assistant.getWeaponDescription(_quality, _type, _name);
        } else {
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
