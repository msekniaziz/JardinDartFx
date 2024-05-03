package tn.jardindart.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import tn.jardindart.models.User;

public class DataBase {

    public static Connection connect;
        public static Connection getConnect() {
            String DatabaseName = "pi";
            String username = "root";
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


    public static ObservableList<User> getDatauser(){
            Connection conn = getConnect();
            ObservableList<User> list = FXCollections.observableArrayList();
            try {
                conn = getConnect();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE role='USER'");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String mail = rs.getString("mail");
                    String tel = rs.getString("tel");
                    String gender = rs.getString("gender");
                    int points = rs.getInt("nb_points");
                    LocalDate dn = rs.getDate("date_birthday").toLocalDate();
                    String status = rs.getString("status");

                    list.add(new User(id,nom,prenom,mail,tel,gender,points,dn,status));
                }  } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

    }
