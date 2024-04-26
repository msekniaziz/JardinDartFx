package tn.esprit.pifx.models;


import java.util.Date;

public class ProdR {

    private Integer id,userId,ptcId,typeProdId;

    private Boolean statut;

    private String description,nomP,justificatif,nomPtcId,nomTypeProd,nomUtilisateur;



    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPtcId() {
        return ptcId;
    }

    public void setPtcId(Integer ptcId) {
        this.ptcId = ptcId;
    }

    public int getTypeProdId() {
        return typeProdId;
    }

    public void setTypeProdId(Integer typeProdId) {
        this.typeProdId = typeProdId;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNomPtcId() {
        return nomPtcId;
    }

    public void setNomPtcId(String nomPtcId) {
        this.nomPtcId = nomPtcId;
    }

    public String getNomTypeProd() {
        return nomTypeProd;
    }

    public void setNomTypeProd(String nomTypeProd) {
        this.nomTypeProd = nomTypeProd;
    }
    @Override
    public String toString() {
        return String.valueOf(id); // ou toute autre propriété à afficher
    }


    // Constructeur par défaut
    public ProdR() {
    }

    public ProdR( Integer userId, Integer ptcId, Integer typeProdId, Boolean statut, String description, String nomP, String justificatif) {
        this.userId = userId;
        this.ptcId = ptcId;
        this.typeProdId = typeProdId;
        this.statut = statut;
        this.description = description;
        this.nomP = nomP;
        this.justificatif = justificatif;
    }    public ProdR( Integer id ,Integer userId, Integer ptcId, Integer typeProdId, Boolean statut, String description, String nomP, String justificatif) {
        this.id = id;
        this.userId = userId;
        this.ptcId = ptcId;
        this.typeProdId = typeProdId;
        this.statut = statut;
        this.description = description;
        this.nomP = nomP;
        this.justificatif = justificatif;
    }
}
