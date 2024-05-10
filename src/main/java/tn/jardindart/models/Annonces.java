package tn.jardindart.models;

import java.util.Objects;

public class Annonces {

    int id , user_id ,id_Cat , Negociable , Status ;
    String Description , Title , Image , Category ;
    double Prix ;

    // Autres champs...
    Categories category; // Ajoutez un champ de type Category

    public Categories getCategorie() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Annonces() {
    }

    public Annonces(int id, int user_id, int id_Cat, int negociable, String description, String title, double prix) {
        this.id = id;
        this.user_id = user_id;
        this.id_Cat = id_Cat;
       this. Negociable = negociable;
        this.Description = description;
        this.Title = title;
      this.  Prix = prix;
    }

    public Annonces(int id, int id_Cat, int negociable, String description, String title, double prix) {
        this.id = id;
        this.id_Cat = id_Cat;
       this. Negociable = negociable;
        this.Description = description;
       this. Title = title;
       this. Prix = prix;
    }

    public Annonces(int id, int id_Cat, int negociable, int status, String description, String title, String category, double prix) {
        this.id = id;
        this.id_Cat = id_Cat;
        this.Negociable = negociable;
        this.Status = status;
        this.Description = description;
        this.Title = title;
       this. Category = category;
       this. Prix = prix;
    }

    public Annonces(int id, int id_Cat, int negociable, int status, String description, String title, String image, String category, double prix) {
        this.id = id;
        this.id_Cat = id_Cat;
       this. Negociable = negociable;
       this .Status = status;
        this.Description = description;
      this.  Title = title;
       this. Image = image;
       this. Category = category;
        this.Prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId_Cat() {
        return id_Cat;
    }

    public void setId_Cat(int id_Cat) {
        this.id_Cat = id_Cat;
    }

    public int getNegiciable() {
        return Negociable;
    }

    public void setNegiciable(int negociable) {
        Negociable = negociable;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annonces annonces = (Annonces) o;
        return id == annonces.id && user_id == annonces.user_id && id_Cat == annonces.id_Cat && Negociable == annonces.Negociable && Status == annonces.Status && Objects.equals(Description, annonces.Description) && Objects.equals(Title, annonces.Title) && Objects.equals(Image, annonces.Image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, id_Cat, Negociable, Status, Description, Title, Image);
    }

    @Override
    public String toString() {
        return "Annonces{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", id_Cat=" + id_Cat +
                ", Negociable=" + Negociable +
                ", Status=" + Status +
                ", Description='" + Description + '\'' +
                ", Title='" + Title + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public boolean isNegotiable() {
        return true;
    }


}
