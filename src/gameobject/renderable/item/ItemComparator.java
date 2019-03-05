package gameobject.renderable.item;

import gameobject.renderable.item.Item;

import java.util.Comparator;

// Method to sort item list
public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
}
