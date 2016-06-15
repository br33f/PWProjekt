package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class Station extends Entity {
    //attributes
    private Car naprawiany;

    //methods
    public Station(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.naprawiany = null;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g.setColor(Color.DARK_GRAY);
        g.drawLine(x, y + 20, x + width, y + 20);
        g.drawLine(x, y + height - 20, x + width, y + height - 20);
        g.drawString("Podnosnik", x + 10, y + 10);
        g2.setStroke(new BasicStroke(1));
    }

    @Override
    public void tick() {

    }

    public synchronized boolean isEmpty(){
        return (this.naprawiany == null);
    }

    public synchronized boolean napraw(Car car){
        if(this.isEmpty()){
            this.naprawiany = car;
            return true;
        }
        else
            return false;
    }

    public synchronized Car getNaprawiany(){
        return this.naprawiany;
    }

    public synchronized void zwolnij(){
        this.naprawiany = null;
    }
}
