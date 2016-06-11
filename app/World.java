package app;

import entitites.Building;
import entitites.Car;
import entitites.ParkingSlot;
import entitites.Station;
import gui.Configurator;

import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class World {
    //attributes
    private Configurator cfg;
    private int createdCars = 0;
    private int counter = 0;
    public ParkingSlot[] parkingSlots;
    public Car[] cars;
    public Station station;
    public Building building;

    //methods
    public World(){
        this.cfg = Configurator.getInstance();

        //create parking slots
        this.parkingSlots = new ParkingSlot[this.cfg.getCars()];
        for(int i = 0; i < this.cfg.getCars(); i++)
            this.parkingSlots[i] = new ParkingSlot(520, 100 + (40 * i), 60, 35);

        //create Station
        this.station = new Station(120, 200, 100, 60);

        //create building
        this.building = new Building();

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

        this.station.render(g);
        this.building.render(g);

        for(Car car : this.cars) {
            if(car != null)
                car.render(g);
        }
        //System.out.println("render inside");
    }

    public void tick(){
        counter++;
        if(counter > 100){
            counter = 0;
            tick100();
        }

        for(ParkingSlot slot : this.parkingSlots) {
            slot.tick();
        }
        //System.out.println("tick inside");
    }

    private void tick100(){
        if(this.createdCars < this.cfg.getCars()) {
            this.cars[createdCars] = new Car(700, 750, 30, 50, this);
            new Thread(this.cars[createdCars]).start();
            this.createdCars++;
        }
    }
}
