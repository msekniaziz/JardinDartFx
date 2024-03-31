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
    public void modifierPT(Producttrocwith producttrocwith) throws SQLException {

    }

    @Override
    public void modifierPT1(Producttrocwith producttrocwith, int idprd, String nom, String category, String description, String image) {
        try {
            // Prepare the SQL query
            String req = "UPDATE produit_troc_with SET prod_id_troc_id = ?, nom = ?, category = ?, description = ?,  image = ? WHERE id = ?";
            // Create a PreparedStatement
            PreparedStatement ps= conx.prepareStatement(req);

            // Set parameters
            ps.setInt(1, idprd);
            ps.setString(2, nom);
            ps.setString(3, category);
            ps.setString(4, description);
            ps.setString(5, image);
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



}