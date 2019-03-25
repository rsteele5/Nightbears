package main.utilities;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

/**
 * This is a wrapper class that aggregates and wraps the standard
 * java System.out functions. This allows for them to be more useful
 * in our use cases.
 */
public class Debug {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[91m";
    private static final String ANSI_GREEN = "\u001B[92m";
    private static final String ANSI_YELLOW = "\u001B[93m";
    private static final String ANSI_BLACK = "\u001B[97m";
    private static final String ANSI_BG_RED = "\u001b[41m";


    /**
     * This simply prints a message to the console.
     * <br><br><p>If the status is false the message WILL NOT BE DISPLAYED.</p>
     * @param status The DebugEnabler static for this particular message
     * @param message The message to be written to the console
     */
    public static void log(boolean status, String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println("  [Debug] -> " + message);
        }
    }

    /**
     * This prints a success message to the console. Success messages
     * are represented by green text.
     * <br><br><p>If the status is false the message WILL NOT BE DISPLAYED.</p>
     * @param status The DebugEnabler static for this particular message
     * @param message The message to be written to the console
     */
    public static void success(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_GREEN + "[Success] -> " + message + ANSI_RESET);
        }
    }

    /**
     * This prints a warning message to the console. Warning messages
     * are represented by green text.
     * <br><br><p>If the status is false the message WILL NOT BE DISPLAYED.</p>
     * @param status The DebugEnabler static for this particular message
     * @param message The message to be written to the console
     */
    public static void  warning(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_YELLOW + "[Warning] -> "+  message + ANSI_RESET);
        }
    }

    /**
     * This prints a error message to the console. Error messages
     * are represented by green text.
     * <br><br><p>If the status is false the message WILL NOT BE DISPLAYED.</p>
     * @param status The DebugEnabler static for this particular message
     * @param message The message to be written to the console
     */
    public static void error(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_RED + "  [Error] -> "+  message + ANSI_RESET);
        }
    }

    /**
     * This prints a critocal error message to the console. Critical error
     * messages are represented with a special [Error] tag and red text.
     * These are to be used to catch exceptions thrown by the program.
     * <br><br><p>Critical error messages are always displayed</p>
     * @param message The message to be written to the console
     */
    public static void criticalError(String message) {
        System.out.println("  " + ANSI_BG_RED  + "[Error]" + ANSI_RESET + ANSI_RED + " -> " +   message + ANSI_RESET);
    }

    /**
     * Draws a green rectangle to the screen defined by the parameter.
     * <br><br><p>If the status is false the rectangle WILL NOT BE DRAWN.</p>
     * @param status The DebugEnabler static for drawing
     * @param graphics The graphics context to draw to
     * @param rect The rectangle to be drawn
     */
    public static void drawRect(boolean status, Graphics2D graphics, Rectangle2D rect) {
        graphics.setColor(Color.GREEN);
        if(DebugEnabler.DRAWING_ACTIVE && status) {
            graphics.drawRect((int)rect.getX() - 5, (int)rect.getY() - 5, (int)rect.getWidth() + 5, (int)rect.getHeight() + 5);
        }
        graphics.setColor(Color.WHITE);
    }

    /**
     * Draws the specified string to the screen location
     * <br><br><p>If the status is false the string WILL NOT BE DRAWN.</p>
     * @param status The DebugEnabler static for drawing
     * @param graphics The graphics context to draw to
     * @param x The x position of the string to be drawn
     * @param y The y position of the string to be drawn
     * @param string The string to be drawn
     */
    public static void drawString(boolean status, Graphics graphics, int x, int y, String string) {
        graphics.setColor(Color.GREEN);
        Font font = graphics.getFont();
        graphics.setFont(new Font( "Arial", Font.PLAIN, 22));
        if(DebugEnabler.DRAWING_ACTIVE && status) {
            graphics.drawString(string, x, y);
        }
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
    }

    /**
     * Begins logging to the screen. This method specifically enables the
     * Garbage collection log.
     */
    public static void startLog() {
        if(DebugEnabler.LOGGING_ACTIVE){
            System.out.println(ANSI_GREEN + "[Success] -> Logging activated successfully" + ANSI_RESET);
            startLoggingGc();
        }else{
            System.out.println(ANSI_YELLOW + "[Warning] -> Logging Disabled" + ANSI_RESET);
        }
    }

    private static void startLoggingGc() {
        // http://www.programcreek.com/java-api-examples/index.php?class=javax.management.MBeanServerConnection&method=addNotificationListener
        // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html#GARBAGE_COLLECTION_NOTIFICATION
        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer().
                        addNotificationListener(gcMbean.getObjectName(), listener, null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Hooks into the Garbage Collector and logs when he does his thing
    static private NotificationListener listener = new NotificationListener() {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            CompositeData cd = (CompositeData) notification.getUserData();
            GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
            GcInfo gcInfo = gcNotificationInfo.getGcInfo();
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                if(DebugEnabler.GARBAGE_COLLECTION)
                System.out.println(ANSI_YELLOW + "     [GC] -> Garbage Collected! || Duration: " + gcInfo.getDuration() + "ms ||" + ANSI_RESET);
            }
        }
    };
}
