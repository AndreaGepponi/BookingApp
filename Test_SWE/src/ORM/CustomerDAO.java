package ORM;


import DomainModel.CreditCard;
import DomainModel.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void addUser(String name, String surname, int age, String username, String password, String cardNumber,
                        String cardExpirationDate, String cardSecurityCode) throws SQLException  {
        String Role = "Customer";
        String sql = "";
            CreditCardDAO creditCardDAO = new CreditCardDAO();
            creditCardDAO.addCreditCard(cardNumber, cardExpirationDate, cardSecurityCode);
            sql = String.format("INSERT INTO \"User\" (name, surname, age, username, role, password, creditCard) " +
                            "VALUES ('%s', '%s', '%d', '%s', '%s', '%s', '%s')",
                    name, surname, age, username, Role,password, cardNumber);

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

    public void removeUser(String username) throws SQLException, ClassNotFoundException     {

        String sql = String.format("DELETE FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;

        // remove CreditCard method (CASCADE DELETE)
            removeCreditCard(username);
        // remove booking (CASCADE DELETE)
            removeBooking(username);
        // remove review (CASCADE DELETE)
            removeReview(username);

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

    public void removeCreditCard(String username) throws SQLException{
        String sql;

        PreparedStatement preparedStatement = null;

        try {
            Customer customer = getUser(username);
                CreditCardDAO creditCardDAO = new CreditCardDAO();
                String cardNumber = getCardNumber(username);
                sql = String.format("UPDATE \"User\" SET creditCard = NULL WHERE username = '%s'", username);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                creditCardDAO.removeCreditCard(cardNumber);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }
    }

    public void removeBooking(String username) {
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.removeUserBooking(username);
    }

    public void removeReview(String username) {
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.removeUserReview(username);
    }

    public Customer checkPassword(String username, String password) throws SQLException {

        Customer customer = null;

        String sql = String.format("SELECT * FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int age = resultSet.getInt("age");
                    if (resultSet.getString("creditCard") != null) {
                        CreditCard creditCard_tmp = new CreditCardDAO().getCreditCard(resultSet.getString("creditCard"));
                        customer = new Customer(id, name, surname, age, username, password, "CreditCard", creditCard_tmp.getCardNumber(), creditCard_tmp.getCardExpirationDate(), creditCard_tmp.getCardSecurityCode());
                    }
                } else {
                    System.err.println("Invalid password. Please try again.");
                    return null;
                }
            } else {
                System.err.println("Invalid username. Please try again."); // FIXME: it asks the password anyway
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return customer;

    }

    public Customer getUser(String username) throws SQLException {

        Customer customer = null;

        String sql = String.format("SELECT * FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String password = resultSet.getString("password");
                if (resultSet.getString("creditCard") != null) {
                    CreditCard creditCard_tmp = new CreditCardDAO().getCreditCard(resultSet.getString("creditCard"));
                    customer = new Customer(id, name, surname, age, username, password, "CreditCard", creditCard_tmp.getCardNumber(), creditCard_tmp.getCardExpirationDate(), creditCard_tmp.getCardSecurityCode());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return customer;

    }

    public String[] getPersonalData(String cardNumber) throws SQLException {

        String[] data = new String[2];

        String sql;

        sql = String.format("SELECT * FROM \"User\" WHERE creditCard = '%s'", cardNumber);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data[0] = resultSet.getString("name");
                data[1] = resultSet.getString("surname");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return data;

    }

    public void updateName(String username, String name) throws SQLException {

        String sql = String.format("UPDATE \"User\" SET name = '%s' AND WHERE username = '%s'", name, username);

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

    public void updateSurname(String username, String surname) throws SQLException {

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

    public void updateAge(String username, int age) throws SQLException {

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

    public void updateUsername(String username, String newUsername) throws SQLException {

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

    public void updatePassword(String username, String password) throws SQLException {

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

    public void updateCreditCard(String username, String cardNumber, String cardExpirationDate, String cardSecurityCode) throws SQLException{

        String sql = String.format("UPDATE \"User\" SET creditCard = '%s' WHERE username = '%s'", cardNumber, username);

        PreparedStatement preparedStatement = null;

        try {
            CreditCardDAO creditCardDAO = new CreditCardDAO();
            creditCardDAO.addCreditCard(cardNumber, cardExpirationDate, cardSecurityCode);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("CreditCard updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

    }

    public ArrayList<String> getAllUsernames() throws SQLException {

        ArrayList<String> usernames = new ArrayList<>();

        String sql = String.format("SELECT username FROM \"User\" ORDER BY id ASC");

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

    private String getCardNumber(String username) throws SQLException {

        String cardNumber = null;

        String sql = String.format("SELECT creditCard FROM \"User\" WHERE username = '%s'", username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cardNumber = resultSet.getString("creditCard");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return cardNumber;

    }

}
