package tn.esprit.ads.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    final String USERNAME = "root";
    final String PASSWORD = "";
    final String URL = "jdbc:mysql://127.0.0.1:3306/dev";
    private Connection cnx;
    private static MyDataBase instance;

    public MyDataBase() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("not connected");
        }
    }
    public static MyDataBase getInstance(){
        if (instance == null)
            instance = new MyDataBase();
        return instance;
    }
    public Connection getCnx() {
        return cnx;
    }


}
