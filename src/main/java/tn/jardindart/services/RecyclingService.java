package tn.jardindart.services;

import java.util.List;

public interface RecyclingService <ProdR>{
    void ajouter(tn.jardindart.models.ProdR pR);

    void modifier(tn.jardindart.models.ProdR pR);

    void supprimer(tn.jardindart.models.ProdR pR);

    List<tn.jardindart.models.ProdR> recuperer();
}
