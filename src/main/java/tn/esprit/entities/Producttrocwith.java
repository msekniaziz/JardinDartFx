package tn.esprit.entities;

public class Producttrocwith {

    int id,prod_id_troc_id;
    String nom,category,description,image;

    public Producttrocwith(int id, int prod_id_troc_id, String nom, String category, String description, String image) {
        this.id = id;
        this.prod_id_troc_id = prod_id_troc_id;
        this.nom = nom;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public Producttrocwith(int prod_id_troc_id, String nom, String category, String description, String image) {
        this.prod_id_troc_id = prod_id_troc_id;
        this.nom = nom;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public Producttrocwith() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProd_id_troc_id() {
        return prod_id_troc_id;
    }

    public void setProd_id_troc_id(int prod_id_troc_id) {
        this.prod_id_troc_id = prod_id_troc_id;
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

    @Override
    public String toString() {
        return "Producttrocwith{" +
                "id=" + id +
                ", prod_id_troc_id=" + prod_id_troc_id +
                ", nom='" + nom + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
