import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import DomainModel.*;
import BusinessLogic.*;
import ORM.CustomerDAO;
import ORM.ManagerDAO;
import ORM.StructureDAO;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, ClassNotFoundException {
        handleLoginAction();
    }

    public static void handleLoginAction() throws SQLException, ClassNotFoundException, ParseException {

        Scanner scanner = new Scanner(System.in);
        LoginController loginController = new LoginController();

        String input;

        do {

            System.out.println(
                    """
                    \s
                     1. Sign in as Customer
                     2. Sign up as Customer
                     3. Sign in as Manager
                     4. Sign up as Manager
                     5. Exit
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner1 = new Scanner(System.in);

                    System.out.println("\nUsername: ");
                    String username = scanner1.nextLine();
                    System.out.println("Password: ");
                    String password = scanner1.nextLine();

                    Customer customer = (Customer) loginController.login(username, password, "Customer");

                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer concreteCustomer = customerDAO.getUser(username);

                    if (customer != null)
                        handleCustomer(concreteCustomer);

                }
                case "2" -> {

                    String[] data = register();

                    Customer customer = (Customer) loginController.registerCustomer(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6], data[7]);

                    if (customer != null)
                        handleCustomer(customer);
                }
                case "3" -> {

                    Scanner scanner3 = new Scanner(System.in);

                    System.out.println("\nUsername: ");
                    String username = scanner3.nextLine();
                    System.out.println("Password: ");
                    String password = scanner3.nextLine();


                    Manager manager = (Manager) loginController.login(username, password,"Manager");

                    ManagerDAO managerDAO = new ManagerDAO();
                    Manager concreteManager = managerDAO.getUser(username);

                    if (manager != null)
                        handleManager(concreteManager);

                }
                case "4" -> {

                    String[] data = register();

                    Manager manager = (Manager) loginController.registerCustomer(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6], data[7]);

                    if (manager != null)
                        handleManager(manager);
                }
                case "5" -> System.exit(0);
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleCustomer(Customer customer) throws SQLException, ClassNotFoundException, ParseException {

        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     USER PAGE
                     1. Browse offers
                     2. User profile: view and edit your profile
                     3. Log out
                     4. Delete profile
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleCustomerAction(customer);
                case "2" -> handleCustomerProfileAction(customer);
                case "3" -> { break label; }
                case "4" -> {CustomerProfileController customerProfileController = new CustomerProfileController(customer);
                    customerProfileController.deleteProfile(customer.getUsername());}
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleCustomerProfileAction(Customer customer) throws SQLException{

        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     USER PROFILE
                     1. View your profile
                     2. Edit your profile
                     3. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> viewCustomerProfile(customer);
                case "2" -> handleProfileEditorCustomerAction(customer);
                case "3" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileEditorCustomerAction(Customer customer) throws SQLException{

        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     PROFILE EDITOR
                     1. Personal data
                     2. Login data
                     3. Payment data
                     4. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleProfileEditorCustomerDataAction(customer);
                case "2" -> handleProfileCustomerLoginAction(customer);
                case "3" -> handleProfileEditorPaymentDataAction(customer);
                case "4" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileEditorCustomerDataAction(Customer customer) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        CustomerProfileController customerProfileController = new CustomerProfileController(customer);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     PERSONAL DATA
                     1. Update name
                     2. Update surname
                     3. Update age
                     4. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner1 = new Scanner(System.in);

                    System.out.println("\nNew Name: ");
                    String newName = scanner1.nextLine();

                    customerProfileController.updateName(newName);

                }
                case "2" -> {

                    Scanner scanner2 = new Scanner(System.in);

                    System.out.println("\nNew Surname: ");
                    String newSurname = scanner2.nextLine();

                    customerProfileController.updateSurname(newSurname);

                }
                case "3" -> {

                    Scanner scanner3 = new Scanner(System.in);

                    System.out.println("\nNew Age: ");
                    int newAge = scanner3.nextInt();

                    customerProfileController.updateAge(newAge);

                }
                case "4" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileCustomerLoginAction(Customer customer) throws SQLException{

        Scanner scanner = new Scanner(System.in);
        CustomerProfileController customerProfileController = new CustomerProfileController(customer);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     LOGIN DATA
                     1. Update username
                     2. Update password
                     3. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner1 = new Scanner(System.in);

                    ArrayList<String> usernames = customerProfileController.getAllUsernames();

                    String newUsername;

                    do {

                        System.out.println("\nNew Username: ");
                        newUsername = scanner1.nextLine();

                        if (newUsername == null || newUsername.isEmpty()) {
                            System.out.println("Username cannot be null or empty.");
                            continue;
                        }

                        if (usernames.contains(newUsername)) {
                            System.out.println("Username already exists.");
                        }

                    } while (usernames.contains(newUsername));

                    customerProfileController.updateUsername(newUsername);

                }
                case "2" -> {

                    Scanner scanner3 = new Scanner(System.in);

                    String newPassword;

                    System.out.println();

                    do {

                        System.out.println("New Password: ");
                        newPassword = scanner3.nextLine();

                        if (newPassword == null || newPassword.isEmpty()) {
                            System.out.println("Password cannot be null or empty.");
                        }

                    } while (newPassword == null || newPassword.isEmpty());

                    customerProfileController.updatePassword(newPassword);

                }
                case "3" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileEditorPaymentDataAction(Customer customer) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CustomerProfileController userProfileController = new CustomerProfileController(customer);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     PAYMENT DATA
                     1. Update the credit card
                     2. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner4 = new Scanner(System.in);

                    System.out.println();
                    System.out.println("Card Number: ");
                    String cardNumber = scanner4.nextLine();
                    System.out.println("Card Expiration Date: ");
                    String cardExpirationDate = scanner4.nextLine();
                    System.out.println("Card Security Code: ");
                    String cardSecurityCode = scanner4.nextLine();

                    userProfileController.updateCreditCard(cardNumber, cardExpirationDate, cardSecurityCode);

                }
                case "2" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleCustomerAction(Customer customer) throws SQLException, ClassNotFoundException, ParseException {

        Scanner scanner = new Scanner(System.in);
        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     BROWSE PAGE
                     1. Find room
                     2. Show your bookings
                     3. Remove booking
                     4. Add review
                     5. Show reviews
                     6. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner2 = new Scanner(System.in);
                    CustomerProfileController customerProfileController1 = new CustomerProfileController(customer);
                    System.out.println("Insert city:");
                    String City = scanner2.nextLine();
                    System.out.println("Insert bed number:");
                    int beds = scanner2.nextInt();
                    System.out.println("Insert type of structure:");
                    String type = scanner2.nextLine();
                    System.out.println("Insert starting date:");

                    System.out.print("Inserisci l'anno: ");
                    int year = scanner2.nextInt();

                    System.out.print("Inserisci il mese (1-12): ");
                    int month = scanner2.nextInt();

                    System.out.print("Inserisci il giorno del mese: ");
                    int dayOfMonth = scanner2.nextInt();

                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.set(year, month - 1, dayOfMonth);

                    System.out.println("Insert ending date:");

                    System.out.print("Inserisci l'anno: ");
                    int year2 = scanner2.nextInt();

                    System.out.print("Inserisci il mese (1-12): ");
                    int month2 = scanner2.nextInt();

                    System.out.print("Inserisci il giorno del mese: ");
                    int dayOfMonth2 = scanner2.nextInt();

                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.set(year2, month2 - 1, dayOfMonth2);

                    ArrayList<Integer> ids = customerProfileController1.findRoom(City, beds, calendarStart, calendarEnd, type);

                    ArrayList<Room> rooms = new ArrayList<>();
                    for(int index : ids){
                        rooms.add(customerProfileController1.Associate(index));
                    }

                    for(Room room : rooms){
                        System.out.println(room.getId() + "| Price:" + room.getCost() + "| Beds" + room.getSpace() + "| Hotel" + room.getH());
                    }

                    System.out.println("Do you want to book one of this room?");
                    String answer = scanner2.nextLine();
                    if(answer.equals("Yes") || answer.equals("yes")){
                        System.out.println("Insert room Id:");
                        int rId = scanner2.nextInt();
                        customerProfileController1.bookRoom(rId, calendarStart, calendarEnd, customer.getUsername());
                    }


                }
                case "2" -> {
                    ArrayList<Booking> bks = customer.getBks();
                    if (bks.isEmpty()){
                        System.out.println("You have no bookings.");
                    }
                    else {
                        for (Booking b : bks) {
                            System.out.println("Hotel: " + b.getH() + "Period: " + b.getP().getStart() + "/" + b.getP().getEnd());
                        }
                    }
                }
                case "3" -> {
                    CustomerProfileController customerProfileController1 = new CustomerProfileController(customer);
                    Scanner scanner1 =new Scanner(System.in);
                    String u_name = customer.getUsername();
                    System.out.println("\nInsert structure name:");
                    String s_name =scanner1.nextLine();
                    System.out.println("\nInsert period:");
                    String period = scanner1.nextLine();
                    customerProfileController1.removeBooking(u_name, s_name, period);
                }
                case "4" -> {
                    CustomerProfileController customerProfileController1 = new CustomerProfileController(customer);
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Insert Structure Name:");
                    String s_name = scanner1.nextLine();
                    System.out.println("Insert text:");
                    String txt = scanner1.nextLine();
                    System.out.println("Insert score:");
                    int score = scanner1.nextInt();
                    customerProfileController1.addReview(s_name, customer.getUsername(), txt, score);
                }
                case "5" -> {
                    CustomerProfileController customerProfileController1 = new CustomerProfileController(customer);
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Insert user_name:");
                    String Un = scanner1.nextLine();
                    System.out.println("Insert Structure name:");
                    String Hn = scanner1.nextLine();
                    Review r = customerProfileController1.getSingleReview(Un, Hn);
                    if(r != null) {
                        System.out.println("Avg user:" + customerProfileController1.avgUser(Un) + "| Avg Structure" + customerProfileController1.avgHotel(Hn));
                        System.out.println(r.getScore() + "|" + r.getText());
                    }
                    else {
                        System.out.println("No reviews available for this customer and this structure.");
                    }
                }
                case "6" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleManager(Manager manager) throws SQLException, ClassNotFoundException{

        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     USER PAGE
                     1. Browse your Structures
                     2. User profile: view and edit your profile
                     3. Log out
                     4. Delete profile
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleManagerAction(manager);
                case "2" -> handleMangerProfileAction(manager);
                case "3" -> { break label; }
                case "4" ->{ManagerProfileController managerProfileController = new ManagerProfileController(manager);
                            managerProfileController.deleteProfile(manager.getUsername());}
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);
    }

    public static void handleManagerAction(Manager manager) throws SQLException, ClassNotFoundException{
        Scanner scanner = new Scanner(System.in);
        ManagerProfileController managerProfileController = new ManagerProfileController(manager);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     ACTION
                     1. Add Structure
                     2. Remove Structure
                     3. Show reviews
                     4. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    StructureDAO structureDAO = new StructureDAO();
                    String ManagerName = manager.getName();
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Insert Structure Name: ");
                    String StructureName = scanner1.nextLine();
                    System.out.println("Insert Structure Place: ");
                    String StructurePlace = scanner1.nextLine();
                    System.out.println("Insert Structure type: ");
                    String StructureType = scanner1.nextLine();
                    System.out.println("Insert single bed cost:");
                    int cost = scanner1.nextInt();
                    System.out.println("Insert the number of room sizes:");
                    int sizes = scanner1.nextInt();
                    int [] r = new int[sizes];
                    int [] s = new int [sizes];
                    for(int i=0;i<sizes;i++){
                        System.out.println("Insert size:");
                        r[i] = scanner1.nextInt();
                        System.out.println("Insert number of rooms with that size:");
                        s[i] = scanner1.nextInt();
                    }
                    structureDAO.addStructure(StructureName, StructurePlace, ManagerName, StructureType, r, s, cost);
                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Insert name of the structure:");
                    String Name = scanner1.nextLine();
                    managerProfileController.removeStructure(Name);
                }
                case "3" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Insert user_name:");
                    String Un = scanner1.nextLine();
                    System.out.println("Insert Structure name:");
                    String Hn = scanner1.nextLine();
                    Review r = managerProfileController.getSingleReview(Un, Hn);
                    if(r != null) {
                        System.out.println("Avg Structure" + managerProfileController.avgHotel(Hn));
                        System.out.println(r.getScore() + "|" + r.getText());
                    }
                    else{
                        System.out.println("No reviews available for this customer and this structure.");
                    }
                }
                case "4" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);
    }

    public static void handleMangerProfileAction(Manager manager) throws SQLException{
        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     USER PROFILE
                     1. View your profile
                     2. Edit your profile
                     3. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> viewManagerProfile(manager);
                case "2" -> handleProfileEditorManagerAction(manager);
                case "3" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);
    }

    public static void handleProfileEditorManagerAction(Manager manager) throws SQLException{
        Scanner scanner = new Scanner(System.in);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     PROFILE EDITOR
                     1. Personal data
                     2. Login data
                     3. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleProfileEditorManagerDataAction(manager);
                case "2" -> handleProfileManagerLoginAction(manager);
                case "3" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileEditorManagerDataAction(Manager manager) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        ManagerProfileController managerProfileController = new ManagerProfileController(manager);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     PERSONAL DATA
                     1. Update name
                     2. Update surname
                     3. Update age
                     4. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner1 = new Scanner(System.in);

                    System.out.println("\nNew Name: ");
                    String newName = scanner1.nextLine();

                    managerProfileController.updateName(newName);

                }
                case "2" -> {

                    Scanner scanner2 = new Scanner(System.in);

                    System.out.println("\nNew Surname: ");
                    String newSurname = scanner2.nextLine();

                    managerProfileController.updateSurname(newSurname);

                }
                case "3" -> {

                    Scanner scanner3 = new Scanner(System.in);

                    System.out.println("\nNew Age: ");
                    int newAge = scanner3.nextInt();

                    managerProfileController.updateAge(newAge);

                }
                case "4" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);

    }

    public static void handleProfileManagerLoginAction(Manager manager) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        ManagerProfileController managerProfileController = new ManagerProfileController(manager);

        String input;

        label:
        do {

            System.out.println(
                    """
                    \s
                     LOGIN DATA
                     1. Update username
                     2. Update password
                     3. Go back
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Scanner scanner1 = new Scanner(System.in);

                    ArrayList<String> usernames = managerProfileController.getAllUsernames();

                    String newUsername;

                    do {

                        System.out.println("\nNew Username: ");
                        newUsername = scanner1.nextLine();

                        if (newUsername == null || newUsername.isEmpty()) {
                            System.out.println("Username cannot be null or empty.");
                            continue;
                        }

                        if (usernames.contains(newUsername)) {
                            System.out.println("Username already exists.");
                        }

                    } while (usernames.contains(newUsername));

                    managerProfileController.updateUsername(newUsername);

                }
                case "2" -> {

                    Scanner scanner3 = new Scanner(System.in);

                    String newPassword;

                    System.out.println();

                    do {

                        System.out.println("New Password: ");
                        newPassword = scanner3.nextLine();

                        if (newPassword == null || newPassword.isEmpty()) {
                            System.out.println("Password cannot be null or empty.");
                        }

                    } while (newPassword == null || newPassword.isEmpty());

                    managerProfileController.updatePassword(newPassword);

                }
                case "3" -> { break label; }
                default -> System.out.println("Invalid input. Please try again.");
            }

        } while (true);
    }

    private static String[] register() {

        Scanner scanner2 = new Scanner(System.in);

        // personal data
        System.out.println("\nWelcome! Please provide the following information to register:");
        System.out.println("Name: ");
        String name = scanner2.nextLine();
        System.out.println("Surname: ");
        String surname = scanner2.nextLine();
        System.out.println("Age: ");
        int age;
        String ageString = scanner2.nextLine();
        if (ageString.isEmpty()) {
            age = 0;
        } else {
            age = Integer.parseInt(ageString);
        }

        // login data
        String username, password;
        do {
            System.out.println("\u001B[3m" + "Username and Password are required fields." + "\u001B[0m");
            System.out.println("Username: ");
            username = scanner2.nextLine();
            System.out.println("Password: ");
            password = scanner2.nextLine();
        } while (username == null || username.isEmpty() || password == null || password.isEmpty());

        // payment data
        System.out.println("Are you a Customer? ");
        String answer = scanner2.nextLine();
        String cardNumberORuniqueCode, cardExpirationDateORaccountEmail, cardSecurityCodeORaccountPassword;
        if (answer.equals("Yes") || answer.equals("yes") || answer.equals("y")) {
            System.out.println("Card Number: ");
            cardNumberORuniqueCode = scanner2.nextLine();
            System.out.println("Card Expiration Date: ");
            cardExpirationDateORaccountEmail = scanner2.nextLine();
            System.out.println("Card Security Code: ");
            cardSecurityCodeORaccountPassword = scanner2.nextLine();
        }else {
            return new String[] {name, surname, Integer.toString(age), username, password};
        }

        return new String[] {name, surname, Integer.toString(age), username, password, cardNumberORuniqueCode, cardExpirationDateORaccountEmail, cardSecurityCodeORaccountPassword};

    }

    private static void viewCustomerProfile(Customer customer)  {
        System.out.println("\nName: " + customer.getName());
        System.out.println("Surname: " + customer.getSurname());
        System.out.println("Age: " + customer.getAge());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("CreditCard: " + customer.getCC().getCardNumber());
    }

    private static void viewManagerProfile(Manager manager){
        System.out.println("\nName: " + manager.getName());
        System.out.println("Surname: " + manager.getSurname());
        System.out.println("Age: " + manager.getAge());
        System.out.println("Username: " + manager.getUsername());
        System.out.println("Password: " + manager.getPassword());
    }

}
