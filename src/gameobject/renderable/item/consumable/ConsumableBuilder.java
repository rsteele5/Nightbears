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
    private String _imagePath = potionImg[rand.nextInt(potionImg.length)];;

    // Consumable requirements

    private ConsumableType _type = ConsumableType.values()[rand.nextInt(ConsumableType.values().length-1)];
    private AffectType _affect = setAffect();
    private int num1 = getRandomNumber(minConsumable, maxConsumable);
    private int num2 = getRandomNumber(minConsumable, maxConsumable);
    private int _maxAffect = (num1 >= num2)? num1 : num2;
    private int _minAffect = (num1 <= num2)? num1 : num2;

    // Item requirements
    private DescriptionAssistant assistant = new DescriptionAssistant();
    private String _name = assistant.getConsumableName(_type, _affect);
    private int _value = getRandomNumber(_minAffect, _maxAffect) * 2 ;

    public ConsumableBuilder() { }

    public Consumable buildConsumable() {
        return new Consumable(_x, _y, _imagePath, _layer, _name, _value, _type, _affect, _maxAffect, _minAffect);
    }

    public ConsumableBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
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
