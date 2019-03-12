package gameobject.renderable.item.weapon;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.DescriptionAssistant;

import java.util.Random;

public class WeaponBuilder {
    //Static/global variable to control max armor points to use with random function
    static int maxWeapon = 10;
    static int minWeapon = 5;

    //Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = DrawLayer.Entity;
    private String [] spearImg = {"/assets/Items/spear1.png", "/assets/Items/spear2.png", "/assets/Items/spear3.png"};
    private String [] swordImg = {"/assets/Items/sword1.png", "/assets/Items/sword2.png", "/assets/Items/sword3.png"};
    private String [] clubImg = {"/assets/Items/club1.png", "/assets/Items/club2.png", "/assets/Items/club3.png"};

    //Weapon requirements
    private Random rand = new Random();
    private WeaponType _type = WeaponType.values()[rand.nextInt(WeaponType.values().length-1)];
    private int num1 = getRandomNumber(minWeapon, maxWeapon);
    private int num2 = getRandomNumber(minWeapon, maxWeapon);
    private int _maxDamage = (num1 >= num2)? num1 : num2;
    private int _minDamage = (num1 <= num2)? num1 : num2;
    private int _critChance = getRandomNumber(minWeapon, maxWeapon);

    //Item requirements
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = assistant.getWeaponName(_type);
    private int _value = getRandomNumber(_minDamage, _maxDamage) * 2 ;


    public WeaponBuilder(){ }

    public Weapon buildWeapon(){
        if (_imagePath.equals("")) switch (_type) {
            case Sword:
                _imagePath = swordImg[rand.nextInt(swordImg.length)];
                break;
            case Spear:
                _imagePath = spearImg[rand.nextInt(spearImg.length)];
                break;
            case Club:
                _imagePath = clubImg[rand.nextInt(clubImg.length)];
                break;
            case Misc:
                _imagePath = "/assets/testAssets/Error-MissingImage.png";
                break;
        }
        return new Weapon(_x,_y,_imagePath,_layer,_name,_value,_type, _minDamage, _maxDamage, _critChance);
    }

    public WeaponBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    public WeaponBuilder imagePath(String _imagePath) {
        this._imagePath = _imagePath;
        return this;
    }

    public WeaponBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    public WeaponBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public WeaponBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public WeaponBuilder type(WeaponType _type) {
        this._type = _type;
        return this;
    }

    public WeaponBuilder maxDamage(int _maxDamage) {
        this._maxDamage = _maxDamage;
        return this;
    }

    public WeaponBuilder minDamage(int _minDamage) {
        this._minDamage = _minDamage;
        return this;
    }

    public WeaponBuilder critChance(int _critChance) {
        this._critChance = _critChance;
        return this;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

}
