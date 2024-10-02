package server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The CustomTools class provides utility methods for loading images and fonts.
 */
public class CustomTools {
    /**
     * Creates a JLabel with an image from a resource.
     *
     * @param resource The path to the image resource.
     * @return A JLabel containing the image, or null if an error occurs.
     */
    public static JLabel loadImage(String resource) {
        BufferedImage image;
        try (InputStream inputStream = CustomTools.class.getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    /**
     * Updates the image in a JLabel with a new image from a resource.
     *
     * @param imageContainer The JLabel to update.
     * @param resource       The path to the image resource.
     */
    public static void updateImage(JLabel imageContainer, String resource) {
        BufferedImage image;
        try (InputStream inputStream = CustomTools.class.getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Creates a custom font from a resource.
     *
     * @param resource The path to the font resource.
     * @return The custom font, or null if an error occurs.
     */
    public static Font createFont(String resource) {
        try (InputStream inputStream = CustomTools.class.getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resource);
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return customFont;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    /**
     * Replaces all characters in a word with '*' except spaces.
     *
     * @param word The word to hide.
     * @return The word with characters replaced by '*'.
     */
    public static String hideWords(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != ' ') {
                hiddenWord.append("*");
            } else {
                hiddenWord.append(" ");
            }
        }
        return hiddenWord.toString();
    }
}