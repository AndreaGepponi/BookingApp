package ORM;

import DomainModel.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {
    private Connection connection;

    public ReviewDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addReview(String Hn, String Un, String txt, int score) throws SQLException{
        if(getReview(Un, Hn) != null){
            System.err.println("Can not add more review for this structure.");
            return;
        }

        String sql = String.format("INSERT INTO \"Review\"(score, text, hotel, users) "+
                "VALUES('%s','%s','%s','%s')", score, txt, Hn, Un);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Review added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeReview(String Un, String Hn) {
        String sql = String.format("DELETE FROM \"Review\" WHERE users = '%s' AND hotel = '%s'", Un, Hn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Review removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeUserReview(String Un) {
        String sql = String.format("DELETE FROM \"Review\" WHERE users = '%s'", Un);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Review removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeHotelReview(String Hn) {
        String sql = String.format("DELETE FROM \"Review\" WHERE hotel = '%s'", Hn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Review removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Review getReview(String Un, String Hn) throws SQLException{
        Review review = null;
        String sql = String.format("SELECT * FROM\"Review\" WHERE users = '%s' AND hotel = '%s'", Un, Hn);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                review = new Review(resultSet.getInt("score"), resultSet.getString("text"), resultSet.getString("hotel"), resultSet.getString("user"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return review;
    }

    public ArrayList<Review> getAllUserReviews(String Un) throws SQLException{
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = String.format("SELECT * FROM\"Review\" WHERE users = '%s'", Un);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(new Review(resultSet.getInt("score"), resultSet.getString("text"), resultSet.getString("hotel"), resultSet.getString("users")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return reviews;
    }

    public ArrayList<Review> getAllHotelReview(String Hn) throws SQLException{
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = String.format("SELECT * FROM\"Review\" WHERE hotel = '%s'", Hn);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(new Review(resultSet.getInt("score"), resultSet.getString("text"), resultSet.getString("hotel"), resultSet.getString("users")));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        }

        return reviews;
    }

}
