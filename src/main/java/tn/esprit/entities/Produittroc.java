package tn.esprit.entities;

public class Produittroc {

    private int id,id_user_id,statut;
    private String nom,category,description,image,nom_produit_recherche;

    public Produittroc(int id, int id_user_id, int statut, String nom, String category, String description, String image, String nom_produit_recherche) {
        this.id = id;
        this.id_user_id = id_user_id;
        this.statut = statut;
        this.nom = nom;
        this.category = category;
        this.description = description;
        this.image = image;
        this.nom_produit_recherche = nom_produit_recherche;
    }

    public Produittroc() {

    }

    public int getId() {
        return id;
    }

    public Produittroc(int id_user_id, int statut, String nom, String category, String description, String image, String nom_produit_recherche) {
        this.id_user_id = id_user_id;
        this.statut = statut;
        this.nom = nom;
        this.category = category;
        this.description = description;
        this.image = image;
        this.nom_produit_recherche = nom_produit_recherche;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user_id() {
        return id_user_id;
    }

    public void setId_user_id(int id_user_id) {
        this.id_user_id = id_user_id;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom_produit_recherche() {
        return nom_produit_recherche;
    }

    public void setNom_produit_recherche(String nom_produit_recherche) {
        this.nom_produit_recherche = nom_produit_recherche;
    }

    @Override
    public String toString() {
        return "Produittroc{" +
                ", id_user_id=" + id_user_id +
                ", statut=" + statut +
                ", nom='" + nom + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", nom_produit_recherche='" + nom_produit_recherche + '\'' +
                '}';
    }
}

