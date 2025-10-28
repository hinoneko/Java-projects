package main.passengers;

import java.util.Objects;

public class Passenger {
    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}