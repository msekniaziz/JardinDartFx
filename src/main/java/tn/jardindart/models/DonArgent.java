package tn.jardindart.models;

import java.sql.*;

public class DonArgent {
    private int id,id_association,user_id;
    private double montant;
    private String nom_assoc,nom_ut;
    private Date dateDA;
    //private Association id_association;
   // private User user_id;

    public DonArgent() {
    }

    public DonArgent(int id, double montant, int id_association, int user_id) {
        this.id = id;
        this.montant = montant;
        //this.dateDA = dateDA;
        this.id_association = id_association;
        this.user_id = user_id;
    }

    public DonArgent(Integer userId, double montant,  int id_association) {
        this.user_id = userId;
        this.montant = montant;
        //this.dateDA = dateDA;
        this.id_association = id_association;

    }

    public DonArgent(double montant) {
        this.montant = montant;
        //this.dateDA = dateDA;
    }

    public DonArgent(double montant, int id_association) {
        this.montant=montant;
        this.id_association=id_association;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDateDA() {
        return dateDA;
    }

    public void setDateDA(Date dateDA) {
        this.dateDA = dateDA;
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
        return "DonArgent{" +
                "id=" + id +
                ", id_association=" + id_association +
                ", user_id=" + user_id +
                ", montant=" + montant +
                ", dateDA=" + dateDA +
                '}';
    }
}
