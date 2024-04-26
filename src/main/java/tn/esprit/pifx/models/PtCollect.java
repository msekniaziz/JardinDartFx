package tn.esprit.pifx.models;

import java.util.ArrayList;
import java.util.List;

public class PtCollect {


    private String nomPc;
    private int id;

    private String adressePc;

    private Float latitudePc;

    private Float longitudePc;

    private List<TypeDispo> type = new ArrayList<>();

    private List<ProdR> prodRs = new ArrayList<>();


    public PtCollect(int id, String nomPc, String adressePc, Float latitudePc, Float longitudePc, List<TypeDispo> type) {
       this.id=id;
        this.nomPc = nomPc;
        this.adressePc = adressePc;
        this.latitudePc = latitudePc;
        this.longitudePc = longitudePc;
        this.type = type;
    }


    public String getNomPc() {
        return nomPc;
    }

    public void setNomPc(String nomPc) {
        this.nomPc = nomPc;
    }

    public String getAdressePc() {
        return adressePc;
    }

    public void setAdressePc(String adressePc) {
        this.adressePc = adressePc;
    }

    public Float getLatitudePc() {
        return latitudePc;
    }

    public void setLatitudePc(Float latitudePc) {
        this.latitudePc = latitudePc;
    }

    public Float getLongitudePc() {
        return longitudePc;
    }

    public void setLongitudePc(Float longitudePc) {
        this.longitudePc = longitudePc;
    }

    public List<TypeDispo> getType() {
        return type;
    }

    public void setType(List<TypeDispo> type) {
        this.type = type;
    }

    public void addType(List<TypeDispo> type) {
        this.type.addAll(type);
    }

    public void removeType(TypeDispo type) {
        this.type.remove(type);
    }

    public List<ProdR> getProdRs() {
        return prodRs;
    }

    public void setProdRs(List<ProdR> prodRs) {
        this.prodRs = prodRs;
    }

    public void addProdR(ProdR prodR) {
        this.prodRs.add(prodR);
    }

    public void removeProdR(ProdR prodR) {
        this.prodRs.remove(prodR);
    }

    @Override
    public String toString() {
        return nomPc; // ou toute autre propriété à afficher
    }
    public PtCollect() {
    }

    public PtCollect( String nomPc, String adressePc, Float latitudePc, Float longitudePc, List<TypeDispo> type, List<ProdR> prodRs) {
        this.nomPc = nomPc;
        this.adressePc = adressePc;
        this.latitudePc = latitudePc;
        this.longitudePc = longitudePc;
        this.type = type;
        this.prodRs = prodRs;
    }
    public PtCollect( String nomPc, String adressePc, Float latitudePc, Float longitudePc) {
        this.nomPc = nomPc;
        this.adressePc = adressePc;
        this.latitudePc = latitudePc;
        this.longitudePc = longitudePc;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
