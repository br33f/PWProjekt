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
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
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
}
