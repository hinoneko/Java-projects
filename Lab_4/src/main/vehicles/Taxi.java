package main.vehicles;

import main.passengers.Passenger;

public class Taxi extends Car<Passenger> {
    public Taxi(int maxCapacity) {
        super(maxCapacity);
    }
}
