package tn.esprit.ads.Services;

import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Interfaces.IServices;
import tn.esprit.ads.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class Scategories implements IServices<Categories>{
    private static Connection cnx;
    public Scategories(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(Categories categories) throws SQLException {
        // Vérifier que le nom de la catégorie n'est pas null
        if (categories.getName() == null) {
            System.out.println("Le nom de la catégorie ne peut pas être null.");
            return;
        }

        String req = "INSERT INTO `category`(`id`, `name`, `key_cat`, `nbr_annonce`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, categories.getId());
            pstm.setString(2, categories.getName());
            pstm.setInt(3, categories.getKey_cat());
            pstm.setInt(4, categories.getNbr_annonce());
            pstm.executeUpdate();
            System.out.println("Catégorie ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la catégorie : " + e.getMessage());
        }
    }
    @Override
    public boolean delete(Categories categories) {
        String query = "DELETE FROM `category` WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, categories.getId());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteAll() {
        String query = "DELETE FROM category";
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
    public ArrayList<Categories> getAll() {
        ArrayList<Categories> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setKey_cat(rs.getInt("key_cat"));
                category.setNbr_annonce(rs.getInt("nbr_annonce"));

                categories.add(category);
            }
        } catch (SQLException e) {
            // Lancer une nouvelle RuntimeException avec l'exception d'origine
            throw new RuntimeException("Erreur lors de la récupération des categories.", e);
        }
        return categories;
    }

    @Override
    public void update(Categories categories) {
        String query = "UPDATE category SET name=?, key_cat=?, nbr_annonce=? WHERE id=?";

        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, categories.getName());
            stm.setInt(2, categories.getKey_cat());
            stm.setInt(3, categories.getNbr_annonce());
            stm.setInt(4, categories.getId()); // Spécification de l'ID uniquement dans la clause WHERE
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Catégorie mise à jour avec succès: " + categories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
