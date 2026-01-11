package cabbooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

   
    private static final String URL =
            "jdbc:mysql://localhost:3306/cabbooking";
    private static final String USER = "root";
    private static final String PASSWORD = "Nalevayajiv@1225";

   
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

