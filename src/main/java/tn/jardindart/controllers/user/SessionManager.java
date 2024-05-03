package tn.jardindart.controllers.user;

import tn.jardindart.utils.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance;
    private String userId;

    private int userfront ;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserFront(int userfront) {
        this.userfront = userfront;
    }
    public int getUserFront() {
       return userfront;
    }


    public String getUserId()
    {
        return userId;
    }
    public void cleanUserSessionAdmin() {
      userId= " " ;
         }
    public void cleanUserSessionFront() {
        userfront= 0;
    }

    public String getUsernameById(int userId) {
        String nom = null;
        try {
            String query = "SELECT nom FROM user WHERE id = ?";
            PreparedStatement statement = DataBase.getConnect().prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nom = resultSet.getString("nom");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nom;
    }

    public int getPointsById(int userId) {
        int points = 0;
        try {
            // Query the database to get points for the user
            String query = "SELECT nb_points FROM user WHERE id = ?";
            PreparedStatement statement = DataBase.getConnect().prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                points = resultSet.getInt("nb_points");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    public String getFirstNameById(int userId) {
        String prenom = null;
        try {
            String query = "SELECT prenom FROM user WHERE id = ?";
            PreparedStatement statement = DataBase.getConnect().prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                prenom = resultSet.getString("prenom");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prenom;
    }



}
