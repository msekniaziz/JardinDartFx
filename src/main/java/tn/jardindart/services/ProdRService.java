package tn.jardindart.services;

import tn.jardindart.models.ProdR;
import tn.jardindart.models.User;
import tn.jardindart.utils.DataBase;
import tn.jardindart.utils.MyDataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdRService implements RecyclingService<ProdR> {

    private Connection cnx;

    public ProdRService() {
        cnx = MyDataBase.getInstance().getCnx();
    }
    public boolean validerDonnees(ProdR p) {
        // Validation du nom du produit
        String nomProduit = p.getNomP();
        if (nomProduit == null || nomProduit.isEmpty() || nomProduit.length() < 3 || nomProduit.length() > 12) {
            System.out.println("Le nom du produit est obligatoire et doit contenir entre 3 et 12 caractères !");
            return false;
        }

        // Validation de la description
        String description = p.getDescription();
        if (description == null || description.isEmpty() || description.length() < 8) {
            System.out.println("La description du produit est requise et doit contenir au moins 8 caractères !");
            return false;
        }

        if ( p.getPtcId() == 0) {
            System.out.println("L'identifiant du point de collecte est requis !");
            return false;
        }

        if (p.getTypeProdId() == 0) {
            System.out.println("Le type de produit est requis !");
            return false;
        }


        return true;
    }
    @Override
    public void ajouter(ProdR p) {
            String req = "INSERT INTO `prod_r` (`user_id_id`, `ptc_id_id`, `type_prod_id_id`, `statut`, `description`, `nom_p`, `justificatif`) VALUES (?,?,?,?,?,?,?)";
            try {

                if (validerDonnees(p)) {

                    PreparedStatement pstm =cnx.prepareStatement(req);

                pstm.setInt(1,p.getUserId());
                pstm.setInt(2,p.getPtcId());
                pstm.setInt(3,p.getTypeProdId());
                pstm.setBoolean(4,p.getStatut());
                pstm.setString(5,p.getDescription());
                pstm.setString(6,p.getNomP());
                pstm.setString(7,p.getJustificatif());

                    pstm.executeUpdate();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }

    @Override
    public void modifier(ProdR pR) {
//        if (validerDonnees(pR)) {
            String req = "UPDATE `prod_r` SET `user_id_id` = ?, `ptc_id_id` = ?, `type_prod_id_id` = ?, `statut` = ?, `description` = ?, `nom_p` = ?, `justificatif` = ? WHERE `id` = ?";
            try {
                PreparedStatement pstm = cnx.prepareStatement(req);
                pstm.setInt(1, pR.getUserId()); // Remplacez le type de données et l'index par le bon ordre de votre base de données
                pstm.setInt(2, pR.getPtcId());
                pstm.setInt(3, pR.getTypeProdId());
                pstm.setBoolean(4, pR.getStatut());
                pstm.setString(5, pR.getDescription());
                pstm.setString(6, pR.getNomP());
                pstm.setString(7, pR.getJustificatif());
                // Assurez-vous que votre classe ProdR a un champ pour `last_update` si nécessaire
                // Remplacez le type de données et l'index par le bon ordre de votre base de données
                pstm.setInt(8, pR.getId());
                pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
//    }


    @Override
    public void supprimer(ProdR pR) {
        String req = "DELETE FROM `prod_r` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, pR.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<ProdR> recuperer() {
        List<ProdR> prodRList = new ArrayList<>();
        String req = "SELECT p.*, u.nom AS nom_utilisateur, c.nom_pc AS nom_pc, t.nom AS nom_type_prod " +
                "FROM `prod_r` p " +
                "JOIN `user` u ON p.user_id_id = u.id " +
                "JOIN `pt_collect` c ON p.ptc_id_id = c.id " +
                "JOIN `type_dispo` t ON p.type_prod_id_id = t.id";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                ProdR pR = new ProdR();
                pR.setId(rs.getInt("id"));
                pR.setUserId(rs.getInt("user_id_id"));
                pR.setPtcId(rs.getInt("ptc_id_id"));
                pR.setTypeProdId(rs.getInt("type_prod_id_id"));
                pR.setStatut(rs.getBoolean("statut"));
                pR.setDescription(rs.getString("description"));
                pR.setNomP(rs.getString("nom_p"));
                pR.setJustificatif(rs.getString("justificatif"));
                pR.setNomUtilisateur(rs.getString("nom_utilisateur"));
                pR.setNomPtcId(rs.getString("nom_pc"));
                pR.setNomTypeProd(rs.getString("nom_type_prod"));
                prodRList.add(pR);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prodRList;
    }
    public ProdR getById(int Id) {
        ArrayList<ProdR> allP = (ArrayList<ProdR>) recuperer(); // Récupérer tous les prodr

        // Parcourir la liste des prodr et comparer l'ID
        for (ProdR pr : allP) {
            if (pr.getId() == Id) {
                return pr; // Retourner le don s'il correspond à l'ID recherché
            }
        }

        return null; // Retourner null si aucun don avec l'ID spécifié n'est trouvé
    }
    public void uploadImage(File imageFile) {
        // Define the directory within your project where you want to store the uploaded images
        String uploadDirectory = "src/main/resources/tn.jardindart/images"; // Change this to your desired directory

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
    public Integer getNbById(int userId) {
        int nb = 0;
        try {
            String query = "SELECT nb_points FROM user WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nb = resultSet.getInt("nb_points");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nb;
    }

    public void EditNbPtsUser(int id ,int nb ) {
        try {
            //  int userId = SessionManager.getInstance().getUserFront();
            String query = "UPDATE user SET nb_points = ? WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1,nb );

            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("nbpts++");
            } else {
                System.out.println("nbpts err");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(ProdR prodR, boolean newStatus) {
        // Implémentez la logique pour mettre à jour le statut de prodR dans la base de données
        // Par exemple :

        User user =new  User();
        int id = prodR.getUserId();
        if (newStatus==true){
        int nb = getNbById(id)+1;
            EditNbPtsUser(id,nb);

        }
        else {
            int nb = getNbById(id)-1;
            EditNbPtsUser(id,nb);

        }
        prodR.setStatut(newStatus); // Mettez à jour le statut de l'objet ProdR
        modifier(prodR); // Appelez la méthode modifier pour mettre à jour le statut dans la base de données
    }
    public List<ProdR> recupererParUtilisateur(int userId) {
        List<ProdR> prodRList = new ArrayList<>();
        String req = "SELECT p.*, u.nom AS nom_utilisateur, c.nom_pc AS nom_pc, t.nom AS nom_type_prod " +
                "FROM `prod_r` p " +
                "JOIN `user` u ON p.user_id_id = u.id " +
                "JOIN `pt_collect` c ON p.ptc_id_id = c.id " +
                "JOIN `type_dispo` t ON p.type_prod_id_id = t.id " +
                "WHERE p.user_id_id = ?"; // Ajoutez une clause WHERE pour filtrer par utilisateur

        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, userId); // Remplacez le premier point d'interrogation par l'ID de l'utilisateur
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ProdR pR = new ProdR();
                pR.setId(rs.getInt("id"));
                pR.setUserId(rs.getInt("user_id_id"));
                pR.setPtcId(rs.getInt("ptc_id_id"));
                pR.setTypeProdId(rs.getInt("type_prod_id_id"));
                pR.setStatut(rs.getBoolean("statut"));
                pR.setDescription(rs.getString("description"));
                pR.setNomP(rs.getString("nom_p"));
                pR.setJustificatif(rs.getString("justificatif"));
                pR.setNomUtilisateur(rs.getString("nom_utilisateur"));
                pR.setNomPtcId(rs.getString("nom_pc"));
                pR.setNomTypeProd(rs.getString("nom_type_prod"));
                prodRList.add(pR);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return prodRList;
    }

}
