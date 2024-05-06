package tn.jardindart.services;

import tn.jardindart.models.Association;
import tn.jardindart.models.DonArgent;
import tn.jardindart.models.DonBienMateriel;
import tn.jardindart.models.User;
import tn.jardindart.utils.MyDataBase;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class DonArgentService implements IService<DonArgent> {
    private Connection cnx;

    public DonArgentService(){
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void ajouter(DonArgent donArgent) {
        String qry ="INSERT INTO `don_argent`(montant,date_don_argent,id_association_id,user_id_id) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm =cnx.prepareStatement(qry);

            pstm.setDouble(1,donArgent.getMontant());
            pstm.setDate(2,donArgent.getDateDA());



            pstm.setInt(3,donArgent.getId_association());
            pstm.setInt(4,donArgent.getUser_id());

            pstm.executeUpdate();
            System.out.println("Don d'argent ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(DonArgent donArgent) {
        try {
            String requete = "UPDATE don_argent SET montant = ?, id_association_id = ?, user_id_id = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setDouble(1, donArgent.getMontant());
            pst.setInt(2, donArgent.getId_association());
            pst.setInt(3, donArgent.getUser_id());
            pst.setInt(4, donArgent.getId());
            pst.executeUpdate();
            System.out.println("Don d'argent modifié !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM don_argent WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Don d'argent supprimé !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<DonArgent> afficher() {
        ArrayList<DonArgent> donsA = new ArrayList<>();
        try {
            String requete = "SELECT d.*, u.nom AS nom_utilisateur,  a.nom_association AS nom_assoc " +
                    "FROM don_argent d " +
                    "JOIN user u ON d.user_id_id = u.id " +
                    "JOIN association a ON d.id_association_id = a.id";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DonArgent donArgent = new DonArgent();
                donArgent.setId(rs.getInt("id"));
                donArgent.setMontant(rs.getDouble("montant"));
                donArgent.setId_association(rs.getInt("id_association_id"));
                donArgent.setUser_id(rs.getInt("user_id_id"));
                donArgent.setNom_assoc(rs.getString("nom_assoc"));
                donArgent.setNom_ut(rs.getString("nom_utilisateur"));




                donsA.add(donArgent);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return donsA;
    }

    public ArrayList<DonArgent> afficherParUtilisateur(int userId) {
        ArrayList<DonArgent> donsA = new ArrayList<>();
        try {
            String requete = "SELECT d.*, u.nom AS nom_utilisateur, a.nom_association AS nom_assoc " +
                    "FROM don_argent d " +
                    "JOIN user u ON d.user_id_id = u.id " +
                    "JOIN association a ON d.id_association_id = a.id " +
                    "WHERE d.user_id_id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DonArgent donArgent = new DonArgent();
                donArgent.setId(rs.getInt("id"));
                donArgent.setMontant(rs.getDouble("montant"));
                donArgent.setId_association(rs.getInt("id_association_id"));
                donArgent.setUser_id(rs.getInt("user_id_id"));
                donArgent.setNom_assoc(rs.getString("nom_assoc"));
                donArgent.setNom_ut(rs.getString("nom_utilisateur"));
                donsA.add(donArgent);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return donsA;
    }




    @Override
    public DonArgent getById(int donId) {
        ArrayList<DonArgent> allDon = afficher();

        // Parcourir la liste des dons et comparer l'ID
        for (DonArgent don : allDon) {
            if (don.getId() == donId) {
                return don;
            }
        }

        return null;
    }
}
