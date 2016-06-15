package entitites;

import gfx.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bartek` on 2016-06-11.
 */
public class Parking extends Entity {
    //attributes
    private BufferedImage image;

    //methods
    public Parking(int x, int y) {
        super(x, y);
        image = ImageLoader.loadImage("/Parking.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,x,y,null);
    }
    @Override
    public void tick() {

    }
}
