package tn.esprit.ads.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection connect;
    public static Connection getConnect() {
        String DatabaseName = "pi";
        String username = "dev";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/" + DatabaseName + "?user=" + username + "&password=" + password;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url);
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return connect;
    }
}
