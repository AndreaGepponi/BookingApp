package BusinessLogic;

import DomainModel.Booking;
import DomainModel.Customer;
import ORM.BookingDAO;
import ORM.CustomerDAO;
import ORM.ReviewDAO;
import ORM.RoomDAO;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void BookingOperationsTest() throws SQLException, ClassNotFoundException , ParseException {
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.set(2025,Calendar.JANUARY,1);
        End.set(2025,Calendar.JANUARY,10);

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getUser("mario.rossi");
        CustomerProfileController customerProfileController = new CustomerProfileController(customer);

        BookingDAO bookingDAO = new BookingDAO();

        String s = bookingDAO.calendarToString(Start);
        String e = bookingDAO.calendarToString(End);
        String tot = s + "-" + e;


        try {
            customerProfileController.bookRoom(1, Start, End, "mario.rossi");
            Booking b = bookingDAO.getBooking("mario.rossi", "Hotel_Roma", tot);
            assertNotNull(b);
            bookingDAO.removeSingleBooking("mario.rossi", "Hotel_Roma", tot);
            Booking c = bookingDAO.getBooking("mario.rossi", "Hotel_Roma", tot);
            assertNull(c);
        } catch (SQLException | ClassNotFoundException E) {
            System.err.println(E.getMessage());
        }
    }

    @Test
    public void findRoomTest() throws SQLException, ClassNotFoundException, ParseException{
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.set(2025,Calendar.JANUARY,1);
        End.set(2025,Calendar.JANUARY,10);

        CustomerDAO customerDAO = new CustomerDAO();
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.addRoom("Hotel_Roma", 400, 10);
        Customer customer = customerDAO.getUser("mario.rossi");
        CustomerProfileController customerProfileController = new CustomerProfileController(customer);

        try {
            ArrayList<Integer> rooms = customerProfileController.findRoom("Roma", 10, Start, End, "Hotel");
            assertEquals(rooms.size(), 1, 0);
            roomDAO.removeSingleRoom(roomDAO.getlastid());
        } catch (SQLException | ClassNotFoundException E) {
            System.err.println(E.getMessage());
        }
    }

    @Test
    public void getReviewsTest() throws SQLException, ClassNotFoundException{
        CustomerDAO customerDAO = new CustomerDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        Customer customer = customerDAO.getUser("mario.rossi");
        CustomerProfileController customerProfileController = new CustomerProfileController(customer);

        try {
            reviewDAO.removeUserReview("mario.rossi");
            reviewDAO.removeHotelReview("Hotel_Roma");
            customerProfileController.addReview("Hotel_Roma", "mario.rossi", "text", 3);
            float avgH = customerProfileController.avgHotel("Hotel_Roma");
            float avgU = customerProfileController.avgUser("mario.rossi");
            assertEquals(avgU, 3, 0);
            assertEquals(avgH, 3, 0);
            customerProfileController.removeReview("mario.rossi", "Hotel_Roma");
        } catch (SQLException | ClassNotFoundException E) {
            System.err.println(E.getMessage());
        }
    }
}
