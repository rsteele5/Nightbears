package gameobject.renderable.enemy;

public abstract class MinionState {
    String str_State;

    @Override
    public abstract String toString();
    public abstract String getState();
    public abstract void doAction(Minion minion);
}
