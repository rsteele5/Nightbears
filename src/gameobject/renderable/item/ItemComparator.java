package gameobject.renderable.item;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.Item;

import java.util.Comparator;

// Method to sort item list
public class ItemComparator implements Comparator<RenderableObject> {

    @Override
    public int compare(RenderableObject o1, RenderableObject o2) {
        return ((Item)o1).getCategory().compareTo(((Item)o2).getCategory());
    }
}
