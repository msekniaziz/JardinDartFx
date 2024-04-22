package tn.esprit.ads.Entity;

import java.util.Date;
import java.util.Objects;

public class Commandes {
    int id , id_user_c_id , etat;
    Date date ;
    String name;

    public Commandes() {
    }

    public Commandes(int id_user_c_id) {
        this.id_user_c_id = id_user_c_id;
    }

    public Commandes(int id, int id_user_c_id, int etat, Date date) {
        this.id = id;
        this.id_user_c_id = id_user_c_id;
        this.etat = etat;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user_c_id() {
        return id_user_c_id;
    }

    public void setId_user_c_id(int id_user_c_id) {
        this.id_user_c_id = id_user_c_id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commandes commandes = (Commandes) o;
        return id == commandes.id && id_user_c_id == commandes.id_user_c_id && etat == commandes.etat && Objects.equals(date, commandes.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_user_c_id, etat, date);
    }

    @Override
    public String toString() {
        return "Commandes{" +
                "id=" + id +
                ", id_user_c_id=" + id_user_c_id +
                ", etat=" + etat +
                ", date=" + date +
                '}';
    }

    public boolean isSelected() {
        return false;
    }
}
