package tn.esprit.pifx.services;

import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PtCollectService {

    private Connection cnx;

    public PtCollectService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    // Create
    public void ajouter(PtCollect ptCollect) {
        String req = "INSERT INTO `pt_collect` ( `nom_pc`, `adresse_pc`, `latitude_pc`, `longitude_pc`) VALUES ( ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
          // pstm.setInt(1, ptCollect.getId());
            pstm.setString(1, ptCollect.getNomPc());
            pstm.setString(2, ptCollect.getAdressePc());
            pstm.setFloat(3, ptCollect.getLatitudePc());
            pstm.setFloat(4, ptCollect.getLongitudePc());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du point de collecte : " + e.getMessage());
        }
    }

    // Read
    public List<PtCollect> recuperer() {
        List<PtCollect> ptCollectList = new ArrayList<>();
        String req = "SELECT * FROM `pt_collect`";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PtCollect ptCollect = new PtCollect();
                ptCollect.setId(rs.getInt("id"));
                ptCollect.setNomPc(rs.getString("nom_pc"));
                ptCollect.setAdressePc(rs.getString("adresse_pc"));
                ptCollect.setLatitudePc(rs.getFloat("latitude_pc"));
                ptCollect.setLongitudePc(rs.getFloat("longitude_pc"));
                ptCollectList.add(ptCollect);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des points de collecte : " + e.getMessage());
        }
        return ptCollectList;
    }

    // Update
    public void modifier(PtCollect ptCollect) {
        String req = "UPDATE `pt_collect` SET `nom_pc` = ?, `adresse_pc` = ?, `latitude_pc` = ?, `longitude_pc` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setString(1, ptCollect.getNomPc());
            pstm.setString(2, ptCollect.getAdressePc());
            pstm.setFloat(3, ptCollect.getLatitudePc());
            pstm.setFloat(4, ptCollect.getLongitudePc());
            pstm.setInt(5, ptCollect.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du point de collecte : " + e.getMessage());
        }
    }

    // Delete
    public void supprimer(int id) {
        String req = "DELETE FROM `pt_collect` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du point de collecte : " + e.getMessage());
        }
    }
}
