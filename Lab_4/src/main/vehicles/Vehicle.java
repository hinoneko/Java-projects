package main.vehicles;

import main.passengers.Passenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Vehicle<T extends Passenger> {

    private final int maxCapacity;
    private final List<T> passengers;

    public Vehicle(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Місткість повинна бути більше нуля.");
        }
        this.maxCapacity = maxCapacity;
        this.passengers = new ArrayList<>();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public void boardPassenger(T passenger) {
        if (getOccupiedSeats() >= maxCapacity) {
            throw new IllegalStateException(
                    String.format("%s заповнений. Максимальна місткість: %d",
                            this.getClass().getSimpleName(), maxCapacity));
        }
        if (passengers.contains(passenger)) {
            throw new IllegalArgumentException(
                    String.format("Пасажир %s вже сидить у %s.",
                            passenger.getName(), this.getClass().getSimpleName()));
        }

        passengers.add(passenger);
    }

    public void alightPassenger(T passenger) {
        if (!passengers.remove(passenger)) {
            throw new IllegalArgumentException(
                    String.format("Пасажир %s не сидить у %s.",
                            passenger.getName(), this.getClass().getSimpleName()));
        }
    }

    public List<T> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    @Override
    public String toString() {
        String passengerNames = passengers.isEmpty() ?
                "немає" :
                passengers.stream()
                        .map(Passenger::getName)
                        .collect(java.util.stream.Collectors.joining(", "));

        return String.format("%s (Місткість: %d, Зайнято: %d): Пасажири: [%s]",
                this.getClass().getSimpleName(),
                maxCapacity,
                getOccupiedSeats(),
                passengerNames);
    }
}