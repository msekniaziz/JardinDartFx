package tn.esprit.applicationgui.entites;

public class Blog {
        private int id, status;
        private String titre, contenu_blog, category;
        String image_blog;
    private int like_blog;


        // Constructeur par défaut



    public Blog() {
    }


    // Constructeur avec des paramètres


    public Blog(int id, int status, String titre, String contenu_blog, String category, String image_blog) {
        this.id = id;
        this.status = status;
        this.titre = titre;
        this.contenu_blog = contenu_blog;
        this.category = category;
        this.image_blog = image_blog;
    }

    public Blog(int status, String titre, String contenu_blog, String category) {
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

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", status=" + status +
                ", titre='" + titre + '\'' +
                ", contenu_blog='" + contenu_blog + '\'' +
                ", category='" + category + '\'' +
                ", image_blog='" + image_blog + '\'' +
                '}';
    }


}


