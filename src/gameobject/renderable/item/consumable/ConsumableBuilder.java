package gameobject.renderable.item.consumable;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.AffectType;
import gameobject.renderable.item.DescriptionAssistant;

import java.util.Random;

public class ConsumableBuilder {
    //Static/global variable to control max consumable points to use with random function
    static int maxConsumable = 15;
    static int minConsumable = 8;

    // Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private DrawLayer _layer = DrawLayer.Entity;
    private String [] potionImg = {"/assets/Items/bluepotion.png", "/assets/Items/redPotion.png", "/assets/Items/yellowpotion.png"};
    private Random rand = new Random();
    private String _imagePath = "";

    // Consumable requirements
    private ConsumableType _type;
    private AffectType _affect;
    private int _maxAffect = 0;
    private int _minAffect = 0;
    private String _quality = "";
    private String _description = "";

    // Item requirements
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = "";
    private int _value = 0;

    public ConsumableBuilder() { }

    public Consumable buildConsumable() {
        if (_imagePath.equals("")){
            _imagePath = potionImg[rand.nextInt(potionImg.length)];
        }
        if (_type == null){
            _type = ConsumableType.values()[rand.nextInt(ConsumableType.values().length-1)];
        }
        if (_affect == null){
            _affect = setAffect();
        }
        if (_maxAffect == 0){
            int num1 = getRandomNumber(minConsumable, maxConsumable);
            int num2 = getRandomNumber(minConsumable, maxConsumable);
            _maxAffect = (num1 >= num2)? num1 : num2;
            _minAffect = (num1 <= num2)? num1 : num2;
        }
        if (_quality.equals("")){
            _quality = determineQuality();
        }
        if (_name.equals("")){
            _name = assistant.getConsumableName(_type, _quality);
        }
        if (_description.equals("")){
            _description = assistant.getConsumableDescription(_type, _affect, _quality, _name);
        }
        if (_value == 0){
            _value = getRandomNumber(_minAffect, _maxAffect) * 2;
        }
        return new Consumable(_x, _y, _imagePath, _layer, _name, _value, _type, _affect, _maxAffect, _minAffect, _quality, _description);
    }

    public ConsumableBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    private String determineQuality(){
        int partition = (int) (Math.ceil(maxConsumable +1 - minConsumable) / 3);  //Three possible qualities: good, better, best
        if (_maxAffect < (minConsumable + partition)) return "good";
        else if (_maxAffect > (maxConsumable - partition)) return "best";
        else return "better";
    }

    public ConsumableBuilder imagePath(String _imagePath) {
        this._imagePath = _imagePath;
        return this;
    }

    public ConsumableBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    public ConsumableBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public ConsumableBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public ConsumableBuilder type(ConsumableType _type) {
        this._type = _type;
        return this;
    }

    public ConsumableBuilder affect(AffectType _affect) {
        this._affect = _affect;
        return this;
    }

    public ConsumableBuilder maxAffect(int _maxAffect) {
        this._maxAffect = _maxAffect;
        return this;
    }

    public ConsumableBuilder minAffect(int _minAffect) {
        this._minAffect = _minAffect;
        return this;
    }

    private AffectType setAffect(){
        AffectType temp = AffectType.healthBoost;
        if (_type == ConsumableType.throwable || _type == ConsumableType.ammunition){
            while (temp == AffectType.healthBoost || temp == AffectType.healthLevel){
                temp = AffectType.values()[rand.nextInt(AffectType.values().length-1)];
            }
        } else {
            temp = AffectType.values()[rand.nextInt(AffectType.values().length-1)];
        }
        return temp;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }
}
