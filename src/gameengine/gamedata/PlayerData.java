package gameengine.gamedata;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.armor.Armor;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import main.utilities.Debug;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerData implements Serializable {

    private CopyOnWriteArrayList<Item> playerInventory = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Item> playerEquipment = new CopyOnWriteArrayList<>();
    private String imageDirectory;
    private String name;
    private int gold;
    private int maxHealth;  // Must be divisible by 2
    private int currentHealth;
    private int currentArmor;

    private LocalDate creationDate;
    private LocalDate deathDate;
    private LocalDate victoryDate;

    public PlayerData(){
        initializeInventory();
        initializeEquipment();
        gold = 100;
        maxHealth = 6;
        currentHealth = 6;
        resetCurrentArmor();
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
        resetCurrentArmor();
    }
    public void unequipItem(Item remove, int type) {
        playerEquipment.set(type,null);
        playerInventory.add(remove);
        resetCurrentArmor();
    }

    public int getWeaponDamage(){
        int minDamage;
        int maxDamage;
        int critchance;
        if(playerEquipment.get(3) != null){ // If the player has a weapon equipped get the min/max damage;
            minDamage = ((Weapon)playerEquipment.get(3)).getMinDamage();
            maxDamage = ((Weapon)playerEquipment.get(3)).getMaxDamage();
            critchance = ((Weapon)playerEquipment.get(3)).getCritChance();
        } else { // If the player has no weapon equipped set the min/max damage to 1/4;
            minDamage = 1;
            maxDamage = 4;
            critchance = 0;

        }
        int crit = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        if(crit < critchance) {
            Debug.log(true, "BOOOOOOOOOOOOOOM : " + crit);
            return ThreadLocalRandom.current().nextInt(minDamage*3, (maxDamage + 1)*3);
        }
        return ThreadLocalRandom.current().nextInt(minDamage, maxDamage + 1);
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

        addItem(new WeaponBuilder()
                .imagePath("/assets/Items/spear1.png")
                .name("My Fwirst Spear")
                .type(WeaponType.Spear)
                .value(10)
                .minmaxDamage(5, 7)
                .critChance(3)
                .buildWeapon());

        addItem(new WeaponBuilder()
                .imagePath("/assets/Items/club1.png")
                .name("My Fwirst Club")
                .type(WeaponType.Club)
                .value(10)
                .minmaxDamage(5, 7)
                .critChance(3)
                .buildWeapon());

        addItem(new ArmorBuilder()
                .imagePath("/assets/Items/helmet1.png")
                .name("My Fwirst Helmet")
                .type(ArmorType.Head)
                .value(10)
                .armorPoints(2)
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
                .value(16)
                .armorPoints(4)
                .buildArmor());

        addItem(new ArmorBuilder()
                .imagePath("/assets/Items/pants1.png")
                .name("My cool pants")
                .type(ArmorType.Legs)
                .value(5)
                .armorPoints(3)
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
                .value(18)
                .armorPoints(4)
                .buildArmor(), ArmorType.Head.ordinal());

        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/helmet3.png")
                .name("My Thord Helmet")
                .type(ArmorType.Head)
                .value(53)
                .armorPoints(6)
                .buildArmor(), ArmorType.Head.ordinal());

        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/cape1.png")
                .name("My capeeee")
                .type(ArmorType.OffHand)
                .value(16)
                .armorPoints(1)
                .buildArmor(), ArmorType.OffHand.ordinal());


        equipItem(new ArmorBuilder()
                .imagePath("/assets/Items/feet1.png")
                .name("My bootsies")
                .type(ArmorType.Feet)
                .value(13)
                .armorPoints(1)
                .buildArmor(), ArmorType.Feet.ordinal()+1);
    }

    public int getGold() {
        return gold;
    }

    public void modifyGold(int amt) {
        gold += amt;
    }

    public int getMaxHealth(){
        return maxHealth;
    }
    public void modifyMaxHealth(int amount){
        maxHealth += amount;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public void modifyCurrentHealth(int amount){
        currentHealth += amount;
    }

    public int getCurrentArmor() {
        return currentArmor;
    }

    public void modifyCurrentArmor(int amount){
        currentArmor += amount;
    }

    public void resetCurrentArmor() {
        currentArmor = 0;
        playerEquipment.forEach(item -> {
            if(item instanceof Armor){
                currentArmor += ((Armor)item).getArmorValue();
            }
        });
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public LocalDate getVictoryDate() {
        return victoryDate;
    }

    public void setVictoryDate(LocalDate victoryDate) {
        this.victoryDate = victoryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
