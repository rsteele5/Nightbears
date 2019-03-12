package gameengine.rendering;

//TODO: Add javadoc comments

//Java Imports
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Project Imports
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class RenderEngine extends JPanel {

    //region <Variables>
    private ScreenManager screenManager;

    // off splashscreen rendering
    private Graphics2D graphics;
    private BufferedImage dbImage = null;
    private GraphicsConfiguration graphicsConfig;
    //endregion

    public RenderEngine(ScreenManager myScreenManager) {
        screenManager = myScreenManager;
        setBackground(Color.BLACK);
        graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/NoScary.ttf")));
            Debug.success(DebugEnabler.RENDER_ENGINE,"Font: NoScary registered");
        } catch (IOException |FontFormatException e) {
            Debug.error(DebugEnabler.RENDER_ENGINE,"Font: Cannot be found");
        }
    }

    //TODO: Greg FIx  this shit usage in mouse controller
    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public void draw() {
        // size of the canvas - determined at runtime once rendered
        int width = getSize().width;
        int height = getSize().height;
        if(width > 0 && height > 0) {
            if (dbImage == null) {
                //Creates an off-splashscreen drawable image to be used for double buffering
                dbImage = graphicsConfig.createCompatibleImage(width, height, Transparency.OPAQUE);
                if (dbImage == null) {
                    Debug.error(DebugEnabler.RENDER_ENGINE,"Critical Error: dbImage is null");
                    System.exit(1);
                } else {
                    graphics = (Graphics2D) dbImage.getGraphics();
                }
                graphics.clearRect(0, 0, width, height);
                graphics.setBackground(Color.BLACK);
            }

            screenManager.draw(graphics);
            renderBufferToScreen();
            graphics.clearRect(0, 0, width, height);
        }
    }

    private void renderBufferToScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
