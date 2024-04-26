package tn.esprit.jardindart.services;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.models.DonBienMateriel;
import tn.esprit.jardindart.utils.MyDataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;//fiha .Connection
import java.util.ArrayList;
import java.util.List;


public class DonBienMaterielService implements IService<DonBienMateriel>,IAssocService {
    private AssociationService associationService;


    private Connection cnx;


    public DonBienMaterielService(){
        cnx = MyDataBase.getInstance().getCnx();
        this.associationService = new AssociationService();
    }


    @Override
    public void ajouter(DonBienMateriel donBienMateriel) {
        try {
            String requete = "INSERT INTO don_bien_materiel (description_don, photo_don, statut_don, id_association_id, user_id_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, donBienMateriel.getDesc_don());
            pst.setString(2, donBienMateriel.getPhoto_don());
            pst.setBoolean(3, donBienMateriel.isStatut_don());
            pst.setInt(4, donBienMateriel.getId_association());
            pst.setInt(5, donBienMateriel.getUser_id());
            pst.executeUpdate();
            System.out.println("Don de bien matériel ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(DonBienMateriel donBienMateriel) {
        try {
            String requete = "UPDATE don_bien_materiel SET description_don = ?, photo_don = ?, statut_don = ?, id_association_id = ?, user_id_id = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, donBienMateriel.getDesc_don());
            pst.setString(2, donBienMateriel.getPhoto_don());
            pst.setBoolean(3, donBienMateriel.isStatut_don());
            pst.setInt(4, donBienMateriel.getId_association());
            pst.setInt(5, donBienMateriel.getUser_id());
            pst.setInt(6, donBienMateriel.getId());
            pst.executeUpdate();
            System.out.println("Don de bien matériel modifié !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM don_bien_materiel WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Don de bien matériel supprimé !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<DonBienMateriel> afficher() {
        ArrayList<DonBienMateriel> donsBienMateriel = new ArrayList<>();
        try {
            String requete = "SELECT d.*, u.nom AS nom_utilisateur,  a.nom_association AS nom_assoc " +
                    "FROM don_bien_materiel d " +
                    "JOIN user u ON d.user_id_id = u.id " +
                    "JOIN association a ON d.id_association_id = a.id";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DonBienMateriel donBienMateriel = new DonBienMateriel();
                donBienMateriel.setId(rs.getInt("id"));
                donBienMateriel.setDesc_don(rs.getString("description_don"));
                donBienMateriel.setPhoto_don(rs.getString("photo_don"));
                donBienMateriel.setStatut_don(rs.getBoolean("statut_don"));
                donBienMateriel.setId_association(rs.getInt("id_association_id"));
                donBienMateriel.setUser_id(rs.getInt("user_id_id"));
                donBienMateriel.setNom_assoc(rs.getString("nom_assoc"));
                donBienMateriel.setNom_ut(rs.getString("nom_utilisateur"));




                donsBienMateriel.add(donBienMateriel);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return donsBienMateriel;
    }

    @Override
    public DonBienMateriel getById(int donId) {
        ArrayList<DonBienMateriel> allDonations = afficher(); // Récupérer tous les dons

        // Parcourir la liste des dons et comparer l'ID
        for (DonBienMateriel don : allDonations) {
            if (don.getId() == donId) {
                return don; // Retourner le don s'il correspond à l'ID recherché
            }
        }

        return null; // Retourner null si aucun don avec l'ID spécifié n'est trouvé
    }


    @Override
    public String getAssociationNameById(int associationId) {
        // Fetch association by ID
        List<Association> associations = associationService.afficher();

        // Iterate over the list to find the association with the given ID
        for (Association association : associations) {
            if (association.getId() == associationId) {
                // Return the name of the association
                return association.getNom_association();
            }
        }

        // If no association is found with the given ID, return "Unknown Association"
        return "Unknown Association";
    }

    public void uploadImage(File imageFile) {
        String uploadDirectory = "src/main/resources/tn/esprit/jardindart/images"; // Change this to your desired directory
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


    public void updateStatus(DonBienMateriel don, boolean newStatus) {

        don.setStatut_don(newStatus);
        modifier(don); // Appelez la méthode modifier pour mettre à jour le statut dans la base de données
    }
    }




