package ORM;

import DomainModel.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class    RoomDAO {

    private static Connection connection;

    public RoomDAO(){
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addRoom(String Hn, int c, int space) throws SQLException {
        String sql = String.format("INSERT INTO\"Room\" (hotel , price, bed)" + "VALUES('%s', '%d', '%d')", Hn, c, space);

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Room added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
        }
    }

    public String getHotel(int Id) throws SQLException {
        String sql = String.format("SELECT hotel FROM \"Room\" WHERE  \"id\" = '%s'", Id);

        String hotel = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hotel = resultSet.getString("hotel");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }
        return hotel;
    }

    public int getPrice(int Id) throws SQLException{
        String sql = String.format("SELECT price FROM \"Room\" WHERE  \"id\" = '%s'", Id);

        int price = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getInt("price");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }
        return price;
    }

    public int getBeds(int Id) throws SQLException{
        String sql = String.format("SELECT bed FROM \"Room\" WHERE  \"id\" = '%s'", Id);

        int bed = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bed = resultSet.getInt("bed");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }
        return bed;
    }

    public void removeHotelRooms(String Hn) {
        String sql = String.format("DELETE FROM \"Room\" WHERE hotel = '%s'",Hn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Rooms removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeSingleRoom(int id){
        String sql = String.format("DELETE FROM \"Room\" WHERE id = '%s'",id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Room removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Room> getAllHotelRooms(String Hn) throws SQLException{
        ArrayList<Room> rooms = new ArrayList<>();

        String sql = String.format("SELECT * FROM \"Room\" WHERE hotel = '%s'", Hn);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               rooms.add(new Room(resultSet.getInt("bed"), resultSet.getInt("id"), resultSet.getInt("price"), resultSet.getString("hotel")));
            }
        } catch (SQLException e) {
            System.err.println("ErrorEEE: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return rooms;
    }

    public int getlastid() throws SQLException{
        String sql = String.format("SELECT id FROM \"Room\"");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.isLast()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }
        return id;
    }
}
