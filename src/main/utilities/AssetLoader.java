package main.utilities;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;


/**
 * The AssetLoader is a utility class that loads images from disk.
 * It is used in the loadContent method of a GameScreen.
 */
public final class AssetLoader {

    private static final URL missingImage = AssetLoader.class.getResource("/assets/testAssets/Error-MissingImage.png");

    /**
     * Loads an image file from disk. If the asset loader cannot
     * find an image file associated with the file path, this method returns
     * the missing image file instead.
     * @param imagePath The path to the image on disk
     * @return A buffered image created from the image file
     */
    public static BufferedImage load(String imagePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(AssetLoader.class.getResource(imagePath));
            return image;
        } catch (Exception e) {
            try {
                Debug.error(true, "Failed to load image - " + imagePath);
                Debug.error(true, "EXCEPTION MESSAGE:" + e.getMessage());
                image = convertImage(ImageIO.read(missingImage));
                return image;
            }catch (Exception ex) {
                Debug.criticalError("Failed to load - MissingImage.png");
                Debug.criticalError("EXCEPTION MESSAGE: " + ex.getMessage());
                return null;
            }
        }
    }

    /**
     * This method converts a BufferedImage to a type optimized for our
     * game. It appears to do nothing but I assure you its important.
     * @param image Image to be converted
     * @return The converted Image
     */
    private static BufferedImage convertImage(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * This method resizes an image
     * @param image The image to be resized
     * @param newWidth The new width of the image
     * @param newHeight The new height of the image
     * @return A BufferedImage with the specified width and height
     */
    public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(),
                image.getHeight(), null);
        g.dispose();

        return resizedImage;
    }

    /**
     * This method scales an images width and height by the specified scale factor
     * @param image The image to be scaled
     * @param scaleFactor The factor to multiply the width and height by
     * @return A BufferedImage scaled by the specified amount
     */
    public static BufferedImage scaleImage(BufferedImage image, double scaleFactor) {
        return resizeImage(image,
                (int)(image.getWidth()*scaleFactor),
                (int)(image.getHeight()*scaleFactor));
    }
}
