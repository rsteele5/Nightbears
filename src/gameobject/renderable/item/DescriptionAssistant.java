package gameobject.renderable.item;


import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.WeaponType;

import java.util.Random;


public class DescriptionAssistant {

    private Random rand = new Random();

    public String getWeaponName(WeaponType type){
        String[] adj = {"Formidable", "Dreadful", "Fierce", "Menacing", "Impressive", "Mighty", "Tremendous", "Great",
                "Robust", "Almighty", "Supreme", "Hefty"};
        String myAdj = adj[rand.nextInt(adj.length)];
        switch (type){
            case Sword:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Sword";
                break;
            case Club:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Club";
                break;
            case Spear:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Spear";
                break;
            case Misc:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Mystery";
                break;
        }
        return myAdj;
    }

    public String getArmorName(ArmorType type){
        String[] adj = {"Vigilant", "Insulating", "Shielding", "Impenetrable", "Impervious", "Hermetic", "Waterproof",
                "Indomitable"};
        String myAdj = adj[rand.nextInt(adj.length)];
        switch (type){
            case Cape:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Cape";
                break;
            case Feet:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Boots";
                break;
            case Pants:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Pants";
                break;
            case Chest:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Chest Plate";
                break;
            case Head:
                myAdj = "The " + adj[rand.nextInt(adj.length)] + " Helmet";
                break;
        }

        return myAdj;
    }

    public String getConsumableName(ConsumableType cType, AffectType aType){
        String myAdj = "";
        switch (aType){
            case random:
                String[] list1 = {"Random", "Strange", "Dubious", "Shady", "Unclear", "Dubitable"};
                myAdj = list1[rand.nextInt(list1.length)];
                break;
            case fire:
                String[] list2 = {"Blazing Hot", "Fiery Hot", "Lava Hot", "Ghost Pepper", "3-Alarm Fire"};
                myAdj = list2[rand.nextInt(list2.length)];
                break;
            case enchant:
                String[] list3 = {"Enchanting", "Bewitching", "Captivating", "Magnetizing", "Hypnotizing"};
                myAdj = list3[rand.nextInt(list3.length)];
                break;
            case puncture:
                String[] list4 = {"Piercing", "Stabbing", "Lacerating", "Penetrating", "Permeating", "Impaling",
                        "Infiltrating", "Jabbing"};
                myAdj = list4[rand.nextInt(list4.length)];
                break;
            case healthBoost:
                String[] list5 = {"Energizing", "Refreshing", "Invigorating", "Reinvigorating", "Exhilarating",
                        "Renewing", "Stimulating"};
                myAdj = list5[rand.nextInt(list5.length)];
                break;
            case healthLevel:
                myAdj = "Stamina Raising";
                break;
        }

        String myName = "";
        switch (cType){
            case misc:
                myName = myAdj + "Mystery Consumable";
                break;
            case spell:
                myName = myAdj + " Spell";
                break;
            case edible:
                myName = myAdj + " Edible";
                break;
            case throwable:
                myName = myAdj + " Throwable";
                break;
            case ammunition:
                myName = myAdj + " Ammunition";
                break;
        }

        return myName;
    }

    public String getWeaponDescription(){

        return null;
    }

    public String getArmorDescription(){
        return null;
    }

    public String getConsumableDescription(){
        return null;
    }


}
