package tn.esprit.ads.Services;

import java.sql.*;

import tn.esprit.ads.Entity.User;
import tn.esprit.ads.tools.MyDataBase;

public class Suser {
    private Connection cnx;
    private Statement statement;
    private PreparedStatement pst;
    public Suser()
    {
        cnx= MyDataBase.getInstance().getCnx();
    }

   /* public User getUserById(int idUser) {
        User utilisateur = null;
        ResultSet resultSet = null;

        try {
            // Préparez la requête SQL
            String query = "SELECT * FROM user WHERE idUser = ?";
            pst = cnx.prepareStatement(query);
            pst.setInt(1, idUser);

            // Exécutez la requête
            resultSet = pst.executeQuery();

            // Vérifiez s'il y a des résultats
            if (resultSet.next()) {
                // Construisez l'objet Utilisateur à partir des résultats
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("Mailfield");
                String phoneNumber = resultSet.getString("PhoneNumberfield");
                int userId = resultSet.getInt("userId");


            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions SQL
        } finally {
            // Fermez les ressources
            closeResources(pst, resultSet);
        }

        return utilisateur;
    }*/
   public User getUserById(int idUser) {
       User utilisateur = null;
       ResultSet resultSet = null;

       try {
           // Préparez la requête SQL
           String query = "SELECT * FROM user WHERE id = ?";
           pst = cnx.prepareStatement(query);
           pst.setInt(1, idUser);

           // Exécutez la requête
           resultSet = pst.executeQuery();

           // Vérifiez s'il y a des résultats
           if (resultSet.next()) {
               // Construisez l'objet Utilisateur à partir des résultats
               int id = resultSet.getInt("id");
               String nom = resultSet.getString("nom");
               String prenom = resultSet.getString("prenom");
               String mail = resultSet.getString("mail");
               String tel = resultSet.getString("tel");
               String gender = resultSet.getString("gender");
               // Autres colonnes à extraire...

               // Initialisez l'objet User avec les valeurs extraites
               utilisateur = new User(id, nom, prenom, mail, tel, gender);
           }
       } catch (SQLException e) {
           e.printStackTrace();
           // Gérer les exceptions SQL
       } finally {
           // Fermez les ressources
           closeResources(pst, resultSet);
       }

       return utilisateur;
   }


    private void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions SQL lors de la fermeture des ressources
        }
    }

}
