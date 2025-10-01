public class Address {
    private final String street;
    private final String house;
    private final String apartment;

    public Address(String street, String house, String apartment) {
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getStreet() { return street; }
    public String getHouse() { return house; }
    public String getApartment() { return apartment; }

    @Override
    public String toString() {
        return street + ", " + house + ", " + apartment;
    }
}

