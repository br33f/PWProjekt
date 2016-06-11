package entitites;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public abstract class Entity {
    //attributes
    protected int x, y, width, height;

    //methods
    public Entity(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public abstract void render(Graphics g);

    public abstract void tick();
}
