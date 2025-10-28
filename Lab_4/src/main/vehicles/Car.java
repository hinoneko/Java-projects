package main.vehicles;

import main.passengers.Passenger;

public abstract class Car<T extends Passenger> extends Vehicle<T> {
    public Car(int maxCapacity) {
        super(maxCapacity);
    }
}
