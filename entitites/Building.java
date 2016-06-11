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
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);

        g2.drawLine(50, this.y, 300, this.y);
        g2.drawLine(50, this.y, 50, this.y + 400);
        g2.drawLine(50, this.y + 400, 300, this.y + 400);
        g2.drawLine(300, this.y + 400, 300, this.y + 350);
        g2.drawLine(300, this.y + 250, 300, this.y + 150);
        g2.drawLine(300, this.y + 80, 300, this.y);
        g2.drawLine(100, this.y + 230, 230, this.y + 230);

    }

    @Override
    public void tick() {

    }
}
