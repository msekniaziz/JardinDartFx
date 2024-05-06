package tn.jardindart.services;

import tn.jardindart.models.Panier;
import tn.jardindart.utils.MyDataBase;


import java.sql.*;

public class Spanier {
    public static Connection cnx;
    private PreparedStatement pst;

    public Spanier(){cnx = MyDataBase.getInstance().getCnx();}

    public void ajouterPanier(int idUser)
    {
        String requete = "insert into panier (idUser) values (?)";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, idUser);
            pst.executeUpdate();
            System.out.println("Panier ajoutée!");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }



    public Panier selectPanierParUserId(int idUser) {
        Panier panier = null;
        String requete = "SELECT * FROM panier WHERE idUser = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, idUser);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                panier = new Panier();
                panier.setIdPanier(rs.getInt("idPanier"));
                // Ajoutez d'autres attributs du panier en fonction de votre modèle de données
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return panier;
    }
}
