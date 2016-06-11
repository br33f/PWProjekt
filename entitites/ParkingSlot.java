package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class ParkingSlot extends Entity {
    //attributes
    private Car zaparkowany;

    //methods
    public ParkingSlot(int x, int y, int width, int height){
        super(x, y, width, height);

        this.zaparkowany = null;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawLine(this.x, this.y, this.x + this.width, this.y);
        g.drawLine(this.x, this.y + this.height, this.x + this.width, this.y + this.height);
    }

    @Override
    public void tick() {

    }

    public synchronized boolean isEmpty(){
        return (this.zaparkowany == null);
    }

    public synchronized boolean zaparkuj(Car car){
        if(isEmpty()) {
            this.zaparkowany = car;
            return true;
        }
        else return false;
    }

    public synchronized Car getZaparkowany(){
        return this.zaparkowany;
    }

    public synchronized void zwolnij(){
        this.zaparkowany = null;
    }
}
