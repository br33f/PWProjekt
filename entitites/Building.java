package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class Building extends Entity {
    //methods
    public Building() {
        super(50, 120, 0, 0);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(220, 220, 220));
        g.fillRect(x, y, 270, 400);

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);

        g2.drawLine(50, this.y, 320, this.y);
        g2.drawLine(50, this.y, 50, this.y + 400);
        g2.drawLine(50, this.y + 400, 320, this.y + 400);
        g2.drawLine(320, this.y + 400, 320, this.y + 350);
        g2.drawLine(320, this.y + 250, 320, this.y + 150);
        g2.drawLine(320, this.y + 80, 320, this.y);
        g2.drawLine(100, this.y + 230, 230, this.y + 230);

        g2.setStroke(new BasicStroke(1));

    }

    @Override
    public void tick() {

    }
}
