package main;
import main.vehicles.*;
import main.passengers.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("ДЕМОНСТРАЦІЯ СИСТЕМИ ТРАНСПОРТНИХ ЗАСОБІВ (GENERICS)");

        Bus bus = new Bus(10);
        Taxi taxi = new Taxi(3);
        PoliceCar policeCar = new PoliceCar(2);
        FireTruck fireTruck = new FireTruck(2);

        Passenger p1 = new Passenger("Оксана");
        Passenger p2 = new Passenger("Андрій");

        Policeman po1 = new Policeman("офіцер Віктор");
        Policeman po2 = new Policeman("офіцер Ірина");
        Policeman po3 = new Policeman("офіцер Максим");

        Firefighter ff1 = new Firefighter("пожежник Сергій");
        Firefighter ff2 = new Firefighter("пожежник Надія");

        System.out.println("\nЕТАП 1: ПОЧАТКОВА ПОСАДКА ТА ПЕРЕВІРКА ОБМЕЖЕНЬ");

        System.out.println("Посадка успішних пасажирів...");

        System.out.println("  -> В автобус: Оксана, офіцер Віктор (2 пасажири)");
        bus.boardPassenger(p1);
        bus.boardPassenger(po1);

        System.out.println("  -> В таксі: Андрій (1 пасажир)");
        taxi.boardPassenger(p2);

        System.out.println("  -> В поліцейську машину: офіцер Ірина (1 пасажир)");
        policeCar.boardPassenger(po2);

        System.out.println("  -> В пожежну машину: пожежник Сергій (1 пасажир)");
        fireTruck.boardPassenger(ff1);

        System.out.println("[Початкова посадка успішна]");


        System.out.println("\nЕТАП 2: ТЕСТУВАННЯ ОБМЕЖЕНЬ ТА ВИНЯТКІВ");

        System.out.println("Тест 2.0: Спроба посадити офіцера Віктора у пожежну машину...");
        System.out.println("  -> КОМПІЛЯТОР: Generics забороняють цю дію. Викликається 'incompatible types' на етапі компіляції.");


        System.out.print("\nТест 2.1: Спроба посадити пожежника Сергія двічі... ");
        if (fireTruck.getPassengers().contains(ff1)) {
            System.out.println("\nПОМИЛКА: Пасажир " + ff1.getName() + " вже сидить у пожежній машині.");
        } else {
            fireTruck.boardPassenger(ff1);
        }

        System.out.println("\nТест 2.2: Перевірка переповнення поліцейської машини (макс 2).");

        System.out.print("  -> Заповнення поліцейської машини офіцером Максимом... ");
        policeCar.boardPassenger(po3);
        System.out.println("\nЗаповнено.");

        System.out.print("  -> Спроба посадити 3-го офіцера Віктора... ");
        if (policeCar.getOccupiedSeats() >= policeCar.getMaxCapacity()) {
            System.out.println("\nПОМИЛКА: поліцейська машина заповнена. Максимальна місткість: " + policeCar.getMaxCapacity());
        } else {
            policeCar.boardPassenger(po1);
        }


        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);

        System.out.println("\nЕТАП 3: ПОТОЧНИЙ СТАТУС ТА ПІДРАХУНОК НА ДОРОЗІ (WILDCARD)");

        System.out.println("Статус автобуса:           " + bus);
        System.out.println("Статус таксі:              " + taxi);
        System.out.println("Статус поліцейської машини: " + policeCar);
        System.out.println("Статус пожежної машини:    " + fireTruck);


        int totalPassengerCount = road.getCountOfHumans();
        System.out.println("\nЗАГАЛЬНА КІЛЬКІСТЬ ЛЮДЕЙ НА ДОРОЗІ: " + totalPassengerCount + " (Сума пасажирів у всіх ТЗ)");


        System.out.println("\nЕТАП 4: ВИСАДКА ТА ОНОВЛЕННЯ СТАТУСУ");

        System.out.print("Тест 4.1: Висадка Оксани з автобуса... ");
        bus.alightPassenger(p1);
        System.out.println("\nУспішно.");

        System.out.print("Тест 4.2: Спроба висадити пожежника Надію з пожежної машини (її там немає)");
        if (!fireTruck.getPassengers().contains(ff2)) {
            System.out.println("\nПОМИЛКА: Пасажир " + ff2.getName() + " не сидить у пожежній машині.");
        } else {
            fireTruck.alightPassenger(ff2);
        }

        System.out.println("\nОновлений статус та загальний підрахунок");
        System.out.println("Оновлений статус автобуса: " + bus);
        System.out.println("НОВА ЗАГАЛЬНА КІЛЬКІСТЬ ЛЮДЕЙ: " + road.getCountOfHumans());


        System.out.println("\nЕТАП 5: ДОДАТКОВЕ ТЕСТУВАННЯ");

        System.out.println("\nТест 5.1: Таксі може перевозити різні типи пасажирів");
        Firefighter ff3 = new Firefighter("пожежник Олег");
        taxi.boardPassenger(ff3);
        System.out.println("  -> В таксі посаджено пожежника Олега");
        System.out.println("  -> Статус таксі: " + taxi);

        System.out.println("\nТест 5.2: Переповнення таксі (макс 3)");
        Passenger p3 = new Passenger("Марія");
        taxi.boardPassenger(p3);
        System.out.println("  -> В таксі посаджено Марію (3/3)");

        System.out.print("  -> Спроба посадити 4-го пасажира");
        Passenger p4 = new Passenger("Тарас");
        if (taxi.getOccupiedSeats() >= taxi.getMaxCapacity()) {
            System.out.println("\nПОМИЛКА: таксі заповнене. Максимальна місткість: " + taxi.getMaxCapacity());
        } else {
            taxi.boardPassenger(p4);
        }

        System.out.println("\nТест 5.3: Заповнення пожежної машини до максимуму");
        fireTruck.boardPassenger(ff2);
        System.out.println("  -> В пожежну машину посаджено пожежника Надію (2/2)");
        System.out.println("  -> Статус пожежної машини: " + fireTruck);

        System.out.println("\nФІНАЛЬНИЙ СТАТУС ДОРОГИ:");
        System.out.println("Статус автобуса:           " + bus);
        System.out.println("Статус таксі:              " + taxi);
        System.out.println("Статус поліцейської машини: " + policeCar);
        System.out.println("Статус пожежної машини:    " + fireTruck);
        System.out.println("\nФІНАЛЬНА КІЛЬКІСТЬ ЛЮДЕЙ НА ДОРОЗІ: " + road.getCountOfHumans());
    }
}