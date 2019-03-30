package gameengine.gamedata;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import main.utilities.Debug;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerData implements Serializable {

    private CopyOnWriteArrayList<Item> playerInventory = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Item> playerEquipment = new CopyOnWriteArrayList<>();
    private String imageDirectory;
    private int gold;

    public PlayerData(){
        initializeInventory();
        initializeEquipment();
        gold = 100;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }


    public CopyOnWriteArrayList<Item> getInventory(){
        return playerInventory;
    }

    public void addItem(Item item){
        playerInventory.add(item);
    }

    public void removeItem(Item item){
        playerInventory.remove(item);
    }

    public CopyOnWriteArrayList<Item> getPlayerEquipment() {
        return playerEquipment;
    }

    public void equipItem(Item equip, int type) {
        if(playerEquipment.get(type) != null) {
            playerInventory.add(playerEquipment.get(type));
        }
        if(playerInventory.contains(equip)) playerInventory.remove(equip);
        playerEquipment.set(type,equip);
    }
    public void unequipItem(Item remove, int type) {
        playerEquipment.set(type,null);
        playerInventory.add(remove);
    }

    public void replaceList(CopyOnWriteArrayList<Item> updatedItems) {
        this.playerInventory = updatedItems;
    }

    public void initializeInventory(){
        addItem(new WeaponBuilder()
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword")
                .type(WeaponType.Sword)
                .value(10)
                .minmaxDamage(5, 7)
                .critChance(3)
                .buildWeapon());

        addItem(new ArmorBuilder()
                .imagePath("/assets/Items/helmet1.png")
                .name("My Fwirst Helmet")
                .type(ArmorType.Head)
                .value(12)
                .armorPoints(10)
                .buildArmor());

        addItem(new ConsumableBuilder()
                .imagePath("/assets/Items/bluepotion.png")
                .name("Blew Potion")
                .value(12)
                .type(ConsumableType.edible)
                .buildConsumable());

        addItem(new ArmorBuilder()
                .imagePath("/assets/Items/chest1.png")
                .name("My foist chesty")
                .type(ArmorType.Chest)
                .value(24)
                .armorPoints(16)
                .buildArmor());

        addItem(new ArmorBuilder()
                .imagePath("/assets/Items/pants1.png")
                .name("My cool pants")
                .type(ArmorType.Legs)
                .value(7)
                .armorPoints(5)
                .buildArmor());

        if (playerInventory.size() > 0) {
            playerInventory.sort(new ItemComparator());
        }
    }
    public void initializeEquipment() {
        for(int x=0; x < 6; x++) {
            playerEquipment.add(null);
        }

        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/helmet2.png")
                .name("My Swecond Helmet")
                .type(ArmorType.Head)
                .value(26)
                .armorPoints(18)
                .buildArmor(), ArmorType.Head.ordinal());

        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/helmet3.png")
                .name("My Thord Helmet")
                .type(ArmorType.Head)
                .value(53)
                .armorPoints(30)
                .buildArmor(), ArmorType.Head.ordinal());

        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/cape1.png")
                .name("My capeeee")
                .type(ArmorType.OffHand)
                .value(16)
                .armorPoints(5)
                .buildArmor(), ArmorType.OffHand.ordinal());


        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/feet1.png")
                .name("My bootsies")
                .type(ArmorType.Feet)
                .value(13)
                .armorPoints(3)
                .buildArmor(), ArmorType.Feet.ordinal());
    }

    public int getGold() {
        return gold;
    }

    public void changeGold(int amt) {
        gold += amt;
    }
}
