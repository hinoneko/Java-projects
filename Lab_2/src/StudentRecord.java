import java.time.LocalDate;

public class StudentRecord {
    private final String surname;
    private final String name;
    private final LocalDate birthDate;
    private final String phone;
    private final Address address;

    public StudentRecord(String surname, String name, LocalDate birthDate, String phone, Address address) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }

    @Override
    public String toString() {
        return "Прізвище: " + surname + "\n" +
               "Ім'я: " + name + "\n" +
               "Дата народження: " + birthDate + "\n" +
               "Телефон: " + phone + "\n" +
               "Адреса: " + address;
    }
}

