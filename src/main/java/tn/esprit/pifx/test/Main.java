package tn.esprit.pifx.test;

import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.services.ProdRService;
import tn.esprit.pifx.services.PtCollectService;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        //========================================================== Test de la méthode ajouter
        ProdRService prodRService = new ProdRService();
        ProdR nouveauProdR = new ProdR();
        nouveauProdR.setTypeProdId(1);
        nouveauProdR.setPtcId(2);
        nouveauProdR.setUserId(1);
        nouveauProdR.setStatut(1);
        nouveauProdR.setDescription("CASCADE");
        nouveauProdR.setNomP("Nom");
        nouveauProdR.setJustificatif("Chemin");
        prodRService.ajouter(nouveauProdR);
        System.out.println("Nouveau produit recyclé ajouté avec succès.");

        //========================================================== Test de la méthode recuperer
        List<ProdR> produitsRecycles = prodRService.recuperer();

//        System.out.println("Liste des produits recyclés :");
//        for (ProdR p : produitsRecycles) {
//            System.out.println(p.getId() + " - " + p.getNomP() + " - " + p.getDescription());
//        }

        //==========================================================Test de la méthode modifier

//        if (!produitsRecycles.isEmpty()) {
//            ProdR produitAModifier = produitsRecycles.get(0);
//            produitAModifier.setDescription("Nouvelle description");
//            produitAModifier.setNomP("Nouveau nom");
//            prodRService.modifier(produitAModifier);
//            System.out.println("Produit recyclé modifié avec succès.");
//        }

        //==========================================================Test de la méthode supprimer

//        if (!produitsRecycles.isEmpty()) {
//            ProdR produitASupprimer = produitsRecycles.get(0);
//            prodRService.supprimer(produitASupprimer);
//            System.out.println("Produit recyclé supprimé avec succès.");
//        }


// Création d'une instance de PtCollectService
        PtCollectService ptCollectService = new PtCollectService();
        List<PtCollect> pointsCollecte = ptCollectService.recuperer();

        //==================================================================================================================== Test de la méthode ajouter
        PtCollect nouveauPtCollect = new PtCollect();

//        nouveauPtCollect.setNomPc("Point de collecte test");
//        nouveauPtCollect.setAdressePc("Adresse du point de collecte test");
//        nouveauPtCollect.setLatitudePc(235.12345f);
//        nouveauPtCollect.setLongitudePc(210.98765f);
//        ptCollectService.ajouter(nouveauPtCollect);
//        System.out.println("Nouveau point de collecte ajouté avec succès.");

        // ==========================================================Test de la méthode recuperer
        System.out.println("Liste des points de collecte :");
        for (PtCollect p : pointsCollecte) {
        System.out.println(p.getId() + " - " + p.getNomPc() + " - " + p.getAdressePc());
        }

        //========================================================== Test de la méthode modifier
//        if (!pointsCollecte.isEmpty()) {
//            PtCollect ptCollectAModifier = pointsCollecte.get(0);
//            ptCollectAModifier.setNomPc("Modification1");
//            ptCollectAModifier.setAdressePc("Modification1");
//            ptCollectAModifier.setLongitudePc(12.1F);
//            ptCollectAModifier.setLatitudePc(13.1F);
//            ptCollectService.modifier(ptCollectAModifier);
//            System.out.println("Point de collecte modifié avec succès.");
//        }

        // ==========================================================Test de la méthode supprimer
//        if (!pointsCollecte.isEmpty()) {
//            int idPointCollecteASupprimer = pointsCollecte.get(0).getId();
//            ptCollectService.supprimer(idPointCollecteASupprimer);
//            System.out.println("Point de collecte supprimé avec succès.");
//        }
    }

    }
