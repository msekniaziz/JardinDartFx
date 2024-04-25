package tn.esprit.applicationgui.Controllers;

import tn.esprit.applicationgui.entites.ReponseBlog;
import tn.esprit.applicationgui.utils.MyDataBase;
import java.sql.Date;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseBlogservice implements ServiceRep <ReponseBlog>{
    private Connection cnx;
public ReponseBlogservice(){
    cnx= MyDataBase.getInstance().getConx();

}


    @Override
    public void addrep(ReponseBlog reponseBlog) throws SQLException {
        String req = "INSERT INTO reponse_blog (date, contenu) VALUES ('" + reponseBlog.getDate() + "','" + reponseBlog.getContenu() +  "')";
        Statement stm = cnx.createStatement();
        stm.executeUpdate(req);
        System.out.println("reponse  ajoutée");

    }

    @Override
    public List<ReponseBlog> showrep() throws SQLException {
        List<ReponseBlog> reponseBlogList = new ArrayList<>();
        String qry = "SELECT * FROM reponse_blog";

        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                // Déclaration et initialisation de currentDate avec la date actuelle
                Date currentDate = new Date(System.currentTimeMillis());

                // Création d'une nouvelle instance de ReponseBlog avec currentDate
                ReponseBlog reponseBlog = new ReponseBlog("17/05/2000", "cont", 1);

                // Mise à jour de currentDate avec la valeur de la colonne "datecurrent" du ResultSet
                currentDate = rs.getDate("datecurrent");

                // Mise à jour des autres attributs de reponseBlog à partir des colonnes du ResultSet
                reponseBlog.setId(rs.getInt("id"));
                reponseBlog.setDate(rs.getString("date"));
                reponseBlog.setContenu(rs.getString("contenu"));

                reponseBlogList.add(reponseBlog);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponseBlogList;

    }


    @Override
    public void deleterep(int id) throws SQLException {
        String req = "DELETE FROM reponse_blog WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();

    }

    @Override
    public void editrep(ReponseBlog reponseBlog) throws SQLException {
        String req = "UPDATE reponse_blog SET date = ?, contenu = ?,  status = ? WHERE id = ?";
        PreparedStatement pstm = cnx.prepareStatement(req);
        pstm.setString(1, reponseBlog.getDate());
        pstm.setString(2, reponseBlog.getContenu());
        pstm.setInt(3,reponseBlog.getStatus());
        pstm.executeUpdate();






    }
}
