package tn.jardindart.services;

import tn.jardindart.models.TypeDispo;
import tn.jardindart.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDispoService {
    private Connection cnx;

    public TypeDispoService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    // CREATE operation
    public boolean ajouter(TypeDispo typeDispo) {
        String req = "INSERT INTO `type_dispo` (`nom`) VALUES (?)";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, typeDispo.getNom());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ operation
    public List<TypeDispo> recuperer() {
        List<TypeDispo> typeList = new ArrayList<>();
        String req = "SELECT `id`, `nom` FROM `type_dispo`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                TypeDispo t = new TypeDispo();
                t.setId(rs.getInt("id"));
                t.setNom(rs.getString("nom"));
                typeList.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return typeList;
    }

    // UPDATE operation
    public boolean modifier(TypeDispo typeDispo) {
        if (typeDispo.getId() == null) {
            throw new IllegalArgumentException("L'identifiant du type de produit ne peut pas être null.");
        }
        String req = "UPDATE `type_dispo` SET `nom`=? WHERE `id`=?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, typeDispo.getNom());
            pstmt.setInt(2, typeDispo.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE operation
    public boolean supprimer(int typeId) {
        String req = "DELETE FROM `type_dispo` WHERE `id`=?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, typeId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
