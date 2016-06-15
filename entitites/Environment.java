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
public class Environment extends Entity
{
    //attributes
    private BufferedImage image;

    //methods
    public Environment(int x, int y) {
        super(x, y);
        image = ImageLoader.loadImage("/Pasy.png");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(58, 157, 35));
        g.fillRect(0, 0, 800, 800);
        g.drawImage(image,0,-20,null);
    }

    @Override
    public void tick() {

    }
}
