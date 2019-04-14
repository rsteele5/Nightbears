package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class SpikeBall extends CollidableRenderable {
    public SpikeBall(int x, int y) {
        super(x, y,"/assets/sidescroll/Spike_Trap.png", DrawLayer.Entity, 1, true);
    }


    @Override
    public void update() {

    }

    @Override
    public boolean triggered(GameObject gameObject) {
        //Debug.log(true,"HERE");
        if(gameObject instanceof Player){
            ((Player)gameObject).hit(3);
        }
        return true;
    }
    Random generator = new Random();
    int t = generator.nextDouble() < .5d ? -1 : 1;
    float i = 0;//(float) (generator.nextDouble()  * 60 * t);

    int pX = 0;
    int pY = 0;
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x + pX + 319,y + pY + 600,161,157);
    }



    int pSign = 0;
    int sign = 1;
    final float xPos = 400;
    final float yPos = 400;
    float a1 = .01f;
    final float a2 = .025f;

    float acc = 0;
    @Override
    public void draw(Graphics2D g){
        if(Math.abs(i) > 35 && pSign != sign) {
            pSign  = sign;
            sign = i < 0 ? 1 : -1;
        }
        else {
            a1 += a2 * sign;
            a1 = Math.abs(a1) > 1f ?  1f * sign: a1;
            pSign = 0;
        }

        i += round(a1,3);
        /*
        double i1 = Math.tan(Math.toRadians(i/2)) * 220;
        double length = 2 * i1;
        double theta = 90 - ((180 - i)/2);
        double y1 = Math.sin(Math.toRadians(theta))* length;
        double x1 = y1/Math.tan(Math.toRadians(theta));
        */
       // double i1 = Math.tan(Math.toRadians(i/2)) * 220;
       // double length = (2 * (Math.tan(Math.toRadians(i/2)) * 220));
       // double theta = (90 - ((180 - i)/2));
        //double y1 = Math.sin(Math.toRadians((90 - ((180 - i)/2))))* (2 * (Math.tan(Math.toRadians(i/2)) * 220));
        //double x1 = (Math.sin(Math.toRadians((90 - ((180 - i)/2))))* (2 * (Math.tan(Math.toRadians(i/2)) * 220)))/Math.tan(Math.toRadians((90 - ((180 - i)/2))));
        acc += a1;

        pX = (int) -((Math.sin(Math.toRadians((90 - ((180 - i)/2))))* (2 * (Math.tan(Math.toRadians(i/2)) * 220)))/Math.tan(Math.toRadians((90 - ((180 - i)/2)))));
        pY = (int) -(Math.sin(Math.toRadians((90 - ((180 - i)/2))))* (2 * (Math.tan(Math.toRadians(i/2)) * 220)));

        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(Math.toRadians(i),xPos + x,yPos + y);

        g2.drawImage(image, x, y, null);
        Debug.drawRect(true,g,getCollisionBox());
        g2.dispose();
    }
    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
