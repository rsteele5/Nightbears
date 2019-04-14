package gameobject.renderable.enemy;

public abstract class EnemyState {
    String str_State;

    @Override
    public abstract String toString();
    public abstract String getState();
    public abstract void doAction(Enemy minion);
    public abstract boolean complete();
}
