package tn.esprit.ads.Services;

import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Interfaces.IServices;
import tn.esprit.ads.tools.DataBase;
import tn.esprit.ads.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class Sannonces implements IServices<Annonces> {
    public static Connection cnx;

    public Sannonces() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        String query = "SELECT name FROM category";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                String categoryName = rs.getString("name");
                categories.add(categoryName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories.", e);
        }
        return categories;
    }

    public void add(Annonces annonces) throws SQLException {
        String req = "INSERT INTO `annonces`(`id`, `title`, `description`, `negociable`, `prix`, `category`, `status`, `image`,  `id_cat_id`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, annonces.getId());

            pstm.setString(2, annonces.getTitle());
            pstm.setString(3, annonces.getDescription());
            pstm.setInt(4, annonces.getNegiciable());
            pstm.setDouble(5, annonces.getPrix());
            pstm.setString(6, annonces.getCategory());
            pstm.setInt(7, annonces.getStatus());
            pstm.setString(8, annonces.getImage());
            pstm.setInt(9, annonces.getId_Cat());
            pstm.executeUpdate();
            System.out.println("Annonce ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(Annonces a) {
        String query = "DELETE FROM annonces WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, a.getId());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        String query = "DELETE FROM annonces";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public ArrayList<Annonces> getAll() {
        ArrayList<Annonces> annonces = new ArrayList<>();
        String query = "SELECT * FROM annonces";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Annonces annonce = new Annonces();
                annonce.setId(rs.getInt("id"));
                annonce.setUser_id(rs.getInt("user_id"));
                annonce.setTitle(rs.getString("title"));
                annonce.setDescription(rs.getString("description"));
                annonce.setNegiciable(rs.getInt("negociable"));
                annonce.setPrix(rs.getFloat("prix"));
                annonce.setStatus(rs.getInt("status"));
                annonce.setImage(rs.getString("image"));
                annonce.setId_Cat(rs.getInt("id_cat_id"));
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            // Lancer une nouvelle RuntimeException avec l'exception d'origine
            throw new RuntimeException("Erreur lors de la récupération des annonces.", e);
        }
        return annonces;
    }

    @Override
    public void update(Annonces annonces) {
        try {
            // Construire la requête SQL UPDATE avec la clause WHERE pour spécifier l'ID de l'annonce à mettre à jour
            String query = "UPDATE annonces SET user_id=?, title=?, description=?, negociable=?, prix=?, image=?, id_cat_id=? WHERE id=?";

            // Préparer la déclaration de requête avec la requête SQL
            PreparedStatement stm = cnx.prepareStatement(query);

            // Remplacer les paramètres de la requête par les valeurs de l'annonce fournie
            stm.setInt(1, annonces.getUser_id());
            stm.setString(2, annonces.getTitle());
            stm.setString(3, annonces.getDescription());
            stm.setInt(4, annonces.getNegiciable());
            stm.setDouble(5, annonces.getPrix());
            stm.setString(6, annonces.getImage());
            stm.setInt(7, annonces.getId_Cat());
            stm.setInt(8, annonces.getId()); // Spécifier l'ID de l'annonce dans la clause WHERE

            // Exécuter la mise à jour de la base de données
            stm.executeUpdate();

            // Afficher un message de confirmation
            System.out.println("Annonce mise à jour avec succès: " + annonces);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public void update(Annonces annonces) {


        try {
            String query = "UPDATE annonces SET user_id=?, title=?, description=?, negociable=?, prix=?, image=?, id_cat_id=? WHERE id=?";
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, annonces.getUser_id());
            stm.setString(2, annonces.getTitle());
            stm.setString(3, annonces.getDescription());
            stm.setInt(4, annonces.getNegiciable());
            stm.setDouble(5, annonces.getPrix());
            stm.setString(6, annonces.getImage());
            stm.setInt(7, annonces.getId());
            stm.setInt(8, annonces.getId_Cat());
            stm.executeUpdate();
            // int rowsAffected = stm.executeUpdate();
            //  if (rowsAffected > 0) {
            System.out.println("Annonce mise à jour avec succès: " + annonces);
        }
     catch(
    SQLException e)

    {
        e.printStackTrace();
    }
}*/

    public String getCategoryName(int categoryId) {
        String categoryName = null;
        String query = "SELECT name FROM category WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, categoryId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                categoryName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryName;
    }
    public boolean updateAndReturnSuccess(Annonces annonces) {
        String query = "UPDATE annonces SET user_id=?, title=?, description=?, negociable=?, prix=?, image=? WHERE id=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, annonces.getUser_id());
            stm.setString(2, annonces.getTitle());
            stm.setString(3, annonces.getDescription());
            stm.setInt(4, annonces.getNegiciable());
            stm.setDouble(5, annonces.getPrix());
            stm.setString(6, annonces.getImage());
            stm.setInt(7, annonces.getId()); // spécification de l'id dans la clause WHERE
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Annonce mise à jour avec succès: " + annonces);
                return true; // La mise à jour a réussi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // La mise à jour a échoué
    }

    public static ResultSet checkdispo(int id) throws SQLException {
        String query = "SELECT category FROM annonces WHERE id = ?";
        System.out.println("1");
        PreparedStatement statement = cnx.prepareStatement(query);
        System.out.println("2");
        statement.setInt(1, id);
        System.out.println("3");
        return statement.executeQuery();
    }
    public Annonces getById(int idAds) {
        ArrayList<Annonces> allAds = getAll(); // Récupérer tous les dons

        // Parcourir la liste des dons et comparer l'ID
        for (Annonces a : allAds) {
            if (a.getId() == idAds) {
                return a; // Retourner le don s'il correspond à l'ID recherché
            }
        }

        return null; // Retourner null si aucun don avec l'ID spécifié n'est trouvé
    }
    /*public Annonces getById(int idAds) {
        String query = "SELECT * FROM annonces WHERE id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, idAds);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Annonces annonce = new Annonces();
                annonce.setId(rs.getInt("id"));
                annonce.setUser_id(rs.getInt("user_id"));
                annonce.setTitle(rs.getString("title"));
                annonce.setDescription(rs.getString("description"));
                annonce.setNegiciable(rs.getInt("negociable"));
                annonce.setPrix(rs.getFloat("prix"));
                annonce.setStatus(rs.getInt("status"));
                annonce.setImage(rs.getString("image"));
                annonce.setId_Cat(rs.getInt("id_cat_id"));
                return annonce;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
        return null; // Retourner null si aucun don avec l'ID spécifié n'est trouvé
    }*/



}
