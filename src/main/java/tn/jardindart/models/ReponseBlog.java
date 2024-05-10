package tn.jardindart.models;

import java.time.LocalDate;

public class ReponseBlog {
    private LocalDate date;
   private String contenu ;
    private int id, id_user_reponse_id;
private int blog_id;

    public ReponseBlog(LocalDate date, String contenu, int id, int blog_id, Blog blog) {
        this.date = date;
        this.contenu = contenu;
        this.id = id;
        this.blog_id = blog_id;
        this.blog = blog;
    }

    public int getId_user_reponse_id() {
        return id_user_reponse_id;
    }

    public void setId_user_reponse_id(int id_user_reponse_id) {
        this.id_user_reponse_id = id_user_reponse_id;
    }
//    private Blog blog;

    public ReponseBlog() {
       
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    @Override
    public String toString() {
        return "ReponseBlog{" +
                "date=" + date +
                ", contenu='" + contenu + '\'' +
                ", id=" + id +
                ", id_user_reponse_id=" + id_user_reponse_id +
                ", blog_id=" + blog_id +
                ", blog=" + blog +
                '}';
    }

    //    public Blog getBlog() {
//        return blog;
//    }
//
//    public void setBlog(Blog blog) {
//        this.blog = blog;
//    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setDate(String  date) {
//        this.date = date;
//    }



    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


    public String getContenu() {
        return contenu;
    }

    public void setId(int id) {
    }
public  Blog blog;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public int getId() {
        return id;
    }
}
