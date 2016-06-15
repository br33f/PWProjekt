package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa ImageLoader.
 * Zawiera jedynie statyczną metodę, która ładuje obrazek z określonego źródła i zwraca BufferedImage.
 */
public class ImageLoader {
    public static BufferedImage loadImage(String path)
    {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}