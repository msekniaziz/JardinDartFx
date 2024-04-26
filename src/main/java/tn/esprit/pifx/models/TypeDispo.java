package tn.esprit.pifx.models;

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
