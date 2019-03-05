package gameobject.renderable.button;

import gameobject.renderable.DrawLayer;
import main.utilities.Action;

import java.awt.*;

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

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        super.draw(graphics);
        graphics.setFont(font);
        //graphics.setColor(color);
        int fontHeight = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(text);
        int fontAscent = graphics.getFontMetrics().getAscent();
        graphics.drawString(text, x+(image.getWidth()/2)-(width/2), y+fontHeight);
    }

    public void setText(String text) {
        this.text = text;
    }
}
