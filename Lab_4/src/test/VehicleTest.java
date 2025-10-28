package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.Road;
import main.vehicles.*;
import main.passengers.*;

public class VehicleTest {

    private final Passenger regularPassenger = new Passenger("Олег");
    private final Firefighter firefighter = new Firefighter("Іван");
    private final Policeman policeOfficer = new Policeman("Петро");

    @Test
    void busAndTaxi_canBoardAnyPassenger() {
        Bus bus = new Bus(10);
        Taxi taxi = new Taxi(4);

        assertDoesNotThrow(() -> bus.boardPassenger(regularPassenger));
        assertDoesNotThrow(() -> bus.boardPassenger(firefighter));
        assertDoesNotThrow(() -> taxi.boardPassenger(policeOfficer));

        assertEquals(2, bus.getOccupiedSeats());
        assertEquals(1, taxi.getOccupiedSeats());

        assertInstanceOf(Car.class, taxi);
        assertInstanceOf(Vehicle.class, taxi);
    }

    @Test
    void fireTruck_canOnlyBoardFirefighters() {
        FireTruck fireTruck = new FireTruck(3);

        assertDoesNotThrow(() -> fireTruck.boardPassenger(firefighter));
        assertEquals(1, fireTruck.getOccupiedSeats());
    }

    @Test
    void policeCar_canOnlyBoardPoliceOfficers() {
        PoliceCar policeCar = new PoliceCar(4);

        assertDoesNotThrow(() -> policeCar.boardPassenger(policeOfficer));
        assertEquals(1, policeCar.getOccupiedSeats());
    }

    @Test
    void vehicle_boardsAndAlightsPassenger() {
        Taxi taxi = new Taxi(2);
        taxi.boardPassenger(regularPassenger);

        assertEquals(2, taxi.getMaxCapacity());
        assertEquals(1, taxi.getOccupiedSeats());

        assertDoesNotThrow(() -> taxi.alightPassenger(regularPassenger));
        assertEquals(0, taxi.getOccupiedSeats());
    }

    @Test
    void vehicle_throwsException_onFullCapacity() {
        Bus bus = new Bus(1);
        bus.boardPassenger(regularPassenger);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                bus.boardPassenger(firefighter)
        );
        assertTrue(exception.getMessage().contains("заповнений"));
    }

    @Test
    void vehicle_throwsException_onNonExistingPassengerAlight() {
        Taxi taxi = new Taxi(2);
        taxi.boardPassenger(regularPassenger);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taxi.alightPassenger(firefighter)
        );
        assertTrue(exception.getMessage().contains("не сидить"));
    }

    @Test
    void road_countsAllHumansCorrectly_usingWildcard() {
        Road road = new Road();

        Bus bus = new Bus(5);
        bus.boardPassenger(regularPassenger);
        bus.boardPassenger(firefighter);
        bus.boardPassenger(new Passenger("Марія"));
        road.addCarToRoad(bus);

        FireTruck fireTruck = new FireTruck(3);
        fireTruck.boardPassenger(firefighter);
        fireTruck.boardPassenger(new Firefighter("Олег"));
        road.addCarToRoad(fireTruck);

        PoliceCar policeCar = new PoliceCar(4);
        policeCar.boardPassenger(policeOfficer);
        road.addCarToRoad(policeCar);

        assertEquals(6, road.getCountOfHumans(), "Кількість людей на дорозі підраховано неправильно.");
    }
}