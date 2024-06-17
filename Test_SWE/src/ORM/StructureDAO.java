package ORM;

import DomainModel.Booking;
import DomainModel.Manager;
import DomainModel.Room;
import DomainModel.Structure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class StructureDAO {

    private static Connection connection;
    public StructureDAO(){
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addStructure(String name, String place, String Manager, String type, int[]r, int []s, int c) throws SQLException{
        String sql = String.format("INSERT INTO \"Structure\" (name, place, manager, type, rooms)" +
                "VALUES ('%s', '%s', '%s', '%s', '%d')", name, place, Manager, type, Arrays.stream(r).sum());
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Structure added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }

        RoomDAO roomDAO = new RoomDAO();

        for(int i=0;i<r.length;i++){
            for(int j=0;j<s[i];j++){
                roomDAO.addRoom(name, c*s[i], s[i]);
            }
        }

    }

    public void removeStructure(String name) throws SQLException, ClassNotFoundException{
        String sql = String.format("DELETE FROM \"Structure\" WHERE name = '%s'", name);

        PreparedStatement preparedStatement = null;

        // remove Booking (CASCADE DELETE)
        removeBooking(name);
        // remove Room (CASCADE DELETE)
        removeRoom(name);
        // remove Review (CASCADE DELETE)
        removeReview(name);

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Structure removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }
    }

    public void removeBooking(String name) {
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.removeHotelBooking(name);
    }

    public void removeRoom(String name) {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.removeHotelRooms(name);
    }

    public void removeReview(String name) {
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.removeHotelReview(name);
    }

    public ArrayList<Integer> findVacant(String City, int space, Calendar S, Calendar E, String Type) throws SQLException, ParseException {
        ArrayList<String> hotels = new ArrayList<>();

        String sql = String.format("SELECT * FROM \"Structure\" WHERE place = '%s' AND type = '%s'", City, Type);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hotels.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        ArrayList<Integer> rooms = new ArrayList<>();

        for (String hotel : hotels) {
            String r_sql = String.format("SELECT * FROM \"Room\" WHERE hotel = '%s' AND bed >= '%d'", hotel, space);
            PreparedStatement room_preparedStatement = null;
            ResultSet roomSet = null;

            try {
                room_preparedStatement = connection.prepareStatement(r_sql);
                roomSet = room_preparedStatement.executeQuery();
                if (roomSet.next()) {
                    rooms.add(roomSet.getInt("id"));
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                if (room_preparedStatement != null) {
                    room_preparedStatement.close();
                }
                if (roomSet != null) {
                    roomSet.close();
                }
            }
        }


        BookingDAO bookingDAO = new BookingDAO();
        ArrayList<Booking> bks = new ArrayList<>();

        ArrayList<Integer> available = new ArrayList<>();

        for (Integer room : rooms) {
            bks.addAll(bookingDAO.getAllRoomBookings(room));

            boolean accept = true;

            for(Booking B : bks){
                if(S.before(B.getP().getEnd()) || E.after(B.getP().getStart()))
                    accept = false;
            }

            if(accept){
                available.add(room);
            }

            bks.clear();
        }

        return available;
    }

    public ArrayList<String> getAllStructure(String username) throws SQLException {
        ArrayList<String> hotels = new ArrayList<>();

        String sql = String.format("SELECT name FROM \"Structure\" WHERE manager = '%s'", username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hotels.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return hotels;
    }

    public Structure getStructure(String Mn, String Hn) throws SQLException, ClassNotFoundException {
        String sql = String.format("SELECT * FROM \"Structure\" WHERE manager = '%s' AND name = '%s'", Mn, Hn);

        ManagerDAO managerDAO = new ManagerDAO();
        Manager manager = managerDAO.getUser(Mn);

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> rooms = roomDAO.getAllHotelRooms(Hn);



        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Structure(resultSet.getString("name"), manager.getUsername(), resultSet.getString("place"),
                        rooms.size(), resultSet.getString("type"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }
        Structure hotel = null;
        return hotel;
    }

    public ArrayList<Booking> getAllBooking(String hotel) throws SQLException, ParseException {
        BookingDAO bookingDAO = new BookingDAO();
        return bookingDAO.getAllHotelBookings(hotel);
    }
}
