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
    static int maxWeapon = (int)(ItemMeta.maxWeapon * ItemMeta.amplifier);
    static int minWeapon = (int)(ItemMeta.minWeapon * ItemMeta.amplifier);

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
    private String quality = "";

    //Item requirements
    /** DescriptionAssistant is utilized to generate the name and description for each item **/
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = "";
    private String _description = "";
    private int _value = 0;
    //endregion

    public Weapon buildWeapon(){
        type(this._type);
        imagePath(this._imagePath);
        minmaxDamage(this._minDamage, this._maxDamage);

        if (_critChance == 0){
            _critChance = getRandomNumber(minWeapon, maxWeapon);
        }
        if (quality.equals("")){
            quality = determineQuality(); //Good, Better, Best
        }
        if (_value == 0) {
            _value = getRandomNumber(_minDamage, _maxDamage) * 2;
        }
        if (_name == ""){
            _name = assistant.getWeaponName(_type, quality);
        }
        if (_description == ""){
            _description = assistant.getWeaponDescription(quality, _type, _name);
        }
        return new Weapon(_x,_y,_imagePath,_layer,_name,_value,_type, _minDamage, _maxDamage, _critChance, quality, _description);
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
     * will be assigned according to the minimum and maximum affect of consumable items.
     * @param _minDamage is the minimum affect value
     * @param _maxDamage is the maximum affect value
     */
    public WeaponBuilder minmaxDamage(int _minDamage, int _maxDamage) {
        if (_maxDamage == 0) {
            int num1 = getRandomNumber(minWeapon, maxWeapon);
            int num2 = getRandomNumber(minWeapon, maxWeapon);
            this._maxDamage = (num1 >= num2)? num1 : num2;
            this._minDamage = (num1 <= num2)? num1 : num2;
        } else {
            this._minDamage = _minDamage;
            this._maxDamage = _maxDamage;
        }
        return this;
    }


    public WeaponBuilder critChance(int _critChance) {
        this._critChance = _critChance;
        return this;
    }

    private String determineQuality(){
        int partition = (int) (Math.ceil(maxWeapon +1 - minWeapon) / 3);  //Three possible qualities: good, better, best
        if (_maxDamage < (minWeapon + partition)) return "good";
        else if (_maxDamage > (maxWeapon - partition)) return "best";
        else return "better";
    }

    public WeaponBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public WeaponBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public WeaponBuilder description(String _description){
        this._description = _description;
        return this;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

}
