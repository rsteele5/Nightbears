package gameobject.renderable.item.consumable;

public enum ConsumableType {
    edible,
    throwable,
    spell,
    ammunition,
    misc        //TODO: consider removing. Requires updating type() method in ConsumableBuilder
}
