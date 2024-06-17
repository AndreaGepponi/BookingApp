package ORM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String url = "jdbc:postgresql://localhost:5432/Test";
    private static final String username = "postgres";
    private static final String password = "TfaTljRos";
    private static Connection connection = null;

    // singleton instance
    private static ConnectionManager instance = null;

    private ConnectionManager(){}

    public static ConnectionManager getInstance() {

        if (instance == null) { instance = new ConnectionManager(); }

        return instance;

    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        if (connection == null)
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }

        return connection;

    }

}