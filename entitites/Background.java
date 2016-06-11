package entitites;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bartek` on 2016-06-11.
 */
public class Background extends Entity {

    //atributes
    private BufferedImage image;

    //methods
    public Background(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(Graphics g) {
        try
        {
            image = ImageIO.read(new File("Tlo.png"));
        }
        catch (IOException ex)
        {
            System.out.println("Brak ikonki");
            System.exit(1);
        }
        g.drawImage(image,x,y,null);
    }

    @Override
    public void tick() {

    }
}
