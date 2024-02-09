import java.sql.*;

public class User {
    static final String URL = "jdbc:postgresql://localhost:5432/library";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";
    protected Connection connection;

    public User() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
