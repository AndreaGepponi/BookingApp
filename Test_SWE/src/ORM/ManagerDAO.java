package ORM;

import DomainModel.Booking;
import DomainModel.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ManagerDAO {
    private Connection connection;

    public ManagerDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void addUser(String name, String surname, int age, String username, String password) throws SQLException{
        String Role = "Manager";
        String sql = String.format("INSERT INTO \"User\" (name, surname, age, username, role, password) " +
                        "VALUES ('%s', '%s', '%d', '%s', '%s', '%s')", name, surname, age, username, Role, password);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public void removeUser(String username) throws SQLException, ClassNotFoundException {

        String sql = String.format("DELETE FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;

        // remove Structure (CASCADE DELETE)
        ArrayList<String> Structures = getStructures(username);
        StructureDAO structureDAO = new StructureDAO();

        for(String name : Structures){
            structureDAO.removeStructure(name);
        }


        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("User removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public Manager checkPassword(String Username, String password) throws SQLException{

        Manager manager = null;

        String sql = String.format("SELECT * FROM \"User\" WHERE username = '%s'", Username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int age = resultSet.getInt("age");
                    manager = new Manager(name, surname, age, Username, password);

                } else {
                    System.err.println("Invalid password. Please try again.");
                    return null;
                }
            } else {
                System.err.println("Invalid Username. Please try again."); // FIXME: it asks the password anyway
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return manager;

    }

    public Manager getUser(String username) throws SQLException{

        Manager manager = null;

        String sql = String.format("SELECT * FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String password = resultSet.getString("password");
                manager = new Manager(name, surname, age, username, password);

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return manager;

    }

    public void updateName(String username, String name) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET name = '%s' WHERE username = '%s'", name, username);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Name updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public void updateSurname(String username, String surname) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET surname = '%s' WHERE username = '%s'", surname, username);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Surname updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public void updateAge(String username, int age) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET age = '%d' WHERE username = '%s'", age, username);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Age updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public void updateUsername(String username, String newUsername) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET username = '%s' WHERE username = '%s'", newUsername, username);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Username updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public void updatePassword(String username, String password) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET password = '%s' WHERE username = '%s'", password, username);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Password updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public ArrayList<String> getAllUsernames() throws SQLException{

        ArrayList<String> usernames = new ArrayList<>();

        String sql = String.format("SELECT username FROM \"User\" WHERE role LIKE 'manager' ORDER BY id ASC");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return usernames;
    }

    public ArrayList<String> getStructures(String username) throws SQLException {
        StructureDAO structureDAO = new StructureDAO();
        return structureDAO.getAllStructure(username);
    }

    public ArrayList<Booking> getAllBookings(String username) throws SQLException, ParseException {
        ArrayList<Booking> bookings = new ArrayList<>();
        ArrayList<String> hotels = getStructures(username);

        StructureDAO structureDAO = new StructureDAO();
        for(String name : hotels){
            bookings.addAll(structureDAO.getAllBooking(name));
        }

        return bookings;
    }

    public void removeStructure(String n)throws SQLException, ClassNotFoundException{
        StructureDAO structureDAO = new StructureDAO();
        structureDAO.removeStructure(n);
    }
}
