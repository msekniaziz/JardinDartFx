package tn.esprit.ads.Entity;

import java.util.Objects;

public class Categories {
    int id;
    String name;
    int key_cat;
    int nbr_annonce;

    public Categories() {
    }

    public Categories(int id, String name, int key_cat, int nbr_annonce) {
        this.id = id;
        this.name = name;
        this.key_cat = key_cat;
        this.nbr_annonce = nbr_annonce;
    }


    public Categories(String name, int key_cat) {
        this.name = name;
        this.key_cat = key_cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey_cat() {
        return key_cat;
    }

    public void setKey_cat(int key_cat) {
        this.key_cat = key_cat;
    }

    public int getNbr_annonce() {
        return nbr_annonce;
    }

    public void setNbr_annonce(int nbr_annonce) {
        this.nbr_annonce = nbr_annonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return id == that.id && key_cat == that.key_cat && nbr_annonce == that.nbr_annonce && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, key_cat, nbr_annonce);
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key_cat=" + key_cat +
                ", nbr_annonce=" + nbr_annonce +
                '}';
    }

    public boolean isSelected() {
        return false;
    }
}
