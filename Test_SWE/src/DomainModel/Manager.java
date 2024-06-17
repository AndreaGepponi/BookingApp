package DomainModel;

import java.util.ArrayList;

public class Manager extends User{
    private String Name, Surname, password;
    private int age;
    public String Username;
    private ArrayList<Structure> Hotels;

    public Manager(String name, String surname, int age, String username, String password){
        setName(name);
        setSurname(surname);
        setAge(age);
        setUsername(username);
        setPassword(password);
        Hotels = new ArrayList<>();
    }

    public ArrayList<Structure> getHotels() {
        return Hotels;
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

    public void updatePassword(String newPassword) {
        setPassword(newPassword);
    }

    public void updateUsername(String newUsername) {
        Username = newUsername;
    }
}
