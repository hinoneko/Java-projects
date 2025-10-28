package main;

import main.passengers.Passenger;
import main.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {
    public List<Vehicle<? extends Passenger>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Passenger> car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        carsInRoad.add(car);
    }

    public int getCountOfHumans() {
        int totalHumans = 0;
        for (Vehicle<? extends Passenger> car : carsInRoad) {
            totalHumans += car.getOccupiedSeats();
        }
        return totalHumans;
    }
}