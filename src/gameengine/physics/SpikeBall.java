package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpikeBall extends CollidableRenderable implements Interactable {
    public SpikeBall(int x, int y) {
        super(x, y,"/assets/sidescroll/Spike_Trap.png", DrawLayer.Entity, 1, true);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(0,0,400,400);
    }

    @Override
    public boolean action(GameObject g) {
        Debug.log(true,"HERE");
        return false;
    }
    int t = Math.random() < .5d ? -1 : 1;
    float i = (float) (Math.random() * 60 * t);
    int pSign = 0;
    int sign = 1;
    final float xPos = 400;
    final float yPos = 400;
    float a1 = .01f;
    final float a2 = .025f;
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
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,g,
                new Rectangle2D.Double(x, y, width, height));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(Math.toRadians(i),xPos + x,yPos + y);
        g2.drawImage(image, x, y, null);
        g2.dispose();
    }
    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
