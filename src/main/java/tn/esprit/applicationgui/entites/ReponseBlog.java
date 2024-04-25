package tn.esprit.applicationgui.entites;

public class ReponseBlog {
    private String date;
   private String contenu ;
    private int status ;
    private int id;


    public ReponseBlog(String date, String contenu, int status) {
        this.date = date;
        this.contenu = contenu;
        this.status = status;
        this.id = id;
    }

    public ReponseBlog() {
       
    }

    @Override
    public String toString() {
        return "ReponseBlog{" +
                "date=" + date +
                ", contenu='" + contenu + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


    public String getContenu() {
        return contenu;
    }

    public void setId(int id) {
    }
}
