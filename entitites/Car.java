package entitites;

import app.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by br33 on 11.06.2016.
 */
public class Car extends Entity implements Runnable {
    //attributes
    private Timer carTimer;
    private int age, counter;
    private int etap;
    private World world;
    private ParkingSlot miejsceParkingowe;

    //methods
    public Car(int x, int y, int width, int height, World world) {
        super(x, y, width, height);

        this.miejsceParkingowe = null;
        this.world = world;

        carTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
                counter++;
                if(counter > 200){
                    counter = 0;
                    age++;
                }
            }
        });
        carTimer.start();

        this.age = this.counter = 0;
        this.etap = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.setColor(Color.RED);
        g.fill3DRect(x, y, width, height, true);
    }

    @Override
    public void tick() {
        switch(etap){
            case 0:
                //szukaj miejsca do parkowania
                this.searchSpot();
                break;
            case 1:
                //jazda na miejsce
                this.goParkingSlot();
                break;
            case 2:
                //szukaj naprawy
                this.searchRepair();
                break;
            case 3:
                //jedz na stanowisko
                this.goStation1();
                break;
            case 4:
                //dojazd na stanowisko przez drzwi
                this.goStation2();
                break;
        }
    }

    private void swap(){
        int px = width;
        this.width = this.height;
        this.height = px;
    }

    private void searchSpot(){
        for(ParkingSlot ps : this.world.parkingSlots){
            if(ps.isEmpty())
                if(ps.zaparkuj(this)) {
                    miejsceParkingowe = ps;
                    etap++;
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
            etap++;
        }
    }

    private void searchRepair(){
        boolean mojaKolej = true;
        for(Car c : this.world.cars){
            if(c != null) {
                if (c.age > this.age) {
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
            this.x -= 3;
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
        if(this.x > this.world.station.x + 30)
            this.x -= 3;
        else
            etap++;
    }

    @Override
    public void run() {
        System.out.println("witam");
    }
}
