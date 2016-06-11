package entitites;

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
    }

    @Override
    public void render(Graphics g) {
        try
        {
            image = ImageIO.read(new File("Parking.png"));
        }
        catch (IOException ex)
        {
            System.out.println("Brak ikonki parking");
            System.exit(1);
        }
        g.drawImage(image,x,y,null);
    }
    public void renderStop(Graphics g) {
        try
        {
            image = ImageIO.read(new File("Stop.png"));
        }
        catch (IOException ex)
        {
            System.out.println("Brak ikonki parking");
            System.exit(1);
        }
        g.drawImage(image,300,170,null);
    }
    @Override
    public void tick() {

    }
}
