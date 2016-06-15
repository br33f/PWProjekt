package entitites;

import app.World;
import gfx.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by br33 on 11.06.2016.
 */
public class Worker extends Entity implements Runnable{
    //attributes
    private int status;
    private int naprawaCzas = 50;
    private Car naprawiany;
    private World world;
    private BufferedImage[] image;
    private int imgStatus=1;
    private int side;
    private int foot;


    //methods
    public Worker(int x, int y, int width, int height, World world) {
        super(x, y, width, height);

        this.status = 0;
        this.world = world;
        this.naprawiany = null;
        this.side = 0;
        this.foot = 0;

        //ustaw ikony
        image = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            image[i] = ImageLoader.loadImage("/Worker" + (i + 1) + ".png");
    }

    @Override
    public void render(Graphics g) {
        int f = (foot > 5) ? 2 : 0;
        g.drawImage(image[imgStatus - 1 + f],x,y,width+10, height+10, null);
    }

    @Override
    public void tick() {
        switch(status){
            case 0:
                //szukaj sobie zajęcia
                this.searchJob();
                break;
            case 1:
                //idz do stacji
                imgStatus=2;
                this.goStation();
                break;
            case 2:
                //idz do kasy
                this.goCheckout();
                imgStatus=1;
                break;
        }
    }

    private void searchJob(){
        if(!this.world.station.isEmpty())
            status = 1;
        if(!this.world.checkout.isEmpty())
            status = 2;
    }

    private void goStation(){
        if(this.x > this.world.building.x + 10) {
            this.x -= 2;
            this.foot = (this.foot + 1) % 10;
        }
        else if(this.y > this.world.station.y + this.world.station.height / 2) {
            this.y -= 2;
            this.foot = (this.foot + 1) % 10;
        }
        else{
            if(this.world.station.getNaprawiany() != null || this.naprawiany != null)
            {
                if(this.naprawiany == null){
                    if(this.world.station.getNaprawiany().x <= this.world.station.x + 15 ){
                        this.naprawiany = this.world.station.getNaprawiany();
                        this.world.station.zwolnij();
                        this.world.checkout.zaplac(this.naprawiany);
                    }
                }
            }

            //jeżeli jest na pozycji naprawy
            if(this.naprawiany != null) {
                naprawaCzas--;
                if (naprawaCzas <= 0) {
                    this.naprawiany.etap++;
                    status++;

                    Random generator = new Random();
                    this.naprawaCzas = 15 + generator.nextInt(80);

                    this.naprawiany = null;
                }
            }
        }
    }

    private void goCheckout(){
        if(this.x > this.world.building.x + 10 && this.y < 420) {
            this.foot = (this.foot + 1) % 10;
            this.x -= 2;
        }
        else if(this.y < 420) {
            this.foot = (this.foot + 1) % 10;
            this.y += 2;
        }
        else if(this.x < 200){
            this.foot = (this.foot + 1) % 10;
            this.x += 2;
        }
        else if(this.world.checkout.getObslugiwany().y >= 386){
            status = 0;
            this.world.checkout.getObslugiwany().setSide(side);
            side = (side + 1) % 2;
            this.world.checkout.zwolnij();
        }
    }

    @Override
    public void run() {
        Timer workerTimer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
            }
        });
        workerTimer.start();
    }
}
