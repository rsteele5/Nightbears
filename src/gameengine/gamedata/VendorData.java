package gameengine.gamedata;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.weapon.WeaponBuilder;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class VendorData implements Serializable {
    private CopyOnWriteArrayList<Item> vendorInventory = new CopyOnWriteArrayList<Item>();


    public VendorData(){
        initializeInventory();
    }

    public VendorData getVendorData() {return this;}

    public CopyOnWriteArrayList<Item> getInventory() {return vendorInventory;}

    public void addItem(Item item) {vendorInventory.add(item);}

    public void removeItem(Item item) { vendorInventory.remove(item);}

    public void replaceList(CopyOnWriteArrayList<Item> updatedItems) {this.vendorInventory = updatedItems;}

    public void initializeInventory() {
        for (int i = 0; i < 8; i++){
            vendorInventory.add(new ArmorBuilder()
                    .buildArmor()
            );
            vendorInventory.add(new WeaponBuilder()
                    .buildWeapon()
            );
            vendorInventory.add(new ConsumableBuilder()
                    .buildConsumable()
            );
        }

        if (vendorInventory.size() > 0) {
            vendorInventory.sort(new ItemComparator());
        }
    }

    public CopyOnWriteArrayList<Item> restockItems(){
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

        if (restock.size() > 0) {
            restock.sort(new ItemComparator());
        }
        return restock;
    }
}
