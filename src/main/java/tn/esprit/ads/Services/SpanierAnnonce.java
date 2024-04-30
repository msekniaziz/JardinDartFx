package tn.esprit.ads.Services;

import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.PanierAnnonce;
import tn.esprit.ads.tools.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpanierAnnonce {
    public static Connection cnx;
    public SpanierAnnonce(){cnx = MyDataBase.getInstance().getCnx();}
    public void ajouterProduitAuPanier(int idPanier, int idAnnonce) {
        String requete = "INSERT INTO panierannonce (idPanier,idAnnonce) VALUES (?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, idPanier);
            pst.setInt(2, idAnnonce);

            pst.executeUpdate();
            System.out.println("Produit ajouté au panier!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    Sannonces annonces = new Sannonces();
   /* public ArrayList<Annonces> getCard(int idPanier) {
        ArrayList<Annonces> annonces = new ArrayList<>();
        String query = "SELECT idAnnonce FROM panierannonce WHERE idPanier = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, idPanier);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int idAnnonce = rs.getInt("idAnnonce");
                 Annonces annonce = new Annonces();
                Sannonces ads = new Sannonces();
                ads.getAnnonceById(idAnnonce); // Méthode à implémenter
                ads.add(annonce);
            }
        } catch (SQLException e) {
            // Lancer une nouvelle RuntimeException avec l'exception d'origine
            throw new RuntimeException("Erreur lors de la récupération des annonces.", e);
        }
        return annonces;
    }*/
   public ArrayList<Annonces> getCard(int idPanier) {
       ArrayList<Annonces> annonces = new ArrayList<>();
       String query = "SELECT idAnnonce FROM panierannonce WHERE idPanier = ?";
       try {
           PreparedStatement stm = cnx.prepareStatement(query);
           stm.setInt(1, idPanier);
           ResultSet rs = stm.executeQuery();
           while (rs.next()) {
               int idAnnonce = rs.getInt("idAnnonce");
               Annonces annonce = getAnnonceById(idAnnonce); // Récupérer l'annonce à partir de l'ID
               if (annonce != null) {
                   annonces.add(annonce); // Ajouter l'annonce à la liste
               }
           }
       } catch (SQLException e) {
           throw new RuntimeException("Erreur lors de la récupération des annonces.", e);
       }
       return annonces;
   }
    public Annonces getAnnonceById(int idAnnonce) {
        Annonces annonce = null;
        String query = "SELECT id, user_id, title, prix, image FROM annonces WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, idAnnonce);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                annonce = new Annonces();
                annonce.setId(rs.getInt("id"));
                annonce.setUser_id(rs.getInt("user_id"));
                annonce.setTitle(rs.getString("title"));
                annonce.setPrix(rs.getFloat("prix"));
                annonce.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'annonce avec l'ID : " + idAnnonce, e);
        }
        return annonce;
    }

    public void DeleteProduitAuPanier( int idAds) {
        String query = "DELETE FROM panierannonce where idAnnonce= ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm=cnx.prepareStatement(query);
            stm.setInt(1, idAds);
            stm.executeUpdate();
            System.out.println("produit supprimé de votre panier!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des produits du panier");

            throw new RuntimeException(e);
        }
    }
    public void deleteAllAnnoncesDansPanier(int idPanier) {
        String query = "DELETE FROM panierannonce WHERE idPanier = ?";

        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setInt(1, idPanier);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Toutes les annonces du panier ont été supprimées!");
            } else {
                System.out.println("Aucune annonce trouvée dans le panier pour cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des annonces du panier : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
