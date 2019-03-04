package gameobjects.renderables.items;

import gamescreens.DrawLayer;

public class WeaponBuilder {

    //Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = DrawLayer.Entity;
    //Item requirements
    private String _name = "[Insert Weapon Name]";
    private int _value = -1;
    //Weapon requirements
    private WeaponType _type = WeaponType.Misc;
    private int _maxDamage = 0;
    private int _minDamage = 0;
    private int _critChance = 0;

    public WeaponBuilder(){ }

    public Weapon buildWeapon(){
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

}
