import java.sql.*;


class Library {
    static final String URL = "jdbc:postgresql://localhost:5432/library";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";
    private Connection connection;


    public Library() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(int id, String name, int age, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO customers (customer_id, customer_name, age, password)" +
                            "VALUES (?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setString(4, password);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("New user was registered");
                System.out.println();
            }
            else {
                System.out.println("Something went wrong");
                System.out.println();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addItem(int id, String name, int year, String author) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO books (book_id, book_name, year_of_publ, author)" +
                            "VALUES (?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, year);
            statement.setString(4, author);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Books was successfully added");
                System.out.println();
            }
            else {
                System.out.println("Something went wrong");
                System.out.println();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id = ?");
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Books was successfully deleted");
                System.out.println();
            }
            else {
                System.out.println("Book not found");
                System.out.println();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void borrowItem(String title, int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement("Select available FROM books WHERE book_name = ?");
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getBoolean("available")) {
                    PreparedStatement statement2 = connection.prepareStatement("UPDATE books SET available = false, customer_id = ? WHERE book_name = ?");
                    statement2.setInt(1, user_id);
                    statement2.setString(2, title);

                    int rowsUpdated = statement2.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("The book successfully borrowed");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("The book is already borrowed");
                    System.out.println();
                }
            }
            else {
                System.out.println("Book not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnItem(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("Select available FROM books WHERE book_name = ?");
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (!resultSet.getBoolean("available")) {
                    PreparedStatement statement2 = connection.prepareStatement("UPDATE books SET available = true, customer_id = null WHERE book_name = ?");
                    statement2.setString(1, title);

                    int rowsUpdated = statement2.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("The book successfully returned");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("The book is already returned");
                    System.out.println();
                }
            }
            else {
                System.out.println("Book not found");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAvailableItems() {
        try {
            if (connection != null){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM books ORDER BY book_id");

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("book_id") + ") '" +
                            resultSet.getString("book_name") + "' - " +
                            resultSet.getString("author") + '(' +
                            resultSet.getInt("year_of_publ") + ')');
                }
                System.out.println();
            }
            else {
                System.out.println("Connection to database is null.");
                System.out.println();
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
