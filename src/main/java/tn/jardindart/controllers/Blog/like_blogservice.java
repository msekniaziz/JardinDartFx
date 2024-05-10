package tn.jardindart.controllers.Blog;

import tn.jardindart.utils.MyDataBase;
import tn.jardindart.models.like_blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class like_blogservice implements likeservice<like_blog> {

    private Connection cnx;

    public like_blogservice() {
        cnx = MyDataBase.getInstance().getCnx();

    }


    @Override
    public void addlike(like_blog like_blog) throws SQLException {
        String req = "INSERT INTO like_blog ( like_blog, blog_id, user_id) VALUES (?, ?, ?)";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, like_blog.getLike_blog());
        stm.setInt(2, like_blog.getBlog_id());
        stm.setInt(3, like_blog.getUser_id());
        stm.executeUpdate();
        System.out.println("Reponse ajoutée");

    }



    @Override
    public List<like_blog> showlike() throws SQLException {
        List<like_blog> likeBlogList = new ArrayList<>();
        String qry = "SELECT * FROM like_blog";
        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                like_blog like = new like_blog();
                like.setId(rs.getInt("id"));
                like.setLike_blog(rs.getInt("like_blog"));
                like.setBlog_id(rs.getInt("blog_id"));
                like.setUser_id(rs.getInt("user_id"));
                likeBlogList.add(like);
            }
        }
        return likeBlogList;
    }


    @Override
    public void deletelike(int id) throws SQLException {
        String req = "DELETE FROM like_blog WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    @Override
    public void editlike(like_blog like_blog) throws SQLException {

        String req = "UPDATE like_blog SET  like_blog = ? WHERE id = ?";
        PreparedStatement pstm = cnx.prepareStatement(req);
        pstm.setInt(1, like_blog.getLike_blog());
        pstm.executeUpdate();

//        if (tableView != null) {
//            // Update the data source of the TableView
//            ObservableList<Blog> Cardblog = tableView.getItems();
//            Cardblog.setAll(showlike());
//        }
//

    }

    public int countLikesByBlogId(int blogId) throws SQLException {
        String req = "SELECT COUNT(*) FROM like_blog WHERE blog_id = ?";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, blogId);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }

}