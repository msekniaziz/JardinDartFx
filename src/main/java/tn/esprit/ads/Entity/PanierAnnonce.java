package tn.esprit.ads.Entity;

import java.util.Objects;

public class PanierAnnonce {
    int id;
    Annonces annonces;
    Panier panier;

    public PanierAnnonce() {
    }

    public PanierAnnonce(int id, Annonces annonces, Panier panier) {
        this.id = id;
        this.annonces = annonces;
        this.panier = panier;
    }

    public int getId() {
        return id;
    }

    public Annonces getAnnonces() {
        return annonces;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnnonces(Annonces annonces) {
        this.annonces = annonces;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanierAnnonce that = (PanierAnnonce) o;
        return id == that.id && Objects.equals(annonces, that.annonces) && Objects.equals(panier, that.panier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, annonces, panier);
    }

    @Override
    public String toString() {
        return "PanierAnnonce{" +
                "id=" + id +
                ", annonces=" + annonces +
                ", panier=" + panier +
                '}';
    }

}
