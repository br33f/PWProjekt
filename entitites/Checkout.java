package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class Checkout extends Entity {
    //attributes
    private Car obslugiwany;

    //methods
    public Checkout(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.obslugiwany = null;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.drawString("KASA", x + 5, y + height + 15);
    }

    @Override
    public void tick() {

    }

    public synchronized boolean isEmpty(){
        return (this.obslugiwany == null);
    }

    public synchronized boolean zaplac(Car car){
        if(isEmpty()){
            this.obslugiwany = car;
            return true;
        }
        else
            return false;
    }

    public synchronized Car getObslugiwany(){
        return this.obslugiwany;
    }

    public synchronized void zwolnij(){
        this.obslugiwany.etap++;
        this.obslugiwany = null;
    }
}
