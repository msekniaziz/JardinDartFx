package tn.esprit.ads.Services;

import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Commandes;
import tn.esprit.ads.Services.Scommandes;
import tn.esprit.ads.Interfaces.IServices;
import com.mysql.cj.jdbc.MysqlDataSource;
import tn.esprit.ads.tools.MyDataBase;
import tn.esprit.ads.Entity.Annonces;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static tn.esprit.ads.Services.Sannonces.cnx;

public class Scommandes  {
    private static Connection cnx;

    public Scommandes(){
        cnx = MyDataBase.getInstance().getCnx();
    }

    /*public void add(Commandes commandes) throws SQLException {
        String selectQuery = "SELECT `status` FROM `annonces` WHERE user_id=?";
        String updateQuery = "UPDATE `annonces` SET `status` = 1 WHERE user_id=?";
        String insertQuery = "INSERT INTO `commandes`(`id`,`id_user_c_id`, `etat`, `date`) VALUES (?, ?, ?, ?)";

        try {
            // Vérifier le statut de l'annonce
            PreparedStatement selectStatement = cnx.prepareStatement(selectQuery);
            selectStatement.setInt(1, commandes.getId_user_c_id());
            ResultSet resultSet = selectStatement.executeQuery();

            int status = 0;
            if (resultSet.next()) {
                status = resultSet.getInt("status");
            }

            if (status != 1) {
                // Mettre à jour le statut de l'annonce à 1
                PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
                updateStatement.setInt(1, commandes.getId_user_c_id());
                updateStatement.executeUpdate();
            }

            // Vérifier si l'utilisateur avec l'ID spécifié existe
            String userExistQuery = "SELECT id FROM user WHERE id=?";
            PreparedStatement userExistStatement = cnx.prepareStatement(userExistQuery);
            userExistStatement.setInt(1, commandes.getId_user_c_id());
            ResultSet userResultSet = userExistStatement.executeQuery();

            if (!userResultSet.next()) {
                System.out.println("L'utilisateur avec l'ID spécifié n'existe pas.");
                return; // Arrêter l'opération d'ajout de commande si l'utilisateur n'existe pas
            }

            // Vérifier la date de la commande
            if (commandes.getDate() == null) {
                System.out.println("La date de la commande est null.");
                return; // Arrêter l'opération d'ajout de commande si la date est null
            }

            // Insérer la nouvelle commande
            PreparedStatement insertStatement = cnx.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, commandes.getId());
            insertStatement.setInt(2, commandes.getId_user_c_id());
            insertStatement.setInt(3, commandes.getEtat());
            insertStatement.setDate(4, new java.sql.Date(commandes.getDate().getTime()));

            insertStatement.executeUpdate();

            System.out.println("Commande ajoutée avec succès.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
    public String recupereNom(int idUser) throws SQLException {
        String nom = null;
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nom = resultSet.getString("nom");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nom ;
    }
    public void add(int idUser) throws SQLException {
                String req = "INSERT INTO `commandes`(id_user_c_id, etat,date) VALUES (?,?,?)";
        LocalDate localDate = LocalDate.now();
        int etat = 0 ;
        try {
                    PreparedStatement pstm = cnx.prepareStatement(req);
                    pstm.setInt(1, idUser);
                    pstm.setInt(2, etat);
                    pstm.setObject(3, localDate);
                    pstm.executeUpdate();
                    System.out.println("Order added successfully.");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

    public boolean delete(Commandes commandes) {
        String query = "DELETE FROM `commandes` WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, commandes.getId());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAll() {
        String query = "DELETE FROM commandes";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Commandes> getAll() {
        ArrayList<Commandes> commandes = new ArrayList<>();
        String query = "SELECT * FROM commandes";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Commandes commande = new Commandes();
                commande.setId_user_c_id(rs.getInt("id_user_c_id"));
                commande.setEtat(rs.getInt("etat"));
                commande.setDate(rs.getDate("date"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            // Lancer une nouvelle RuntimeException avec l'exception d'origine
            throw new RuntimeException("Erreur lors de la récupération des commandes.", e);
        }
        return commandes;
    }
  /* public ArrayList<Commandes> getAll() {
       ArrayList<Commandes> commandes = new ArrayList<>();
       String query = "SELECT commandes.*, user.nom " +
               "FROM commandes " +
               "INNER JOIN user ON commandes.id_user_c_id = users.id";
       try {
           Statement stm = cnx.createStatement();
           ResultSet rs = stm.executeQuery(query);
           while (rs.next()) {
               Commandes commande = new Commandes();
               commande.setId(rs.getInt("id"));
               commande.setId_user_c_id(rs.getInt("id_user_c_id"));
               commande.setEtat(rs.getInt("etat"));
               commande.setName(rs.getString("nom")); // Setter pour le nom d'utilisateur
               // commande.setDate(rs.getDate("06/04/2024")); // Assurez-vous de définir la date correctement
               commandes.add(commande);
           }
       } catch (SQLException e) {
           // Lancer une nouvelle RuntimeException avec l'exception d'origine
           throw new RuntimeException("Erreur lors de la récupération des commandes.", e);
       }
       return commandes;
   }*/


    public void update(Commandes commandes) {
        String query = "UPDATE commandes SET id_user_c_id=?, etat=? WHERE id=?";

        try {
            PreparedStatement stm = cnx.prepareStatement(query);

            stm.setInt(1, commandes.getId_user_c_id());
            stm.setDouble(2, commandes.getEtat());

            // Spécification de l'ID uniquement dans la clause WHERE
            stm.setInt(3, commandes.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Commande mise à jour avec succès: " + commandes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
