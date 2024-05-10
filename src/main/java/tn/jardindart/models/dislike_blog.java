package tn.jardindart.models;

public class dislike_blog {
    private int id,dislike_blogs_id,blog_id,user_id;

    public dislike_blog(int id, int dislike_blogs_id, int blog_id, int user_id) {
        this.id = id;
        this.dislike_blogs_id = dislike_blogs_id;
        this.blog_id = blog_id;
        this.user_id = user_id;
    }

    public dislike_blog() {

    }

    @Override
    public String toString() {
        return "dislike_blog{" +
                "id=" + id +
                ", dislike_blogs_id=" + dislike_blogs_id +
                ", blog_id=" + blog_id +
                ", user_id=" + user_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  int getDislike_blogs_id() {
        return dislike_blogs_id;
    }

    public void setDislike_blogs_id(int dislike_blogs_id) {
        this.dislike_blogs_id = dislike_blogs_id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
