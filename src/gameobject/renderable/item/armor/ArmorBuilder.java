package gameobject.renderable.item.armor;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.DescriptionAssistant;

import java.util.Random;
import static gameobject.renderable.DrawLayer.Entity;

public class ArmorBuilder {
    //Static/global variable to control max armor points to use with random function
    static int maxArmor = 10;
    static int minArmor = 5;

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
    private Random rand = new Random();
    private ArmorType _type;
    private int _armor = 0;
    private String _quality = "";

    //Item requirements (defaults)
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = "";
    private String _description = "";
    private int _value = 0;


    //TODO check on mass with Austin
    //private int mass;

    public ArmorBuilder(){}

    public Armor buildArmor(){
        if (_type == null){
            _type = ArmorType.values()[rand.nextInt(ArmorType.values().length)];
        }
        if (_imagePath.equals("")){
            switch (_type) {
                case Head:
                    _imagePath = helmetImg[rand.nextInt(helmetImg.length)];
                    break;
                case Chest:
                    _imagePath = chestImg[rand.nextInt(chestImg.length)];
                    break;
                case Leg:
                    _imagePath = pantImg[rand.nextInt(pantImg.length)];
                    break;
                case Foot:
                    _imagePath = feetImg[rand.nextInt(feetImg.length)];
                    break;
                default:
                    _imagePath = offhandImg[rand.nextInt(offhandImg.length)];
                    break;
            }
        }
        if (_armor == 0){
            _armor =  getRandomNumber(minArmor, maxArmor);
        }
        if (_quality.equals("")){
            _quality = determineQuality();
        }
        if (_name.equals("")){
            _name = assistant.getArmorName(_type, _quality);
        }
        if (_value == 0){
            _value = getRandomNumber(minArmor, _armor) * 2;
        }
        if (_description.equals("")){
            _description = assistant.getArmorDescription(_type, _quality, _name);
        }
        return new Armor(_x, _y, _imagePath, _layer, _name, _value, _type, _armor, _quality, _description);
    }

    public ArmorBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    private String determineQuality(){
        int partition = (int) (Math.ceil(maxArmor +1 - minArmor) / 3);  //Three possible qualities: good, better, best
        if (_armor < (minArmor + partition)) return "good";
        else if (_armor > (maxArmor - partition)) return "best";
        else return "better";
    }

    public ArmorBuilder imagePath(String _imagePath) {
        this._imagePath = _imagePath;
        return this;
    }

    public ArmorBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    public ArmorBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public ArmorBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public ArmorBuilder type(ArmorType _type) {
        this._type = _type;
        return this;
    }

    public ArmorBuilder armorPoints(int _armor) {
        this._armor = _armor;
        return this;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

}
