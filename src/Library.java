import javax.swing.*;
import java.awt.*;
import java.sql.*;


class Library {
    static final String URL = "jdbc:postgresql://localhost:5432/library";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";
    private Connection connection;
    private static Library instance;


    private Library() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Library getInstance(){
        if (instance == null){
            synchronized (Library.class){
                if (instance == null){
                    instance = new Library();
                }
            }
        }
        return instance;
    }

    public void addUser(JFrame frame, int id, String name, int age, String password) {
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
                JOptionPane.showMessageDialog(frame, "New user was registered");
            }
            else {
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addItem(JFrame frame, int id, String name, int year, String author) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO books (book_id, book_name, year_of_publ, author, available)" +
                            "VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, year);
            statement.setString(4, author);
            statement.setBoolean(5, true);


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Books was successfully added!");
            }
            else {
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(JFrame frame, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id = ?");
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Books was successfully deleted!");

            }
            else {
                JOptionPane.showMessageDialog(frame, "Book not Found!");

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void borrowItem(JFrame frame, String title, int user_id) {
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
                        JOptionPane.showMessageDialog(frame, "You borrowed a book, do not forget to return!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frame, "The book is already borrowed!");
                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "Book not found");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnItem(JFrame frame, String title) {
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
                        JOptionPane.showMessageDialog(frame, "The book successfully returned");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frame, "The book is already returned");

                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "Book not found");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAvailableItems() {
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setText(""); // Clear previous content

        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE available = true ORDER BY book_id");

                while (resultSet.next()) {
                    textArea.append(resultSet.getInt("book_id") + ") '" +
                            resultSet.getString("book_name") + "' - " +
                            resultSet.getString("author") + " (" +
                            resultSet.getInt("year_of_publ") + ")\n");
                }
                textArea.append("\n");
            } else {
                textArea.append("Connection to database is null.\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.add(textArea);
        frame.add(panel);
        frame.setVisible(true);
    }

}