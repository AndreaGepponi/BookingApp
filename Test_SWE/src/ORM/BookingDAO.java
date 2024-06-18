package ORM;

import DomainModel.Booking;
import DomainModel.BookingMapper;
import DomainModel.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookingDAO {
    private Connection connection;

    public BookingDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public String calendarToString(Calendar calendar) {
        // Definire il formato desiderato
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Convertire l'oggetto Calendar in una stringa
        return sdf.format(calendar.getTime());
    }

    public Calendar[] stringToCalendars(String dateRange) throws ParseException {
        // Definire il formato della data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Suddividere la stringa in base al delimitatore "-"
        String[] dates = dateRange.split("-");

        // Creare i due oggetti Calendar
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        // Impostare le date nei Calendar utilizzando SimpleDateFormat per il parsing
        startDate.setTime(sdf.parse(dates[0]));
        endDate.setTime(sdf.parse(dates[1]));

        // Restituire un array di Calendar
        return new Calendar[] { startDate, endDate };
    }

    public void addBooking(String Un, String Hn, String P, int id){
        String sql = String.format("INSERT INTO \"Booking\"(users, hotel, period, bkid) "+
                "VALUES('%s','%s','%s','%d')", Un, Hn, P, id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Booking added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeSingleBooking(String Un, String Hn, String P) throws SQLException, ClassNotFoundException{
        String sql = String.format("DELETE FROM \"Booking\" WHERE users = '%s' AND hotel = '%s' AND period = '%s'", Un, Hn, P);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Booking removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeUserBooking(String Un) {
        String sql = String.format("DELETE FROM \"Booking\" WHERE users = '%s'", Un);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Booking removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeHotelBooking(String Hn) {
        String sql = String.format("DELETE FROM \"Booking\" WHERE hotel = '%s'", Hn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Booking removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Booking getBooking(String Un, String Hn, String P) throws SQLException, ClassNotFoundException, ParseException {
        Booking booking = null;
        String sql = String.format("SELECT * FROM\"Booking\" WHERE users = '%s' AND  hotel = '%s' AND period = '%s'", Un, Hn, P);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Calendar [] calendars = stringToCalendars(P);

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CustomerDAO customerDAO = new CustomerDAO();
                BookingMapper bookingMapper = new BookingMapper();
                Customer concreteCustomer = customerDAO.getUser(resultSet.getString("users"));
                booking = bookingMapper.map(concreteCustomer, calendars[0], calendars[1], resultSet.getString("hotel"), resultSet.getInt("bkid"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return booking;
    }

    public ArrayList<Booking> getAllUserBookings(String Un) throws SQLException, ParseException{
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = String.format("SELECT * FROM\"Booking\" WHERE users = '%s'", Un);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer concreteCustomer = customerDAO.getUser(resultSet.getString("users"));
                Calendar [] calendars = stringToCalendars(resultSet.getString("period"));
                bookings.add(new Booking(concreteCustomer,calendars[0], calendars[1], resultSet.getString("hotel"), resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return bookings;
    }

    public ArrayList<Booking> getAllHotelBookings(String Hn) throws SQLException, ParseException{
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = String.format("SELECT * FROM\"Booking\" WHERE hotel = '%s'", Hn);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer concreteCustomer = customerDAO.getUser(resultSet.getString("users"));
                Calendar [] calendars = stringToCalendars(resultSet.getString("period"));
                bookings.add(new Booking(concreteCustomer,calendars[0], calendars[1], resultSet.getString("hotel"), resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return bookings;
    }

    public ArrayList<Booking> getAllRoomBookings(int id) throws SQLException, ParseException{
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = String.format("SELECT * FROM\"Booking\" WHERE bkid = '%s'", id);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer concreteCustomer = customerDAO.getUser(resultSet.getString("users"));
                Calendar [] calendars = stringToCalendars(resultSet.getString("period"));
                bookings.add(new Booking(concreteCustomer,calendars[0], calendars[1], resultSet.getString("hotel"), resultSet.getInt("bkid")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return bookings;
    }
}
