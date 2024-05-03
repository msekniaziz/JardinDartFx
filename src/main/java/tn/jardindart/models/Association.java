package tn.jardindart.models;

import java.util.ArrayList;
import java.util.List;

public class Association {
    private int id,rib;
    private String nom_association,adresse_association,logo_association,description_asso;
    private List<DonBienMateriel> donBienMateriels = new ArrayList<>();
    private List<DonArgent> donArgents = new ArrayList<>();

    public Association() {
    }

    public Association(int id, int rib, String nom_association, String adresse_association, String logo_association, String description_asso){
        this.id = id;
        this.rib = rib;
        this.nom_association = nom_association;
        this.adresse_association = adresse_association;
        this.logo_association = logo_association;
        this.description_asso = description_asso;

    }

    public Association( String nom_association, String adresse_association, String logo_association, String description_asso,int rib){

        this.nom_association = nom_association;
        this.adresse_association = adresse_association;
        this.logo_association = logo_association;
        this.description_asso = description_asso;
        this.rib = rib;


    }
    public Association(int id, int rib, String nom_association, String adresse_association, String logo_association, String description_asso, List<DonBienMateriel> donBienMateriels, List<DonArgent> donArgents){
        this.id = id;
        this.rib = rib;
        this.nom_association = nom_association;
        this.adresse_association = adresse_association;
        this.logo_association = logo_association;
        this.description_asso = description_asso;
        this.donBienMateriels = donBienMateriels;
        this.donArgents = donArgents;
    }
    public Association(int rib, String nom_association, String adresse_association, String logo_association, String description_asso) {
        this.rib = rib;
        this.nom_association = nom_association;
        this.adresse_association = adresse_association;
        this.logo_association = logo_association;
        this.description_asso = description_asso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRib() {
        return rib;
    }

    public void setRib(int rib) {
        this.rib = rib;
    }

    public String getNom_association() {
        return nom_association;
    }

    public void setNom_association(String nom_association) {
        this.nom_association = nom_association;
    }

    public String getAdresse_association() {
        return adresse_association;
    }

    public void setAdresse_association(String adresse_association) {
        this.adresse_association = adresse_association;
    }

    public String getLogo_association() {
        return logo_association;
    }

    public void setLogo_association(String logo_association) {
        this.logo_association = logo_association;
    }

    public String getDescription_asso() {
        return description_asso;
    }

    public void setDescription_asso(String description_asso) {
        this.description_asso = description_asso;
    }

    public List<DonBienMateriel> getDonBienMateriels() {
        return donBienMateriels;
    }

    public void setDonBienMateriels(List<DonBienMateriel> donBienMateriels) {
        this.donBienMateriels = donBienMateriels;
    }

    public List<DonArgent> getDonArgents() {
        return donArgents;
    }

    public void setDonArgents(List<DonArgent> donArgents) {
        this.donArgents = donArgents;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", rib=" + rib +
                ", nom_association='" + nom_association + '\'' +
                ", adresse_association='" + adresse_association + '\'' +
                ", logo_association='" + logo_association + '\'' +
                ", description_asso='" + description_asso + '\'' +
                ", donBienMateriels=" + donBienMateriels +
                ", donArgents=" + donArgents +
                '}';
    }


}
