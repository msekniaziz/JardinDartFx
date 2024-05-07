package tn.esprit.applicationgui.entites;

public class like_blog {
    private int id,like_blog,blog_id,user_id;

//    public like_blog() {
//        this.id = id;
//        this.like_blog = like_blog;
//        this.blog_id = blog_id;
//        this.user_id = user_id;
//    }
//
//    public like_blog(int id, int like_blog) {
//        this.id = id;
//        this.like_blog = like_blog;
//    }


    public like_blog(int id, int like_blog, int blog_id, int user_id) {
        this.id = id;
        this.like_blog = like_blog;
        this.blog_id = blog_id;
        this.user_id = user_id;
    }

    public like_blog(int i, int id, int user_id) {

    }

    @Override
    public String toString() {
        return "like_blog{" +
                "id=" + id +
                ", like_blog=" + like_blog +
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

    public int getLike_blog() {
        return like_blog;
    }

    public void setLike_blog(int like_blog) {
        this.like_blog = like_blog;
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
