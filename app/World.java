package app;

import entitites.*;
import gui.Configurator;

import java.awt.*;
import java.util.Random;

/**
 * Created by br33 on 11.06.2016.
 */
public class World {
    //attributes
    public Configurator cfg;
    private Graphics g;
    public int createdCars = 0;
    public int lastSpawn = 0;
    private int rTick = 50;
    private int counter = 0;
    public ParkingSlot[] parkingSlots;
    public ParkingWait[] parkingWaits;
    public Car[] cars;
    public Station station;
    public Building building;
    public Worker worker;
    public Checkout checkout;

    //methods
    public World(){
        this.cfg = Configurator.getInstance();

        //create parking waits
        this.parkingWaits = new ParkingWait[3];
        for(int i = 0; i < 3; i++) {
            boolean best = (i == 0) ? true : false;
            this.parkingWaits[i] = new ParkingWait(690, 250 + (72 * i), 50, 70, best);
        }

        //create parking slots
        this.parkingSlots = new ParkingSlot[3];
        for(int i = 0; i < 3; i++)
            this.parkingSlots[i] = new ParkingSlot(520, 100 + (40 * i), 60, 35);

        //create Station
        this.station = new Station(120, 200, 100, 60);

        //create building
        this.building = new Building();

        //create checkout
        this.checkout = new Checkout(250, 380, 40, 60);

        //create worker
        this.worker = new Worker(100, 470, 20, 20, this);
        new Thread(this.worker).start();

        //create cars
        this.cars = new Car[this.cfg.getCars()];

        //first car
        this.cars[createdCars] = new Car(700, 750, 30, 50, this);
        new Thread(this.cars[createdCars]).start();
        this.createdCars++;

    }

    public void render(Graphics g){
        for(ParkingSlot slot : this.parkingSlots) {
            slot.render(g);
        }

        for(ParkingWait slot : this.parkingWaits) {
            slot.render(g);
        }

        this.station.render(g);
        this.building.render(g);
        this.checkout.render(g);

        this.worker.render(g);

        for(Car car : this.cars) {
            if(car != null)
                car.render(g);
        }
        //System.out.println("render inside");
    }

    public void tick(){
        lastSpawn++;
        counter++;
        if(counter > rTick){
            counter = 0;
            createCar();
            Random generator = new Random();
            rTick = 20 + generator.nextInt(80);
        }
        //System.out.println("tick inside");
    }

    private void createCar(){
        if(this.createdCars < this.cfg.getCars() && this.lastSpawn > 20) {
            this.cars[createdCars] = new Car(700, 750, 30, 50, this);
            new Thread(this.cars[createdCars]).start();
            this.createdCars++;
            this.lastSpawn = 0;
        }
    }
}
