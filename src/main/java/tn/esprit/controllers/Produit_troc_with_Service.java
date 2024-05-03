package tn.esprit.controllers;

import tn.esprit.entities.Producttrocwith;

import tn.esprit.entities.Produittroc;
import tn.esprit.utils.MyDatabase;
import java.util.logging.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Produit_troc_with_Service implements CrudPT <Producttrocwith> {
    private Connection conx;
    private Statement stm;
    private PreparedStatement pstm;
    public Produit_troc_with_Service(){
        conx = MyDatabase.getInstance().getConx();
    }

    @Override
    public List<Producttrocwith> afficherListPTdiffuser(int userid) throws SQLException {
        return null;
    }


    @Override
    public void modifierPT1(Producttrocwith producttrocwith, int idprd, String nom, String category, String description, String image) {

    }

    @Override
    public void addPT(Producttrocwith producttrocwith) throws SQLException {

    }

    @Override
    public void addMeth2(Producttrocwith producttrocwith) throws SQLException {
        String req = "INSERT INTO `produit_troc_with`(`prod_id_troc_id`, `nom`, `category`, `description`, `image`) VALUES (?, ?, ?, ?, ?)";
        pstm = conx.prepareStatement(req);

        pstm.setInt(1, producttrocwith.getProd_id_troc_id());
        pstm.setString(2, producttrocwith.getNom());
        pstm.setString(3, producttrocwith.getCategory());
        pstm.setString(4, producttrocwith.getDescription());
        pstm.setString(5, producttrocwith.getImage());

        pstm.executeUpdate();

        System.out.println("Product troc with added successfully");
    }


    @Override
    public void modifierPT(Producttrocwith producttrocwith) {
        try {
            // Prepare the SQL query
            String req = "UPDATE produit_troc_with SET prod_id_troc_id = ?, nom = ?, category = ?, description = ?,  image = ? WHERE id = ?";
            // Create a PreparedStatement
            PreparedStatement ps= conx.prepareStatement(req);

            // Set parameters
            ps.setInt(1, producttrocwith.getProd_id_troc_id());
            ps.setString(2, producttrocwith.getNom());
            ps.setString(3, producttrocwith.getCategory());
            ps.setString(4, producttrocwith.getDescription());
            ps.setString(5, producttrocwith.getImage());
            ps.setInt(6,producttrocwith.getId());  // Set the id parameter

            // Execute the update query
            ps.executeUpdate();

            // Display success message
            System.out.println("Produittrocwith modifié avec succès");

        } catch (SQLException ex) {
            // Handle any SQL exceptions
            Logger.getLogger(Produit_troc_with_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }






    public void deletePT111(Producttrocwith produittroc) throws SQLException {
        String req = "DELETE FROM `produit_troc_with` WHERE  `nom` = ? AND `category` = ? AND `description` = ? AND `image` = ?";
        pstm = conx.prepareStatement(req);

        // Set parameters based on the product details
        pstm.setString(1, produittroc.getNom());
        pstm.setString(2, produittroc.getCategory());
        pstm.setString(3, produittroc.getDescription());
        pstm.setString(4, produittroc.getImage());

        pstm.executeUpdate();
        System.out.println("Produit supprimé avec succès");
    }

    @Override
            public void deletePT(Producttrocwith produittroc) throws SQLException {
        String req = "DELETE FROM `produit_troc_with` WHERE  `id`=?";
        pstm = conx.prepareStatement(req);

        pstm.setInt(1, produittroc.getId());

        pstm.executeUpdate();
        System.out.println("Produit supprimé avec succès");
    }
    @Override
    public void deletePTall() throws SQLException {
        String req = "DELETE FROM `produit_troc_with`";
        stm = conx.createStatement();
        stm.executeUpdate(req);
        System.out.println("Tous les produits ont été supprimés avec succès");
    }


    public void deletePT1(int productId) {
        String query = "DELETE FROM `produit_troc_with` WHERE `id` = ? ";
        System.out.println("zzzzzzzzzzz");
        try (PreparedStatement preparedStatement = conx.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product with ID " + productId + " deleted successfully.");
            } else {
                System.out.println("No product found with the specified ID: " + productId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public Producttrocwith getProduitById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Producttrocwith> afficherListPT() throws SQLException {
        List<Producttrocwith> productList = new ArrayList<>();
        String req = "SELECT * FROM produit_troc_with";
        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = conx.createStatement();
            rs = stm.executeQuery(req);

            while (rs.next()) {
                Producttrocwith product = new Producttrocwith();
                product.setId(rs.getInt("id"));
                product.setProd_id_troc_id(rs.getInt("prod_id_troc_id"));
                product.setNom(rs.getString("nom"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setImage(rs.getString("image"));

                productList.add(product);
            }
        } finally {
            // Close the resources to prevent memory leaks
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

        return productList;
    }


    public List<Producttrocwith> affichersameuser(int userId) throws SQLException {
        List<Producttrocwith> productList = new ArrayList<>();
        String req = "SELECT * FROM produit_troc_with WHERE prod_id_troc_id != ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = conx.prepareStatement(req);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            while (rs.next()) {
                Producttrocwith product = new Producttrocwith();
                product.setId(rs.getInt("id"));
                product.setProd_id_troc_id(rs.getInt("prod_id_troc_id"));
                product.setNom(rs.getString("nom"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setImage(rs.getString("image"));

                productList.add(product);
            }
        } finally {
            // Close the resources to prevent memory leaks
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }

        return productList;
    }


}