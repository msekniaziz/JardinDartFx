package tn.esprit.controllers;

import tn.esprit.entities.Producttrocwith;
import tn.esprit.entities.Produittroc;
import tn.esprit.utils.MyDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Produit_TrocService implements CrudPT <Produittroc>{
    private Connection conx;
    private Statement stm;
    private PreparedStatement pstm;
    public Produit_TrocService(){
        conx = MyDatabase.getInstance().getConx();
    }


    @Override
    public void addPT(Produittroc produittroc) throws SQLException {

        String req = "INSERT INTO `produit_troc`(`id_user_id`, `nom`, `category`, `description`, `statut`, `image`, `nom_produit_recherche`) VALUES ('"+produittroc.getNom()+"','"+produittroc.getCategory()+"'," + "'"+produittroc.getDescription()+"','"+produittroc.getStatut()+"','"+produittroc.getImage()+"','"+produittroc.getNom_produit_recherche()+"')";
        stm = conx.createStatement();
        stm.executeUpdate(req);
        System.out.println("Product added ajoutée");
    }


    public List<Produittroc> searchByNameAndCategory(String searchTerm) throws SQLException {
        List<Produittroc> productList = new ArrayList<>();

        String req = "SELECT * FROM `produit_troc` WHERE `nom` LIKE ? OR `category` LIKE ?";
        PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, "%" + searchTerm + "%");
        ps.setString(2, "%" + searchTerm + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("id_user_id");
            int statut = rs.getInt("statut");
            String productName = rs.getString("nom");
            String productCategory = rs.getString("category");
            String description = rs.getString("description");
            String image = rs.getString("image");
            String productSearchName = rs.getString("nom_produit_recherche");

            Produittroc product = new Produittroc(id, userId, statut, productName, productCategory, description, image, productSearchName);
            productList.add(product);
        }

        return productList;
    }

    @Override
    public void addMeth2(Produittroc produittroc) throws SQLException {
        String req = "INSERT INTO `produit_troc`(`id_user_id`,`nom`, `category`, `description`, `statut`, `image`, `nom_produit_recherche`) VALUES (?,?,?,?,?,?,?)";
        pstm = conx.prepareStatement(req);
        pstm.setInt(1, produittroc.getId_user_id());

        pstm.setString(2, produittroc.getNom());
        pstm.setString(3, produittroc.getCategory());
        pstm.setString(4, produittroc.getDescription());
        pstm.setInt(5, produittroc.getStatut());
        pstm.setString(6, produittroc.getImage());
        pstm.setString(7, produittroc.getNom_produit_recherche());

        pstm.executeUpdate();
        System.out.println("Produit ajouté meth 2");
    }


    @Override
    public void modifierPT(Produittroc produittroc) throws SQLException {
        String req = "UPDATE `produit_troc` SET `nom`=?, `category`=?, `description`=?, `statut`=?, `image`=?, `nom_produit_recherche`=? WHERE `id`=?";
        pstm = conx.prepareStatement(req);

        pstm.setString(1, produittroc.getNom());
        pstm.setString(2, produittroc.getCategory());
        pstm.setString(3, produittroc.getDescription());
        pstm.setInt(4, produittroc.getStatut());
        pstm.setString(5, produittroc.getImage());
        pstm.setString(6, produittroc.getNom_produit_recherche());
        pstm.setInt(7, produittroc.getId());

        pstm.executeUpdate();
        System.out.println("Produit modifié avec succès");
    }


    @Override
    public void modifierPT1(Producttrocwith producttrocwith, int idprd, String nom, String category, String description, String image) {

    }



    @Override
    public void deletePT(Produittroc produittroc) {
        String req = "DELETE FROM `produit_troc` WHERE `id`=?";
        try (PreparedStatement pstm = conx.prepareStatement(req)) {
            pstm.setInt(1, produittroc.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit supprimé avec succès");
            } else {
                System.out.println("Aucun produit trouvé avec l'ID spécifié");
            }
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de la suppression du produit: " + e.getMessage());
        }
    }




    public void deletePT1(int productId) {
        String query = "DELETE FROM `produit_troc` WHERE `id` = ? ";
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
    public List<Produittroc> afficherListPT() throws SQLException {
        // Implement the method to retrieve a list of Produittroc objects
        String req = "SELECT * FROM `produit_troc`";

        stm  = conx.createStatement();
        ResultSet res = stm.executeQuery(req);

        List<Produittroc> produits = new ArrayList<>();

        while (res.next()){
            Produittroc produit = new Produittroc();
            produit.setId((res.getInt("id")));
            produit.setId_user_id(res.getInt("id_user_id"));
            produit.setNom(res.getString("nom"));
            produit.setCategory(res.getString("category"));
            produit.setDescription(res.getString("description"));
            produit.setStatut(res.getInt("statut"));
            produit.setImage(res.getString("image"));
            produit.setNom_produit_recherche(res.getString("nom_produit_recherche"));

            produits.add(produit);
        }

        return produits;
    }



    @Override
    public List<Produittroc> afficherListPTdiffuser(int userid) throws SQLException {
        // Implement the method to retrieve a list of Produittroc objects for users other than the given user ID
        String req = "SELECT * FROM `produit_troc` WHERE id_user_id != ?";

        PreparedStatement stm = conx.prepareStatement(req);
        stm.setInt(1, userid);
        ResultSet res = stm.executeQuery();

        List<Produittroc> produits = new ArrayList<>();

        while (res.next()){
            Produittroc produit = new Produittroc();
            produit.setId(res.getInt("id"));
            produit.setId_user_id(res.getInt("id_user_id"));
            produit.setNom(res.getString("nom"));
            produit.setCategory(res.getString("category"));
            produit.setDescription(res.getString("description"));
            produit.setStatut(res.getInt("statut"));
            produit.setImage(res.getString("image"));
            produit.setNom_produit_recherche(res.getString("nom_produit_recherche"));

            produits.add(produit);
        }

        return produits;
    }

    @Override
    public void deletePTall() throws SQLException {
        String req = "DELETE FROM `produit_troc`";
        stm = conx.createStatement();
        stm.executeUpdate(req);
        System.out.println("Tous les produits ont été supprimés avec succès");
    }



    @Override
    public Produittroc getProduitById(int id) throws SQLException {
        String req = "SELECT * FROM `produit_troc` WHERE `id`=?";
        pstm = conx.prepareStatement(req);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            Produittroc produit = new Produittroc();
            produit.setId(rs.getInt("id"));
            produit.setId_user_id(rs.getInt("id_user_id"));
            produit.setNom(rs.getString("nom"));
            produit.setCategory(rs.getString("category"));
            produit.setDescription(rs.getString("description"));
            produit.setStatut(rs.getInt("statut"));
            produit.setImage(rs.getString("image"));
            produit.setNom_produit_recherche(rs.getString("nom_produit_recherche"));

            return produit;
        } else {
            System.out.println("Produit avec l'ID " + id + " n'existe pas.");
            return null;
        }
    }


    public void uploadImage(File imageFile) {
        // Define the directory within your project where you want to store the uploaded images
        String uploadDirectory = "src/main/java/images"; // Change this to your desired directory

        // Create the directory if it doesn't exist
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                // Failed to create the directory
                System.err.println("Failed to create the upload directory");
                return; // Exit the method
            }
        }

        try {
            // Get the file name of the uploaded image
            String fileName = imageFile.getName();

            // Define the destination path where you want to store the uploaded image
            Path destinationPath = Path.of(uploadDirectory, fileName);

            // Copy the uploaded image file to the destination path
            Files.copy(imageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Handle the IOException
            System.err.println("Error uploading image: " + e.getMessage());
        }
    }

    public static class Cardtptr {
    }
}

