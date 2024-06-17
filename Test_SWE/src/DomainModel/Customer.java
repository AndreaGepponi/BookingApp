package DomainModel;

import java.util.ArrayList;

public class Customer extends User{
    private String Name, Surname, password;
    private int age;
    private ArrayList<Booking> Bks;
    private CreditCard CC;
    public String Username;

    public Customer(int id, String name, String surname, int age, String username, String password, String paymentMethod, String cardNumberORuniqueCode, String cardExpirationDateORaccountEmail, String cardSecurityCodeORaccountPassword){
        setName(name);
        setSurname(surname);
        setAge(age);
        setUsername(username);
        setPassword(password);
        CreditCard creditCard = new CreditCard(name, surname, cardNumberORuniqueCode, cardExpirationDateORaccountEmail, cardSecurityCodeORaccountPassword);
        setCC(creditCard);
        setBks(new ArrayList<>());
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public ArrayList<Booking> getBks() {
        return Bks;
    }

    public CreditCard getCC() {
        return CC;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setBks(ArrayList<Booking> bks) {
        Bks = bks;
    }

    public void setCC(CreditCard CC) {
        this.CC = CC;
    }

    public void updatePassword(String newPassword) {
        setPassword(newPassword);
    }

    public void updateUsername(String newUsername) {
        Username = newUsername;
    }

}
