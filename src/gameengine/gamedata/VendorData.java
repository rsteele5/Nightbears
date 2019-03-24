package gameengine.gamedata;

import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.ItemMeta;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.weapon.WeaponBuilder;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class VendorData implements Serializable {
    //region <Variables>
    private CopyOnWriteArrayList<Item> vendorInventory = new CopyOnWriteArrayList<Item>();
    //endregion

    VendorData(){
        initializeInventory();
    }

    /**
     * Get the list of inventory items
     * @return inventory array
     */
    public CopyOnWriteArrayList<Item> getInventory() {return vendorInventory;}

    /**
     * Add an item to the inventory
     * @param item is the item to be added
     */
    public void addItem(Item item) {vendorInventory.add(item);}

    /**
     * Remove an item from the inventory
     * @param item is the item to be removed
     */
    public void removeItem(Item item) { vendorInventory.remove(item);}

    /**
     * Replaces the entire inventory list
     * @param updatedItems is the array list that will replace the original
     */
    public void replaceList(CopyOnWriteArrayList<Item> updatedItems) {this.vendorInventory = updatedItems;}

    /**
     * Initial inventory list at the game beginning
     */
    private void initializeInventory() {
        for (int i = 0; i < 8; i++){
            addItem(new ArmorBuilder()
                    .buildArmor()
            );
            addItem(new WeaponBuilder()
                    .buildWeapon()
            );
            addItem(new ConsumableBuilder()
                    .buildConsumable()
            );
        }

        if (vendorInventory.size() > 0) {
            vendorInventory.sort(new ItemComparator());
        }
    }

    /**
     * Vendor's inventory items are renewed and increase in value with each restock.
     */
    public void restockItems(){
        //Increase min/max values for all items
        ItemMeta.attributeAmplifier();

        //Create a new inventory array list
        CopyOnWriteArrayList<Item> restock = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 8; i++){
            restock.add(new ArmorBuilder()
                    .buildArmor()
            );
            restock.add(new WeaponBuilder()
                    .buildWeapon()
            );
            restock.add(new ConsumableBuilder()
                    .buildConsumable()
            );
        }

        //Sort list
        if (restock.size() > 0) {
            restock.sort(new ItemComparator());
        }

        //Replace old list with new list
        replaceList(restock);
    }
}
