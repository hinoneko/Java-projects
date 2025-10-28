package main.vehicles;

import main.passengers.Passenger;

public class Bus extends Vehicle<Passenger> {
    public Bus(int maxCapacity) {
        super(maxCapacity);
    }
}
