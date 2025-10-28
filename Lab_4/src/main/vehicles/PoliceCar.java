package main.vehicles;

import main.passengers.Policeman;

public class PoliceCar extends Car<Policeman> {
    public PoliceCar(int maxCapacity) {
        super(maxCapacity);
    }
}
