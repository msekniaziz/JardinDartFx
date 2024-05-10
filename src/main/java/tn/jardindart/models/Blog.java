package tn.jardindart.models;

public class Blog {
    private int id, status, user_id;
    private String titre, contenu_blog, category;
    String image_blog;
    private int like_blog;


    // Constructeur par défaut



    public Blog() {
    }


// Constructeur avec des paramètres


    public Blog(int id, int status, int user_id, String titre, String contenu_blog, String category, String image_blog, int like_blog) {
        this.id = id;
        this.status = status;
        this.user_id = user_id;
        this.titre = titre;
        this.contenu_blog = contenu_blog;
        this.category = category;
        this.image_blog = image_blog;
        this.like_blog = like_blog;
    }

    public Blog(int status, String userId, String titre, String contenu_blog, String category) {
        this.status = status;
        this.titre = titre;
        this.contenu_blog = contenu_blog;
        this.category = category;
    }

    public static void setstatus(String nom) {
    }

    public static void settitre(String type) {
    }

    public static void setcategory(String status) {
    }

    public static void setcontenu_blog(float contenu) {
    }


    public int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu_blog() {
        return contenu_blog;
    }

    public void setContenu_blog(String contenu_blog) {
        this.contenu_blog = contenu_blog;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_blog() {
        return image_blog;
    }

    public void setImage_blog(String image_blog) {
        this.image_blog = image_blog;
    }



    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", status=" + status +
                ", user_id=" + user_id +
                ", titre='" + titre + '\'' +
                ", contenu_blog='" + contenu_blog + '\'' +
                ", category='" + category + '\'' +
                ", image_blog='" + image_blog + '\'' +
                ", like_blog=" + like_blog +
                '}';
    }



    public int getUser_id() {
  return user_id;  }
}


