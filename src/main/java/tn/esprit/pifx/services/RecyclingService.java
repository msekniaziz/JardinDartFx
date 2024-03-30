package tn.esprit.pifx.services;

import tn.esprit.pifx.models.ProdR;

import java.util.List;

public interface RecyclingService <ProdR>{
    void ajouter(tn.esprit.pifx.models.ProdR pR);

    void modifier(tn.esprit.pifx.models.ProdR pR);

    void supprimer(tn.esprit.pifx.models.ProdR pR);

    List<tn.esprit.pifx.models.ProdR> recuperer();
}
