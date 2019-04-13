package gameobject.renderable.item;


import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.WeaponType;

import java.util.Random;


public class DescriptionAssistant {

    //region <Variables>
    private Random rand = new Random();
    private String[] good = {"Fine", "Great", "Good", "Basic", "Standard"};
    private String[] better = {"Choice", "Finer", "Greater", "Better"};
    private String[] best = {"Ultimate", "Supreme","Finest", "Superior", "Greatest", "Best"};
    private String[] error = {"Quality Error", "Quality Error"};
    //endregion

    /**
     * Sets the weapon name according to the type and quality
     * @param type is the type of weapon (sword, club, spear, mystery)
     * @param quality is the quality of the weapon (good, better, best)
     * @return weapon name
     */
    public String getWeaponName(WeaponType type, String quality){
        String[] adj;
        switch (quality) {
            case "good":
                adj = good;
                break;
            case "better":
                adj = better;
                break;
            case "best":
                adj = best;
                break;
            default:
                adj = error;
                break;
        }

        String myAdj = adj[rand.nextInt(adj.length)];
        switch (type){
            case Sword:
                myAdj += " Sword";
                break;
            case Club:
                myAdj += " Club";
                break;
            case Spear:
                myAdj += " Spear";
                break;
            case Misc:
                myAdj += " Mystery";
                break;
        }
        return myAdj;
    }

    /**
     * Sets the armor name according to the type and quality
     * @param type is the type of armor (head, chest, leg, foot, magic)
     * @param quality is the quality of the armor (good, better, best)
     * @return armor name
     */
    public String getArmorName(ArmorType type, String quality){
        String[] adj;
        switch (quality) {
            case "good":
                adj = good;
                break;
            case "better":
                adj = better;
                break;
            case "best":
                adj = best;
                break;
            default:
                adj = error;
                break;
        }
        String name = adj[rand.nextInt(adj.length)];
        switch (type){
            case OffHand:
                name += " Cape";
                break;
            case Feet:
                name += " Boots";
                break;
            case Legs:
                name += " Pants";
                break;
            case Chest:
                name += " Chest Plate";
                break;
            default:
                name += " Helmet";
                break;
        }
        return name;
    }

    /**
     * Sets the consumable name according to the type and quality
     * @param cType is the type of consumable (spell, edible, throwable, ammunition)
     * @param quality is the quality of the armor (good, better, best)
     * @return consumable name
     */
    public String getConsumableName(ConsumableType cType, String quality){
        String[] adj;
        switch (quality) {
            case "good":
                adj = good;
                break;
            case "better":
                adj = better;
                break;
            case "best":
                adj = best;
                break;
            default:
                adj = error;
                break;
        }

        String myName = adj[rand.nextInt(adj.length)];
        switch (cType){
            case spell:
                myName += " Spell";
                break;
            case edible:
                myName += " Edible";
                break;
            case throwable:
                myName += " Throwable";
                break;
        }
        return myName;
    }

    /**
     * Sets the weapon description
     * @param quality is the weapon's quality
     * @param type is the weapon's type
     * @param name is the weapon's name
     * @return the weapon description
     */
    public String getWeaponDescription(String quality, WeaponType type, String name){
        String[] adj = {"formidable", "dreadful", "fierce", "menacing", "impressive",
                "robust", "hefty"};
        String myAdj = adj[rand.nextInt(adj.length)];
        String description;
        if (quality.equals("good")){
            description = "\nEh. This is a pretty standard " + type + ", but it looks " + myAdj + ".";
        } else if (quality.equals("better")){
            description = "\nThis " + type + " is quite " + myAdj + ".";
        } else {
            description = "\nThe " + name + " is one of my most " + myAdj + " weapons!";
        }
        return description;
    }

    /**
     * Sets the armor description
     * @param quality is the armor's quality
     * @param type is the armor's type
     * @param name is the armor's name
     * @return the armor description
     */
    public String getArmorDescription(ArmorType type, String quality, String name){
        String[] adj = {"insulates", "shields", "defends", "covers",
                "upholds", "repels danger"};
        String myAdj = adj[rand.nextInt(adj.length)];
        String description;
        if (quality.equals("good")){
            description = "\nEh. This is pretty standard " + type + " gear, but it looks like it " + myAdj + " well.";
        } else if (quality.equals("better")){
            description = "\nThis mid-level " + type + " gear " + myAdj + " reasonably well.";
        } else {
            description = "\nThe high-quality " + name + " " + myAdj + " very well!";
        }
        return description;

    }

    /**
     * Sets the consumable description
     * @param quality is the consumable's quality
     * @param type is the consumable's type
     * @param affect is the consumable's effect
     * @return the consumable description
     */
    public String getConsumableDescription(ConsumableType type, AffectType affect,
                                           String quality){
        String myAdj;
        String action = "";
        String maker1 = "";

        //First sentence sets the action. Maker1 is set according to the item's effect
        switch (affect){
            case fire:
                String[] list2 = {"blazing hot", "fiery hot", "hot lava", "ghost pepper", "3-alarm fire"};
                myAdj = list2[rand.nextInt(list2.length)];
                action = "Want to melt your enemies? This " + myAdj + " " + type + " might do the trick.";
                maker1 = "fire-manipulator";
                break;
            case enchant:
                String[] list3 = {"bewitching", "captivating", "hypnotic"};
                myAdj = list3[rand.nextInt(list3.length)];
                action = "Enchant an item with this " + myAdj + " " + type + " to make it more powerful.";
                maker1 = "witch";
                break;
            case healthBoost:
                String[] list5 = {"energize", "refresh", "invigorate", "exhilarate",
                        "renew"};
                myAdj = list5[rand.nextInt(list5.length)];
                action = "A fast-acting stimulant to " + myAdj + " yourself and recover your health.";
                maker1 = "nutritionist";
                break;
            case healthLevel:
                action = "This " + type + " is just what you need to improve your overall stamina.";
                maker1 = "nutritionist";
                break;
        }

        //Maker2 is set according to the item's type
        String maker2 = "";
        switch (type){
            case throwable:
                maker2 = "physicist";
                break;
            case edible:
                maker2 = "baker";
                break;
            case spell:
                maker2 = "sorcerer";
                break;
        }

        //Combine first sentence and create 2nd sentence which includes both makers.
        String description;
        if (quality.equals("good")){
            description = "\n" + action + " It was made by a novice " + maker1 + " and " + maker2 + ".";
        } else if (quality.equals("better")){
            description = "\n" + action + " It was made by a proficient " + maker1 + " and " + maker2 + ".";
        } else {
            description = "\n" + action + " It was made by a master " + maker1 + " and " + maker2 + ".";
        }
        return description;
    }
}
