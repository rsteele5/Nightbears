package gameobject.renderable.button;

import gameobject.renderable.DrawLayer;
import main.utilities.Action;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ButtonText extends Button{

    private Font font;
    private String text;
    private Color color;

    public ButtonText(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, imagePath, drawLayer, null);
        text = ""; color = Color.WHITE;
    }

    public ButtonText(int x, int y, String imagepath, DrawLayer drawLayer, Font font, Color color) {
        this(x, y, imagepath, drawLayer);
        this.font = font;
        this.color = color;
    }

    public ButtonText(int x, int y, String imagepath, DrawLayer drawLayer, Font font, Color color, String text) {
        this(x, y, imagepath, drawLayer,font,color);
        this.text = text;
    }

    public ButtonText(int x, int y, String imagepath, DrawLayer drawLayer, Font font, Color color, String text,  Action handleOnClick) {
        this(x, y, imagepath, drawLayer,font,color, text);
        onClick = handleOnClick;
    }
    public ButtonText(int x, int y, String imagePath, String pressedImagePath, DrawLayer drawLayer, Font font, Color color) {
        this(x, y, imagePath, drawLayer, font,color);
        this.pressedImagePath = pressedImagePath;
    }

    public ButtonText(int x, int y, String imagePath, String pressedImagePath, DrawLayer drawLayer, Font font, Color color, String text, Action handleOnClick) {
        this(x, y, imagePath, drawLayer, font,color, text);
        onClick = handleOnClick;
        this.pressedImagePath = pressedImagePath;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setFont(font);
        int textWidth = graphics.getFontMetrics().stringWidth(text);
        int fontHeight = graphics.getFontMetrics().getHeight();
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,graphics,
                new Rectangle2D.Double(position.x, position.y, width, height));
        if(isPressed) {
            graphics.drawImage(pressedImage, position.x - 10  , position.y - 10, width + 20, height + 20, null);
            graphics.setColor(Color.YELLOW);
            graphics.drawString(text, position.x+(image.getWidth()/2)-(textWidth/2), position.y+fontHeight);
            graphics.setColor(Color.WHITE);
        } else {
            graphics.drawImage(image, position.x , position.y, width, height, null);
            graphics.drawString(text, position.x+(image.getWidth()/2)-(textWidth/2), position.y+fontHeight);
        }

        //graphics.setColor(color);


    }

    @Override
    public void scale(float scaleFactor) {
        super.scale(scaleFactor);
        this.font = font.deriveFont((font.getSize() * scaleFactor));
    }

    public void setText(String text) {
        this.text = text;
    }
}
