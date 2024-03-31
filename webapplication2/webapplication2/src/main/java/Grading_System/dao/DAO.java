package Grading_System.dao;

import Grading_System.util.DBConn;
import Grading_System.util.MySQLConnection;
import java.sql.Connection;
import java.sql.SQLException;

abstract public class DAO {
    private DBConn DB = MySQLConnection.getInstance();;
    protected String table;
    protected Connection conn;

    public DAO(String table) {
        this.conn = this.DB.getConnection();
        this.table = table;
    }

    protected void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
