package Grading_System.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements DBConn{
    private static final String URL = "jdbc:mysql://localhost:3306/GRADING";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static MySQLConnection instance;
    private Connection connection;

    private MySQLConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the singleton instance
    public static synchronized MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}
