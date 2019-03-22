package gameengine.rendering;

//TODO: Add javadoc comments

//Java Imports
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Project Imports
import gameengine.gamedata.GameData;
import gameengine.gamedata.GraphicsSetting;
import gamescreen.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class RenderEngine extends JPanel {

    //region <Variables>
    private ScreenManager screenManager;
    private GameData gameData;
    private String fps = "";

    // off splashscreen rendering
    private Graphics2D graphics;
    private BufferedImage dbImage = null;
    private GraphicsConfiguration graphicsConfig;
    //endregion

    public RenderEngine(GameData gameData, ScreenManager screenManager) {
        setFocusable(true);
        requestFocusInWindow();
        this.screenManager = screenManager;
        this.gameData = gameData;
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

    public void setFPS(String fps){
        if(System.currentTimeMillis() % 7 == 0)
        this.fps = fps;
    }

    public void draw() {
        // size of the canvas - determined at runtime once rendered
        int width = gameData.getGraphicsSettings().getRenderWidth();
        int height = gameData.getGraphicsSettings().getRenderHeight();

        switch(gameData.getGraphicsSettings().getCurrentOption()){
            case High: setBounds(0,0,width, height); break;
            case Medium: setBounds(320, 180, width, height); break;
            case Low: setBounds(480, 270, width, height); break;
        }

        this.setSize(width, height);
        if (dbImage == null || (dbImage.getWidth() != width)) {
            //Creates an off-splashscreen drawable image to be used for double buffering
            dbImage = graphicsConfig.createCompatibleImage(width, height, Transparency.OPAQUE);
            if (dbImage == null) {
                Debug.error(DebugEnabler.RENDER_ENGINE,"Critical Error: dbImage is null");
                System.exit(1);
            } else {
                graphics = (Graphics2D) dbImage.getGraphics();
                graphics.clearRect(0, 0, width, height);
            }
            graphics.setBackground(Color.BLACK);
        } else {
            screenManager.draw(graphics);
            Debug.drawString(DebugEnabler.FPS_CURRENT,graphics,20,20,fps);
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
