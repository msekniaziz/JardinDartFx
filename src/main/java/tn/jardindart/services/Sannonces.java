package tn.jardindart.services;

import tn.jardindart.models.Annonces;
import tn.jardindart.models.Categories;
import tn.jardindart.services.IServices;
import tn.jardindart.utils.DataBase;
import tn.jardindart.utils.MyDataBase;


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


   public void add(Annonces annonces,int userID) throws SQLException {
       String reqInsert = "INSERT INTO annonces (id,user_id ,title, description, negociable, prix, category, status, image, id_cat_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
       String reqUpdate = "UPDATE category SET nbr_annonce = nbr_annonce + 1 WHERE id = ?";

       try {
           // Insérer l'annonce dans la table 'annonces'
           PreparedStatement pstmInsert = cnx.prepareStatement(reqInsert);
           pstmInsert.setInt(1, annonces.getId());
           pstmInsert.setInt(2, userID);
           pstmInsert.setString(3, annonces.getTitle());
           pstmInsert.setString(4, annonces.getDescription());
           pstmInsert.setInt(5, annonces.getNegiciable());
           pstmInsert.setDouble(6, annonces.getPrix());
           pstmInsert.setString(7, annonces.getCategory());
           pstmInsert.setInt(8, annonces.getStatus());
           pstmInsert.setString(9, annonces.getImage());
           pstmInsert.setInt(10, annonces.getId_Cat());
           pstmInsert.executeUpdate();


           int categoryId = annonces.getId_Cat();


           PreparedStatement pstmUpdate = cnx.prepareStatement(reqUpdate);
           pstmUpdate.setInt(1, categoryId);
           pstmUpdate.executeUpdate();

           System.out.println("Annonce ajoutée avec succès. Nombre d'annonces pour la catégorie mise à jour.");
       } catch (SQLException e) {
           System.out.println("Erreur lors de l'ajout de l'annonce : " + e.getMessage());
           throw e;
       }
   }



  @Override
  public boolean delete(Annonces a) {
      String deleteQuery = "DELETE FROM annonces WHERE id = ?";
      String updateCategoryQuery = "UPDATE category SET nbr_annonce = nbr_annonce - 1 WHERE id = ?";

      try {
          // Retrieve the category ID of the advertisement to be deleted
          int categoryId = a.getId_Cat();

          // Delete the Annonces record
          PreparedStatement deleteStatement = cnx.prepareStatement(deleteQuery);
          deleteStatement.setInt(1, a.getId());
          int rowsAffected = deleteStatement.executeUpdate();

          if (rowsAffected > 0) {
              // Successfully deleted the Annonces record, now update the category's nbr_annonce
              PreparedStatement updateCategoryStatement = cnx.prepareStatement(updateCategoryQuery);
              updateCategoryStatement.setInt(1, categoryId);
              updateCategoryStatement.executeUpdate();

              return true;
          } else {
              return false; // No rows affected, deletion unsuccessful
          }
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
    public void update(Categories categories) {

    }


    @Override
    public ArrayList<Annonces> getAll() {
        String category = "active";
        ArrayList<Annonces> annonces = new ArrayList<>();
        String query = "SELECT * FROM annonces WHERE status=0 AND category=?";

        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, category);
            ResultSet rs = stm.executeQuery();

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

    public ArrayList<Annonces> getAllBack() {
        ArrayList<Annonces> annonces = new ArrayList<>();
        //String query = "SELECT * FROM annonces WHERE status =0 ";
        String query = "SELECT * FROM annonces";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);

            ResultSet rs = stm.executeQuery();
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

    public ArrayList<Annonces> getAllMyAds(int userId) {
        ArrayList<Annonces> annonces = new ArrayList<>();
        String query = "SELECT * FROM annonces WHERE status =0 and user_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
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

    public ArrayList<Annonces> getAllAdsByidcat(int idcat) {
        ArrayList<Annonces> annonces = new ArrayList<>();
        String query = "SELECT * FROM annonces WHERE status =0 and id_cat_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, idcat);
            ResultSet rs = stm.executeQuery();
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
    public void update(Annonces annonces,int idUser) {
        try {

            int oldCategoryId = getCurrentCategoryId(annonces.getId());


            String updateQuery = "UPDATE annonces SET title=?, description=?, negociable=?, prix=?, image=?, id_cat_id=? WHERE id=? AND user_id=?";


            PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);

            updateStatement.setString(1, annonces.getTitle());
            updateStatement.setString(2, annonces.getDescription());
            updateStatement.setInt(3, annonces.getNegiciable());
            updateStatement.setDouble(4, annonces.getPrix());
            updateStatement.setString(5, annonces.getImage());
            updateStatement.setInt(6, annonces.getId_Cat());
            updateStatement.setInt(7, annonces.getId());
            updateStatement.setInt(8, idUser);
            updateStatement.executeUpdate();
            updateCategoryCount(oldCategoryId, annonces.getId_Cat());
            System.out.println("Annonce mise à jour avec succès: " + annonces);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer l'ID de la catégorie actuelle d'une annonce
    private int getCurrentCategoryId(int annonceId) throws SQLException {
        String categoryQuery = "SELECT id_cat_id FROM annonces WHERE id = ?";
        PreparedStatement categoryStatement = cnx.prepareStatement(categoryQuery);
        categoryStatement.setInt(1, annonceId);
        ResultSet resultSet = categoryStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id_cat_id");
        }
        return -1; // Retourner -1 si aucun ID de catégorie n'est trouvé
    }

    // Méthode pour mettre à jour les valeurs de nbr_annonce pour les catégories
    private void updateCategoryCount(int oldCategoryId, int newCategoryId) throws SQLException {
        String decrementQuery = "UPDATE category SET nbr_annonce = nbr_annonce - 1 WHERE id = ?";
        String incrementQuery = "UPDATE category SET nbr_annonce = nbr_annonce + 1 WHERE id = ?";

        // Décrémenter nbr_annonce pour l'ancienne catégorie
        PreparedStatement decrementStatement = cnx.prepareStatement(decrementQuery);
        decrementStatement.setInt(1, oldCategoryId);
        decrementStatement.executeUpdate();

        // Incrémenter nbr_annonce pour la nouvelle catégorie
        PreparedStatement incrementStatement = cnx.prepareStatement(incrementQuery);
        incrementStatement.setInt(1, newCategoryId);
        incrementStatement.executeUpdate();
    }




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

    /*public static ResultSet checkdispo(int id) throws SQLException {
        String query = "SELECT category FROM annonces WHERE id = ?";
        System.out.println("1");
        PreparedStatement statement = cnx.prepareStatement(query);
        System.out.println("2");
        statement.setInt(1, id);
        System.out.println("3");
        return statement.executeQuery();
    }*/
    public static ResultSet checkdispo(int id) throws SQLException {
        String query = "SELECT category FROM annonces WHERE id = ?";
        Connection cnx = MyDataBase.getInstance().getCnx();
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    public Annonces getById(int idAds) {
        ArrayList<Annonces> allAds = getAll();

        for (Annonces a : allAds) {
            if (a.getId() == idAds) {
                return a;
            }
        }

        return null;
    }





}
