package tn.jardindart.models;

import java.util.Objects;

public class Panier {
  int idPanier ;
  User user ;

    public Panier() {
    }

    public Panier(int idPanier, User user) {
        this.idPanier = idPanier;
        this.user = user;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return idPanier == panier.idPanier && Objects.equals(user, panier.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPanier, user);
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", user=" + user +
                '}';
    }
}
