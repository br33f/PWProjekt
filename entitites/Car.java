package entitites;

import app.World;

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
    private BufferedImage image;
    private int imageC=1;

    //methods
    public Car(int x, int y, int width, int height, World world) {
        super(x, y, width, height);

        this.miejsceParkingowe = null;
        this.world = world;

        this.counter = 0;
        this.age = 0;
        this.etap = 0;
        this.nr = this.world.createdCars;

        Random generator = new Random();
        this.speed += generator.nextInt(3);
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.drawRect(x, y, width, height);
//        g.setColor(Color.RED);
//        g.fill3DRect(x, y, width, height, true);

        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(this.etap) + ":" + Integer.toString(this.age), x + 10, y + 20);
        try
        {
            if(imageC==1) image = ImageIO.read(new File("Car1.png"));
            else if(imageC==2) image = ImageIO.read(new File("Car2.png"));
            else if(imageC==3) image = ImageIO.read(new File("Car3.png"));
            else if(imageC==4) image = ImageIO.read(new File("Car4.png"));
        }

        catch (IOException ex)
        {
            System.out.println("Brak ikonki");
            System.exit(1);
        }
        g.drawImage(image,x,y,null);

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
                imageC=2;
                this.searchSpot();
                break;
            case 2:
                //jazda na miejsce
                imageC=2;
                this.goParkingSlot();
                break;
            case 3:
                //szukaj naprawy
                imageC=1;
                this.searchRepair();
                break;
            case 4:
                //jedz na stanowisko
                imageC=2;
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
                imageC=3;
                this.goCheckout();
                break;
            case 7:
                //wyjazd
                this.goOut();
                imageC=4;
                break;
        }
    }

    private void swap(){
        int px = width;
        this.width = this.height;
        this.height = px;
    }

    private void searchWait(){
        Car przedemna = null;
        for(Car c : this.world.cars){
            if(c != null) {
                if (c.age > this.age && c.etap == 0) {
                    przedemna = c;
                    break;
                }
            }
        }
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
        boolean mojaKolej = true;
        for(Car c : this.world.cars){
            if(c != null) {
                if (c.age > this.age && c.etap == 3) {
                    mojaKolej = false;
                    break;
                }
            }
        }
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
        if(this.x > s.x + 200)
            this.x -= this.speed;
        else if(this.width > this.height)
            this.swap();
        else if(this.y + this.width/2 < s.y + s.height/2)
            this.y++;
        else if(this.y + this.width/2 > s.y + s.height/2)
            this.y--;
        else if(this.width < this.height) {
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
            this.x += this.speed;
        else if(this.width > this.height)
            this.swap();
        else if(this.y < 386)
            this.y += this.speed;
    }

    private void goOut(){
        if(this.height > this.width && this.x < 450)
            this.swap();
        else if(this.x < 450)
            this.x += this.speed;
        else if(this.height < this.width)
            this.swap();
        else if(this.y < 801)
            this.y += this.speed;
        else{
            if(this.world.lastSpawn > 20) {
                this.etap = 0;
                this.x = 700;
                this.y = 800;
                this.age = 0;
                this.world.lastSpawn = 0;
            }
        }
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
