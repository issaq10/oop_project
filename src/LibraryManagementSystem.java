import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class LibraryManagementSystem {
    public static void main(String[] args) {

        Library library = Library.getInstance();
        Scanner scanner = new Scanner(System.in);
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(5, 1));


        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JButton managerButton = new JButton("Sign in as manager");
        JButton userButton = new JButton("Sign in as user");
        JButton signUpButton = new JButton("Sign up");
        JButton exitButton = new JButton("Exit");

        frame.add(titleLabel);
        frame.add(managerButton);
        frame.add(userButton);
        frame.add(signUpButton);
        frame.add(exitButton);

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.setLayout(new GridLayout(1, 1));
                JPanel managerPanel = new JPanel(new GridLayout(3, 2));

                JLabel userIdLabel = new JLabel("Enter your id:");
                JTextField userIdField = new JTextField();
                JLabel passwordLabel = new JLabel("Enter your password:");
                JPasswordField passwordField = new JPasswordField();
                JButton signin = new JButton("Sign In");

                signin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Manager manager = new Manager();
                        int userId = Integer.parseInt(userIdField.getText());
                        String password = new String(passwordField.getPassword());

                        if (manager.check_manager(userId, password)) {
                            frame.dispose();
                            JOptionPane.showMessageDialog(null, "Sign In Successful!");

                            JFrame managerFrame = new JFrame("Manager Menu");
                            managerFrame.setSize(400, 300);
                            managerFrame.setLayout(new GridLayout(2, 2));

                            JButton displayBooksButton = new JButton("Display Available Books");
                            JButton addBookButton = new JButton("Add a Book");
                            JButton removeBookButton = new JButton("Remove a Book");
                            JButton exitButton = new JButton("Exit");

                            displayBooksButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    library.displayAvailableItems();
                                }
                            });

                            addBookButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String bookIdString = JOptionPane.showInputDialog(frame, "Enter book ID:");
                                    String title = JOptionPane.showInputDialog(frame, "Enter the title of the new book:");
                                    String yearString = JOptionPane.showInputDialog(frame, "Enter the year of publication:");
                                    String author = JOptionPane.showInputDialog(frame, "Enter the author of the new book:");

                                    try {

                                        int bookId = Integer.parseInt(bookIdString);
                                        int year = Integer.parseInt(yearString);


                                        library.addItem(frame, bookId, title, year, author);
                                        JOptionPane.showMessageDialog(frame, "Book added successfully!");
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(frame, "Invalid input! Please enter valid numbers for ID and year.");
                                    }
                                }
                            });

                            removeBookButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    int id = Integer.parseInt(JOptionPane.showInputDialog(managerFrame, "Enter the id of the book to remove:"));
                                    library.removeItem(frame, id);
                                }
                            });

                            exitButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    managerFrame.dispose();
                                }
                            });

                            managerFrame.add(displayBooksButton);
                            managerFrame.add(addBookButton);
                            managerFrame.add(removeBookButton);
                            managerFrame.add(exitButton);

                            managerFrame.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                        }
                    }
                });

                managerPanel.add(userIdLabel);
                managerPanel.add(userIdField);
                managerPanel.add(passwordLabel);
                managerPanel.add(passwordField);
                managerPanel.add(signin);

                frame.add(managerPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();

                JLabel userIdLabel = new JLabel("Enter your id!");
                JTextField userIdField = new JTextField();
                JLabel passwordLabel = new JLabel("Enter your password!");
                JTextField passwordField = new JTextField();
                JButton signin=new JButton("Sign In");
                signin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Customer customer = new Customer();
                        int userId = Integer.parseInt(userIdField.getText());
                        String password = passwordField.getText();

                        if (customer.check_customer(userId, password)) {
                            frame.dispose();
                            JOptionPane.showMessageDialog(frame, "Sign In Successful!");
                            JFrame userFrame = new JFrame("Manager Menu");
                            userFrame.setSize(400, 300);
                            userFrame.setLayout(new GridLayout(5, 2));

                            JButton displayBooksButton = new JButton("Display Available Books");
                            JButton borrowBookButton = new JButton("Borrow a Book");
                            JButton returnBookButton = new JButton("Return a Book");
                            JButton exitButton = new JButton("Exit");

                            displayBooksButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    library.displayAvailableItems();

                                }
                            });

                            borrowBookButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String title = JOptionPane.showInputDialog(userFrame, "Enter the title of the book to borrow:");
                                    library.borrowItem(frame, title, userId);

                                }
                            });

                            returnBookButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String title = JOptionPane.showInputDialog(userFrame, "Enter the title of the book to return:");
                                    library.returnItem(frame, title);
                                }
                            });

                            exitButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    userFrame.dispose();
                                }
                            });

                            userFrame.add(displayBooksButton);
                            userFrame.add(borrowBookButton);
                            userFrame.add(returnBookButton);
                            userFrame.add(exitButton);

                            userFrame.setVisible(true);
                        }


                    }

                });
                frame.add(userIdLabel);
                frame.add(userIdField);
                frame.add(passwordLabel);
                frame.add(passwordField);
                frame.add(signin);

                frame.revalidate();
                frame.repaint();

            }



        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame signupframe = new JFrame("SignUp Form");
                signupframe.setLayout(new GridLayout(5, 2));
                signupframe.setSize(400, 400);

                JLabel userIdLabel = new JLabel("Enter user ID:");
                JTextField userIdField = new JTextField();
                JLabel nameLabel = new JLabel("Enter name:");
                JTextField nameField = new JTextField();
                JLabel ageLabel = new JLabel("Enter age:");
                JTextField ageField = new JTextField();
                JLabel passwordLabel = new JLabel("Enter password:");
                JTextField passwordField = new JTextField();
                JButton addUserButton = new JButton("Add User");

                addUserButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {

                            int userId = Integer.parseInt(userIdField.getText());
                            String name = nameField.getText();
                            int age = Integer.parseInt(ageField.getText());
                            String password = passwordField.getText();

                            library.addUser(signupframe, userId, name, age, password);
                            JOptionPane.showMessageDialog(signupframe, "User added successfully!");
                            signupframe.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(signupframe, "Invalid input! Please enter valid numbers for ID and age.");
                        }
                    }
                });

                signupframe.add(userIdLabel);
                signupframe.add(userIdField);
                signupframe.add(nameLabel);
                signupframe.add(nameField);
                signupframe.add(ageLabel);
                signupframe.add(ageField);
                signupframe.add(passwordLabel);
                signupframe.add(passwordField);
                signupframe.add(addUserButton);
                signupframe.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}