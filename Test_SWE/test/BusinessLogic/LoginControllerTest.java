package BusinessLogic;

import DomainModel.User;
import ORM.CustomerDAO;
import ORM.ManagerDAO;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginControllerTest {
    @Test
    public void loginTest(){

        // Test case 1: this is a known user in the database
        String username = "mario.rossi";
        String password = "Password1";

        LoginController loginController = new LoginController();

        try {
            User user = loginController.login(username, password,"Customer");
            assertNotNull(user);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        // Test case 2: this is not a known user in the database
        String username2 = "sergio.bianchi";
        String password2 = "Password2";

        try {
            User user2 = loginController.login(username2, password2, "Customer");
            assertNull(user2);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void registerCustomerTest(){

        String name = "John";
        String surname = "Doe";
        int age = 30;
        String username = "john.doe";
        String password = "Password1";
        String cardNumber = "43443430";
        String cardExpirationDate = "2030-01-01";
        String cardSecurityCode = "123";

        LoginController loginController = new LoginController();
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            User user = loginController.registerCustomer(name, surname, age, username,
                    password, cardNumber, cardExpirationDate, cardSecurityCode);
            assertNotNull(user);
            customerDAO.removeUser(username);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void registerManagerTest(){
        String name = "Name";
        String surname = "Surname";
        int age = 40;
        String username = "UserName";
        String password = "Password";

        LoginController loginController = new LoginController();
        ManagerDAO managerDAO = new ManagerDAO();

        try {
            User user = loginController.registerManager(name, surname, age, username, password);
            assertNotNull(user);
            managerDAO.removeUser(username);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
