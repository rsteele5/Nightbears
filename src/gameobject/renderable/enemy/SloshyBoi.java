package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameobject.Boundary;
import gameobject.TriggerableBoundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.Player;
import gamescreen.gameplay.GamePlayScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;

public class SloshyBoi extends Boss {

    EnemyState pstate;
    int doIJump = 0, shouldITurn = 0,shouldIComeDown=0, doIShit=0, poopyAmount, poopyCount=0;
    private ArrayList<Debris> poopings;

    public SloshyBoi(int x, int y, DrawLayer drawLayer, float speed, int hp) {
        super(x, y, "/assets/enemies/bosses/sloshyboi/sloshyboi.png", drawLayer, speed, hp);
        setState(new WalkLeft());
        poopings = new ArrayList<>();
        poopings.add(new Debris(x+100,y+100,DrawLayer.Entity,0,0));
        poopings.add(new Debris(x+100,y+100,DrawLayer.Entity,0,1));
        poopings.add(new Debris(x+100,y+100,DrawLayer.Entity,1,2));
        poopyAmount = poopings.size();
    }
    @Override
    public void changeState()
    {
        Debug.log(true,"I am in state: " + state.toString());
        Debug.log(true,"Do I jump: " + doIJump);
        if(doIJump > 5){
            doIJump = 0;
            pstate = state;
            state = new Jump();
        }
        switch (state.getState()){
            case "Walk Left" :
                shouldITurn++;
                if(shouldITurn>10){state = new WalkRight();shouldITurn=0;doIJump++;}
                break;
            case "Walk Right" :
                shouldITurn++;
                if(shouldITurn>10){state = new WalkLeft();shouldITurn=0;doIJump++;}
                break;
            case "Jump" :
                switch (pstate.getState()) {
                    case "Walk Left" :
                        state = new RainL();
                        break;
                    case "Walk Right" :
                        state = new RainR();
                        break;
                    default: Debug.error(true,"bad news bears");
                }
                break;
            case "Rain Right" :
                //state = pstate;
                shouldITurn++;
                if(shouldITurn>10){state = new RainL();shouldITurn=0;}
                if(shouldIComeDown > 399){state=pstate; shouldIComeDown=0;}
                break;
            case "Rain Left" :
                shouldITurn++;
                if(shouldITurn>10){state = new RainR();shouldITurn=0;}
                if(shouldIComeDown > 399){state=pstate; shouldIComeDown=0;}
                break;

        }
    }

    @Override
    public void update() {
        super.update();
        for(Debris debris:poopings){
            if(debris.getState().toString() == "Following") {
                debris.setX(x + 100);
                debris.setY(y + 100);
            }else {
                //do nothing
            }
        }
    }


    @Override
    public void draw(Graphics2D graphics) {
        if(!(state instanceof WalkerHidden)){
            super.draw(graphics);
        }
        Debug.drawRect(DebugEnabler.DRAWING_ACTIVE, graphics, new Rectangle(x, y, width, height));
    }

    @Override
    public boolean collide(Collidable c2) {
        if((c2 instanceof Boundary) || c2 instanceof TriggerableBoundary){
            if(c2.getCollisionBox().getWidth() < 200) {
                changeState();//Wall
            } else {
                if(state instanceof Jump|| state instanceof RainL || state instanceof  RainR) {shouldIComeDown++;if(shouldIComeDown > 399)changeState();}
            }
        } else if (c2 instanceof Player && !((Player) c2).isAttacking())
        {
            changeState();
        }


        if(c2 instanceof Player) {
            if(((Player) c2).isAttacking()){
                shouldITurn = 11;
                addhp(-(((Player) c2).getWeaponDamage()));
                if(getHp() <= 0){
                    killSelf();
                    for (Debris poop : poopings) {
                        poop.killSelf();
                    }
                    ((Player) c2).modifyCoins(50);
                    ((Player) c2).addItem(new WeaponBuilder()
                            .name("Fleshy Spear")
                            .type(WeaponType.Spear)
                            .imagePath("/assets/Items/spear4.png")
                            .value(100)
                            .minmaxDamage(15, 19)
                            .critChance(15)
                            .buildWeapon());
                }
                Debug.log(true, "MY HEALTH: " + hp);

                changeState();
            }
        }
        return true;
    }

    @Override
    protected void attack(){
        doIShit++;
        if(doIShit > 120) {
            if(poopyCount == poopyAmount)
            {
                poopyCount = 0;//todo fix
            }else {
                poopings.get(poopyCount).state = new Falling();
                poopyCount++;
            }
            doIShit=0;
        }
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        for(Debris debris: poopings){
            debris.addToScreen(screen,true);
            screen.kinematics.add(debris);
        }
        super.addToScreen(screen, isActive);
        screen.kinematics.remove(this);
        if(isActive) {
            screen.kinematics.add(this);
        }

    }
}
