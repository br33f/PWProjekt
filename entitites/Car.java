package entitites;

import app.World;
import gfx.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by br33 on 11.06.2016.
 */
public class Car extends Entity implements Runnable {
    //attributes
    private Timer carTimer;
    private int age, counter;
    public int etap;
    private World world;
    private ParkingSlot miejsceParkingowe;
    private ParkingWait miejsceCzekajace;
    private int nr;
    private int speed = 1;
    private BufferedImage[] image;
    private int imageC=1;
    private int side;

    //methods
    public Car(int x, int y, int width, int height, World world) {
        super(x, y, width, height);

        this.miejsceParkingowe = null;
        this.world = world;

        this.side = 0;
        this.counter = 0;
        this.age = 0;
        this.etap = 0;
        this.nr = this.world.createdCars;

        Random generator = new Random();
        this.speed += generator.nextInt(3);

        //ustaw ikony
        image = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            image[i] = ImageLoader.loadImage("/Car" + (i + 1) + ".png");
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawImage(image[imageC - 1],x,y,null);
        g.drawString(Integer.toString(this.age), x + 10, y + 20);

    }

    @Override
    public void tick() {
        switch(etap){
            case 0:
                //znajdz wait parking
                imageC=1;
                this.searchWait();
                if(miejsceCzekajace != null) {
                    this.goParkingWait();
                    if(miejsceCzekajace.isBest() && this.y <= miejsceCzekajace.y + 10) {
                        etap++;
                    }
                }
                break;
            case 1:
                //szukaj miejsca do parkowania
                imageC=1;
                this.searchSpot();
                break;
            case 2:
                //jazda na miejsce
                //imageC=1;
                this.goParkingSlot();
                break;
            case 3:
                //szukaj naprawy
                imageC=2;
                this.searchRepair();
                break;
            case 4:
                //jedz na stanowisko
               // imageC=2;
                this.goStation1();
                break;
            case 5:
                //dojazd na stanowisko przez drzwi
                if(this.world.checkout.getObslugiwany() != null)
                    if(this.world.checkout.getObslugiwany().y < 340)
                        break;
                this.goStation2();
                break;
            case 6:
                //dojazd do kasy

                this.goCheckout();
                break;
            case 7:
                //wyjazd
                this.goOut();
                break;
        }
    }

    public void setSide(int side) {
        this.side = side;
    }

    private void swap(){
        int px = width;
        this.width = this.height;
        this.height = px;
    }

    private void searchWait(){
        Car przedemna = this.carsAhead(0);
        for (ParkingWait pw : this.world.parkingWaits) {
            if (pw.isEmpty()) {
                //warunki pierszenstwa
                if(przedemna != null) {
                    if (przedemna.miejsceCzekajace == null)
                        return;
                    else if(przedemna.miejsceCzekajace.y > pw.y)
                        return;
                }

                if (miejsceCzekajace != null) {
                    if (miejsceCzekajace.y > pw.y) {
                        if (pw.zajmijMiejsce(this)) {
                            miejsceCzekajace.zwolnij();
                            miejsceCzekajace = pw;
                        }
                    }
                } else {
                    if (pw.zajmijMiejsce(this))
                        miejsceCzekajace = pw;
                }
                break;
            }
        }
    }
    private void goParkingWait(){
        if(this.y > this.miejsceCzekajace.y + 10)
            this.y -= 3;
    }

    private void searchSpot(){
        for(ParkingSlot ps : this.world.parkingSlots){
            if(ps.isEmpty())
                if(ps.zaparkuj(this)) {
                    miejsceParkingowe = ps;
                    etap++;
                    miejsceCzekajace.zwolnij();
                    miejsceCzekajace = null;
                    break;
                }
        }
    }

    private void goParkingSlot(){
        if(this.y + this.height/2 > this.miejsceParkingowe.y + this.height/2 + 2){
            //nie dojechalem na wysokosc
            this.y -= 3;
        }
        else if(this.width < this.height){
            //dojechalem na wysokosc - obracam
            imageC=2;
            this.swap();
        }
        else if(this.x > this.miejsceParkingowe.x + 6){
            this.x -= 3;
        }
        else{
            this.age = 0;
            etap++;
        }
    }

    private void searchRepair(){
        Car przedemna = this.carsAhead(3);
        boolean mojaKolej = (przedemna == null) ? true : false;
        if(mojaKolej){
            if(this.world.station.isEmpty())
                if(this.world.station.napraw(this)){
                    etap++;
                    this.miejsceParkingowe.zwolnij();
                }
        }
    }

    private void goStation1(){
        Station s = this.world.station;
        if(this.x > s.x + 230)
            this.x -= this.speed;
        else if(this.width > this.height) {
            imageC=3;
            this.swap();
        }
        else if(this.y + this.width/2 < s.y + s.height/2)
            this.y++;
        else if(this.y + this.width/2 > s.y + s.height/2)
            this.y--;
        else if(this.width < this.height) {
            imageC=2;
            this.swap();
            etap++;
        }
    }

    private void goStation2(){
        if(this.x > this.world.station.x + 15)
            this.x -= this.speed;
    }

    private void goCheckout(){
        if(this.x < 255)
        {
            imageC=4;
            this.x += this.speed;
        }

        else if(this.width > this.height)
        {

            this.swap();
            imageC=3;
        }

        else if(this.y < 386)
            this.y += this.speed;
    }

    private void goOut(){
        if(this.height > this.width && this.x < 450){
            imageC=4;
            this.swap();
        }

        else if(this.x < 450 + (side * 50))
            this.x += this.speed;
        else if(this.height < this.width){
            this.swap();
            imageC=3;
        }

        else if(this.y < 801)
            this.y += this.speed;
        else{
            if(this.world.lastSpawn > 20) {
                this.etap = 0;
                this.x = 720;
                this.y = 800;
                this.age = 0;
                this.world.lastSpawn = 0;
            }
        }
    }

    private Car carsAhead(int etap){
        Car przedemna = null;
        for(Car c : this.world.cars){
            if(c != null) {
                if (c.age > this.age && c.etap == etap) {
                    przedemna = c;
                    break;
                }
            }
        }
        return przedemna;
    }

    @Override
    public void run() {
        carTimer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
                counter++;
                if(counter > 25){
                    counter = 0;
                    age++;
                }
            }
        });
        carTimer.start();
    }
}
