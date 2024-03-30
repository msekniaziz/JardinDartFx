package tn.esprit.pifx.models;


import java.util.Date;

public class ProdR {

    private int id;

    private int userId;

    private int ptcId;

    private int typeProdId;

    private int statut;

    private String description;

    private String nomP;

    private String justificatif;


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPtcId() {
        return ptcId;
    }

    public void setPtcId(int ptcId) {
        this.ptcId = ptcId;
    }

    public int getTypeProdId() {
        return typeProdId;
    }

    public void setTypeProdId(int typeProdId) {
        this.typeProdId = typeProdId;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
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



    @Override
    public String toString() {
        return String.valueOf(id); // ou toute autre propriété à afficher
    }


    // Constructeur par défaut
    public ProdR() {
    }

    public ProdR( int userId, int ptcId, int typeProdId, int statut, String description, String nomP, String justificatif) {
        this.userId = userId;
        this.ptcId = ptcId;
        this.typeProdId = typeProdId;
        this.statut = statut;
        this.description = description;
        this.nomP = nomP;
        this.justificatif = justificatif;
    }
}
