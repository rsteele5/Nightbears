package gameengine.gamedata;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerData implements Serializable {

    private CopyOnWriteArrayList<Item> playerInventory = new CopyOnWriteArrayList<>();

    public PlayerData(){
        initializeInventory();
    }

    public PlayerData getPlayerData() { return this; }


    public CopyOnWriteArrayList<Item> getInventory(){
        return playerInventory;
    }

    /*public void setPlayerData(PlayerData playerData){
        this.playerInventory = playerData.playerInventory;
    }*/



    public void addItem(Item item){
        playerInventory.add(item);
    }

    public void removeItem(Item item){
        playerInventory.remove(item);
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

        if (playerInventory.size() > 0) {
            playerInventory.sort(new ItemComparator());
        }
    }

}
