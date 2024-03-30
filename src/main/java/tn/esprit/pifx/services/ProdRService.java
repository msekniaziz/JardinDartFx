package tn.esprit.pifx.services;

import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdRService implements RecyclingService<ProdR> {

    private Connection cnx;

    public ProdRService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(ProdR p) {
        String req = "INSERT INTO `prod_r` (`user_id_id`, `ptc_id_id`, `type_prod_id_id`, `statut`, `description`, `nom_p`, `justificatif`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm =cnx.prepareStatement(req);

            pstm.setInt(1,p.getUserId());
            pstm.setInt(2,p.getPtcId());
            pstm.setInt(3,p.getTypeProdId());
            pstm.setInt(4,p.getStatut());
            pstm.setString(5,p.getDescription());
            pstm.setString(6,p.getNomP());
            pstm.setString(7,p.getJustificatif());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(ProdR pR) {
        String req = "UPDATE `prod_r` SET `statut` = ?, `description` = ?, `nom_p` = ?, `justificatif` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, pR.getStatut());
            pstm.setString(2, pR.getDescription());
            pstm.setString(3, pR.getNomP());
            pstm.setString(4, pR.getJustificatif());
            pstm.setInt(5, pR.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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
        String req = "SELECT * FROM `prod_r`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                ProdR pR = new ProdR();
                pR.setId(rs.getInt("id"));
                pR.setStatut(rs.getInt("statut"));
                pR.setDescription(rs.getString("description"));
                pR.setNomP(rs.getString("nom_p"));
                pR.setJustificatif(rs.getString("justificatif"));
                prodRList.add(pR);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prodRList;
    }
}
