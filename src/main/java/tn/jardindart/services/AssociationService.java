package tn.jardindart.services;

import tn.jardindart.models.Association;
import tn.jardindart.models.DonArgent;
import tn.jardindart.models.DonBienMateriel;
import tn.jardindart.utils.MyDataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class AssociationService implements IService<Association> {

    private Connection cnx;

    public AssociationService(){
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Association association) {

        String qry ="INSERT INTO `association`(`nom_association`, `adresse_association`, `logo_association`,`rib`,`description_asso`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm =cnx.prepareStatement(qry);

            pstm.setString(1,association.getNom_association());
            pstm.setString(2,association.getAdresse_association());
            pstm.setString(3,association.getLogo_association());
            pstm.setInt(4,association.getRib());
            pstm.setString(5,association.getDescription_asso());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'association:"+e.getMessage());
        }

    }

    @Override
    public void modifier(Association association) {
        try {
            String qry = "UPDATE association SET nom_association = ?, adresse_association = ?, logo_association = ?, description_asso = ?,rib = ? WHERE id = ?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, association.getNom_association());
            pstm.setString(2, association.getAdresse_association());
            pstm.setString(3, association.getLogo_association());
            pstm.setString(4, association.getDescription_asso());
            pstm.setInt(5, association.getRib());
            pstm.setInt(6, association.getId());

            pstm.executeUpdate();
                System.out.println("Association modifiée !");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

    @Override
        public void supprimer(int id) {

        try {
            String qry = "DELETE FROM association WHERE id= ?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1,id);
            pstm.executeUpdate();
            System.out.println("Restaurant supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
        @Override
        public ArrayList<Association> afficher() {
            //1-req SELECT
            //2-recuperation de la base de donné remplissage dans Array
            //3-retour du tableau done
            ArrayList<Association> associations = new ArrayList<>();
            String qry ="SELECT * FROM `association`";
            try {
                Statement stm = cnx.createStatement();

                ResultSet rs = stm.executeQuery(qry);

                while (rs.next()){
                    Association p = new Association();
                    p.setId(rs.getInt(1));
                    p.setNom_association(rs.getString("nom_association"));
                    p.setAdresse_association(rs.getString(3));
                    p.setRib(rs.getInt("rib"));
                    p.setLogo_association(rs.getString(5));
                    p.setDescription_asso(rs.getString(6));



                    associations.add(p);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


            return associations;
        }

    @Override
    public Association getById(int associationId) {
        ArrayList<Association> allAssociation = afficher();

        // Parcourir la liste des dons et comparer l'ID
        for (Association association : allAssociation) {
            if (association.getId() == associationId) {
                return association;
            }
        }

        return null;
    }
    public boolean isAssociationNameUnique(String nomAssoc) {
        // Effectuer une requête à la base de données pour vérifier si le nom est unique
        // Vous devez remplacer cette partie avec la logique spécifique à votre accès aux données

        // Exemple de requête fictive
        String query = "SELECT COUNT(*) FROM Association WHERE nom_association = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nomAssoc);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Le nom est unique si le compte est zéro
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Par défaut, retourner faux en cas d'erreur
    }
    public boolean validateRib(String rib) {
        // Vérifier si le RIB contient exactement 18 chiffres
        if (rib.length() != 10) {

            return false;
        }

        // Vérifier si le RIB contient uniquement des chiffres
        if (!rib.matches("\\d{10}")) {

            return false;
        }

        return true;
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


}







