package BusinessLogic;

import DomainModel.User;
import ORM.CustomerDAO;
import ORM.ManagerDAO;

import java.sql.SQLException;

public class LoginController {
    public LoginController() {}

    public User login(String username, String password, String role) throws SQLException, ClassNotFoundException {

        CustomerDAO customerDAO = new CustomerDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        if(role.equals("Customer")) {
            return customerDAO.checkPassword(username, password);
        }
        return managerDAO.checkPassword(username, password);
    }

    public User registerCustomer(String name, String surname, int age, String username, String password, String cardNumber,
                                 String cardExpirationDate, String cardSecurityCode) throws SQLException, ClassNotFoundException {

        CustomerDAO customerDAO = new CustomerDAO();

        customerDAO.addUser(name, surname, age, username, password, cardNumber, cardExpirationDate, cardSecurityCode);

        return customerDAO.checkPassword(username, password);

    }

    public User registerManager(String name, String surname, int age, String username, String password) throws SQLException, ClassNotFoundException {

        ManagerDAO managerDAO = new ManagerDAO();

        managerDAO.addUser(name, surname, age, username, password);

        return managerDAO.checkPassword(username, password);

    }
}
