import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends User{
    public boolean check_customer(int user_id, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM customers WHERE customer_id = ?");
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
                    "SELECT customer_name FROM customers WHERE customer_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("customer_name");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
