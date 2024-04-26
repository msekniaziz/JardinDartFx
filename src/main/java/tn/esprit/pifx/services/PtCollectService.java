package tn.esprit.pifx.services;

import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.utils.MyDataBase;

import java.sql.*;
import java.util.*;

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

            // Vérifier si nomPc n'est pas null
            String nomPc = ptCollect.getNomPc() != null ? ptCollect.getNomPc() : "";
            String addressePc = ptCollect.getAdressePc() != null ? ptCollect.getAdressePc() : "";

            // Vérifier si la latitude et la longitude ne sont pas null
            float latitude = ptCollect.getLatitudePc() != null ? ptCollect.getLatitudePc() : 0.0f;
            float longitude = ptCollect.getLongitudePc() != null ? ptCollect.getLongitudePc() : 0.0f;

            pstm.setString(1, nomPc);
            pstm.setString(2, addressePc);
            pstm.setFloat(3, latitude);
            pstm.setFloat(4, longitude);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du point de collecte : " + e.getMessage());
        }
    }

    public void ajouterAvecType(PtCollect ptCollect) {
        // Insérer dans la table de jointure PtCollect_TypeDispo
        String reqInsert = "INSERT INTO pt_collect_type_dispo (pt_collect_id, type_dispo_id) VALUES ";
        StringBuilder values = new StringBuilder();

        try {
            // Construction de la requête avec des paramètres dynamiques
            reqInsert += "(?, ?)";
            for (int i = 0; i < ptCollect.getType().size() - 1; i++) {
                reqInsert += ", (?, ?)";
            }

            PreparedStatement pstm = cnx.prepareStatement(reqInsert);
            int index = 1;
            for (TypeDispo typeDisp : ptCollect.getType()) {
                // Associer l'ID du point de collecte avec l'ID du type de disponibilité
                pstm.setInt(index++, ptCollect.getId()); // ID du point de collecte
                pstm.setInt(index++, typeDisp.getId());  // ID du type de disponibilité
            }

            // Exécution de la requête
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout des associations entre PtCollect et TypeDispo : " + e.getMessage());
        }
    }

    private void supprimerAssociations(int ptCollectId) {
        String req = "DELETE FROM `pt_collect_type_dispo` WHERE `pt_collect_id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, ptCollectId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des associations : " + e.getMessage());
        }
    }
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

    public List<PtCollect> recupererT() {
        List<PtCollect> ptCollectList = new ArrayList<>();
        String req = "SELECT pt.*, GROUP_CONCAT(td.nom) AS Types_Disponibles " +
                "FROM pt_collect pt " +
                "JOIN pt_collect_type_dispo ptd ON pt.id = ptd.pt_collect_id " +
                "JOIN type_dispo td ON ptd.type_dispo_id = td.id " +
                "GROUP BY pt.id";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                PtCollect ptCollect = new PtCollect();
                ptCollect.setId(rs.getInt("id"));
                ptCollect.setNomPc(rs.getString("nom_pc"));
                ptCollect.setAdressePc(rs.getString("adresse_pc"));
                ptCollect.setLatitudePc(rs.getFloat("latitude_pc"));
                ptCollect.setLongitudePc(rs.getFloat("longitude_pc"));

                // Récupérer les types de disponibilité en tant que chaîne
                String typesDisponibles = rs.getString("Types_Disponibles");

                // Diviser la chaîne en une liste de noms de types de disponibilité
                List<String> typesDisponiblesNames = Arrays.asList(typesDisponibles.split(","));

                // Créer une liste d'objets TypeDispo à partir des noms
                List<TypeDispo> typesDisponiblesList = new ArrayList<>();
                for (String typeName : typesDisponiblesNames) {
                    TypeDispo typeDispo = new TypeDispo();
                    typeDispo.setNom(typeName);
                    typesDisponiblesList.add(typeDispo);
                }

                // Mettre à jour la liste des types de disponibilité pour le point de collecte
                ptCollect.setType(typesDisponiblesList);

                ptCollectList.add(ptCollect);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des points de collecte : " + e.getMessage());
        }
        return ptCollectList;
    }
    public int recupererDernierIdPtCollect() {
        int dernierId = 0;
        String req = "SELECT MAX(id) AS dernierId FROM pt_collect";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                dernierId = rs.getInt("dernierId");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du dernier ID de point de collecte : " + e.getMessage());
        }
        return dernierId;
    }
    // Update
    public void modifier(PtCollect ptCollect) {
        supprimerAssociations(ptCollect.getId());

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

    public PtCollect getById(int id) {
        ArrayList<PtCollect> allP = (ArrayList<PtCollect>) recuperer(); // Récupérer tous les prodr

        // Parcourir la liste des prodr et comparer l'ID
        for (PtCollect pr : allP) {
            if (pr.getId() == id) {
                return pr; // Retourner le don s'il correspond à l'ID recherché
            }
        }

        return null; // Retourner null si aucun don avec l'ID spécifié n'est trouvé
    }
    public Map<String, Integer> recupererStatistiquesTypes() {
        Map<String, Integer> statistiquesTypes = new HashMap<>();
        String req = "SELECT td.nom, COUNT(*) AS nombre FROM pt_collect pc " +
                "JOIN pt_collect_type_dispo ptd ON pc.id = ptd.pt_collect_id " +
                "JOIN type_dispo td ON ptd.type_dispo_id = td.id " +
                "GROUP BY td.nom";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String nomType = rs.getString("nom");
                int nombre = rs.getInt("nombre");
                statistiquesTypes.put(nomType, nombre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des statistiques sur les types disponibles : " + e.getMessage());
        }
        return statistiquesTypes;
    }
}
