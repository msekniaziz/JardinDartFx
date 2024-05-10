package tn.jardindart.models;

import java.util.ArrayList;
import java.util.List;

public class TypeDispo {

    private Integer id;

    private String nom;

    private List<PtCollect> ptC = new ArrayList<>();

    private List<ProdR> prodRType = new ArrayList<>();

    public TypeDispo(int typeProdId, String s) {
    }

    public TypeDispo() {

    }
    private TypeDispo parent;
    private List<TypeDispo> enfants;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<PtCollect> getPtC() {
        return ptC;
    }

    public void setPtC(List<PtCollect> ptC) {
        this.ptC = ptC;
    }

    public void addPtC(PtCollect ptC) {
        this.ptC.add(ptC);
        ptC.addType((List<TypeDispo>) this);
    }

    public void removePtC(PtCollect ptC) {
        this.ptC.remove(ptC);
        ptC.removeType(this);
    }
    // Méthode pour ajouter un enfant à ce type de disponibilité
    public void ajouterEnfant(TypeDispo enfant) {
        if (enfants == null) {
            enfants = new ArrayList<>();
        }
        enfants.add(enfant);
        //enfant.setParent(this); // Définir ce type de disponibilité comme parent de l'enfant
    }

    // Méthode pour vérifier si ce type de disponibilité a un parent spécifique
    public boolean hasParent(TypeDispo parentToCheck) {
        if (parent == null) {
            return false; // Pas de parent, donc le type ne peut pas avoir ce parent
        }
        if (parent.equals(parentToCheck)) {
            return true; // Le parent correspond
        }
        return parent.hasParent(parentToCheck); // Vérifier si le parent a lui-même le parent à vérifier
    }

    // Méthode pour vérifier si ce type de disponibilité a un enfant spécifique
    public boolean hasChild(TypeDispo childToCheck) {
        if (enfants == null) {
            return false; // Pas d'enfants, donc le type ne peut pas avoir cet enfant
        }
        for (TypeDispo enfant : enfants) {
            if (enfant.equals(childToCheck)) {
                return true; // L'enfant correspond
            }
            if (enfant.hasChild(childToCheck)) {
                return true; // Vérifier si l'enfant a lui-même l'enfant à vérifier
            }
        }
        return false; // Aucun enfant ne correspond
    }
    public List<ProdR> getProdRType() {
        return prodRType;
    }

    public void setProdRType(List<ProdR> prodRType) {
        this.prodRType = prodRType;
    }

    public void addProdRType(ProdR prodRType) {
        this.prodRType.add(prodRType);
    }

    public void removeProdRType(ProdR prodRType) {
        this.prodRType.remove(prodRType);
    }

    @Override
    public String toString() {
        return nom; // ou toute autre propriété à afficher
    }
}
