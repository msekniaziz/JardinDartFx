package tn.esprit.jardindart.models;
import javafx.scene.control.DatePicker;

import java.sql.*;
import java.time.LocalDate;


public class DonBienMateriel {
    private int id,id_association,user_id;
    private String desc_don,photo_don,nom_assoc,nom_ut;
    private boolean statut_don;
    //private LocalDate dateDB;
    //private Association id_association;
    //private User user_id;

    public DonBienMateriel() {
    }
    public DonBienMateriel( String desc_don, String photo_don, boolean statut_don, int id_association, int user_id,String nom_assoc,String nom_ut ) {

        this.desc_don = desc_don;
        this.photo_don = photo_don;
        this.statut_don = statut_don;
        // this.dateDB = dateDB;
        this.id_association = id_association;
        this.user_id = user_id;
        this.nom_assoc=nom_assoc;
        this.nom_ut=nom_ut;
    }

    public DonBienMateriel( String desc_don, String photo_don, boolean statut_don, int id_association,int user_id) {

        this.desc_don = desc_don;
        this.photo_don = photo_don;
        this.statut_don = statut_don;
        // this.dateDB = dateDB;
        this.id_association = id_association;
        this.user_id = user_id;
    }
    public DonBienMateriel(int id, String desc_don, String photo_don, boolean statut_don, int id_association, int user_id,String nom_assoc,String nom_ut) {
        this.id = id;
        this.desc_don = desc_don;
        this.photo_don = photo_don;
        this.statut_don = statut_don;
      //  this.dateDB = dateDB;
        this.id_association = id_association;
        this.user_id = user_id;
        this.nom_assoc=nom_assoc;
        this.nom_ut=nom_ut;

    }

    public DonBienMateriel(String desc_don, String photo_don, boolean statut_don,String nom_assoc,String nom_ut) {
        this.desc_don = desc_don;
        this.photo_don = photo_don;
        this.statut_don = statut_don;
        this.nom_assoc=nom_assoc;
        this.nom_ut=nom_ut;
        //this.dateDB = dateDB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc_don() {
        return desc_don;
    }

    public void setDesc_don(String desc_don) {
        this.desc_don = desc_don;
    }

    public String getPhoto_don() {
        return photo_don;
    }

    public void setPhoto_don(String photo_don) {
        this.photo_don = photo_don;
    }

    public boolean isStatut_don() {
        return statut_don;
    }

    public void setStatut_don(boolean statut_don) {
        this.statut_don = statut_don;
    }




    public int getId_association() {
        return id_association;
    }

    public void setId_association(int id_association) {
        this.id_association = id_association;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNom_assoc() {
        return nom_assoc;
    }

    public void setNom_assoc(String nom_assoc) {
        this.nom_assoc = nom_assoc;
    }

    public String getNom_ut() {
        return nom_ut;
    }

    public void setNom_ut(String nom_ut) {
        this.nom_ut = nom_ut;
    }

    @Override
    public String toString() {
        return "DonBienMateriel{" +
                "id=" + id +
                ", id_association=" + id_association +
                ", user_id=" + user_id +
                ", desc_don='" + desc_don + '\'' +
                ", photo_don='" + photo_don + '\'' +
                ", nom_assoc='" + nom_assoc + '\'' +
                ", nom_ut='" + nom_ut + '\'' +
                ", statut_don=" + statut_don +
                '}';
    }
}
