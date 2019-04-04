package gameobject.renderable.text;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class TextBox extends RenderableObject {

    protected String text;
    protected String displayText;
    protected Font font;
    protected Color color;
    protected boolean centered;

    public TextBox(int x, int y, int width, int height, String text) {
        super(x,y);
        this.centered = false;
        this.width = width;
        this.height = height;
        this.text = text;
        this.drawLayer = DrawLayer.Effect;
        font = new Font("arial", Font.PLAIN, 12);
        color = Color.WHITE;
        displayText = "";
    }

    public TextBox(int x, int y, int width, int height, String text, Font font, Color color) {
        this(x,y,width,height, text);
        this.font = font;
        this.color = color;
    }

    public TextBox(int x, int y, int width, int height, String text, Font font, Color color, boolean centered) {
        this(x,y,width,height, text, font, color);
        this.centered = centered;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        Debug.drawRect(true, graphics, new Rectangle2D.Float(x, y, width, height));
        if(displayText.equals("")) parseString(graphics);
        graphics.setFont(font);
        graphics.setColor(color);
        int fontHeight = graphics.getFontMetrics().getHeight();
        int fontAscent = graphics.getFontMetrics().getAscent();
        int row = 0;
        String text = displayText;
        for (String line: text.split("\n")) {
            if(row < height){
                if(centered == true){
                    int stringWidth = graphics.getFontMetrics().stringWidth(line);
                    //Debug.drawRect(true, graphics, new Rectangle2D.Float(x + ((width-stringWidth)/2),y,stringWidth,height));
                    int xPos = x + ((width-stringWidth)/2);
                    //Debug.log(true, "Position - " + xPos);
                    graphics.drawString(line, xPos, y + row + fontAscent);
                    row += fontHeight;
                } else {
                    graphics.drawString(line, x, y + row + fontAscent);
                    row += fontHeight;
                }
            }
        }
    }

    private void parseString(Graphics2D graphics) {
        //Debug.drawRect(true, graphics, new Rectangle2D.Double(x,y,(double)width, (double) height));
        graphics.setFont(font);
        graphics.setColor(color);
        String newLine = "";
        displayText = "";
        if(!text.equals("")) {
            for (String line : text.split("\n")) {
                for (String word : line.split(" ")) {
                    if (graphics.getFontMetrics().stringWidth(newLine + word) < width) {
                        newLine = newLine.concat(word + " ");
                    } else {
                        displayText = displayText.concat(newLine + "\n");
                        newLine = word + " ";
                    }
                }
                displayText = displayText.concat(newLine + "\n");
                newLine = "";
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        displayText = "";
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void load(){
        if(imagePath != ""){
            image = AssetLoader.load(imagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }

    @Override
    public void scale(float scaleFactor) {
        super.scale(scaleFactor);
        this.font = font.deriveFont((font.getSize() * scaleFactor));
    }
}
