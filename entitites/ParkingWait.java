package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class ParkingWait extends Entity {
    //attributes
    private Car zajmujacy;
    private boolean best;

    //methods
    public ParkingWait(int x, int y, int width, int height, boolean best) {
        super(x, y, width, height);

        this.best = best;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    @Override
    public void tick() {

    }

    public boolean isBest(){
        return this.best;
    }

    public synchronized boolean isEmpty(){
        return (this.zajmujacy == null);
    }

    public synchronized boolean zajmijMiejsce(Car c){
        if(this.zajmujacy == null){
            this.zajmujacy = c;
            return true;
        }
        else
            return false;
    }

    public synchronized Car getZajmujacy(){
        return this.zajmujacy;
    }

    public synchronized  void zwolnij(){
        this.zajmujacy = null;
    }


}
