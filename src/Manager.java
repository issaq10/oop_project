import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager extends User{
    public boolean check_manager(int user_id, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM managers WHERE manager_id = ?");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String user_password = resultSet.getString("password");

                return user_password.equals(password);
            }
            else {
                System.out.println("Incorrect id or password");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String get_name(int id){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT manager_name FROM managers WHERE manager_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("manager_name");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
