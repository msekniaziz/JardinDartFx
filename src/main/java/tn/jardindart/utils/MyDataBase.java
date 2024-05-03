package tn.jardindart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance ;//pour verifier si on a cree une liaison a la bdd ou nn

    private final String URL="jdbc:mysql://127.0.0.1:3306/pi";
    private final String USERNAME ="root";
    private final String PASSWORD ="";
    private Connection cnx ;

    private MyDataBase(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("  " +
                    "Connected to DB ... ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" ___ Connection Failed ___");
        }
    }


    public static MyDataBase getInstance() {
        if(instance == null)
            instance = new MyDataBase();

        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
