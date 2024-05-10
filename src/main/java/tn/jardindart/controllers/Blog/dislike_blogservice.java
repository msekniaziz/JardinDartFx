package tn.jardindart.controllers.Blog;

import tn.jardindart.models.dislike_blog;
import tn.jardindart.models.like_blog;
import tn.jardindart.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dislike_blogservice implements dislikeservice<dislike_blog>{


    private Connection cnx;

    public dislike_blogservice() {
        cnx = MyDataBase.getInstance().getCnx();

    }


    @Override
    public void adddislike(dislike_blog dislikeBlog) throws SQLException {
        String req = "INSERT INTO dislike_blog (id, dislike_blogs_id, blog_id, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, dislikeBlog.getId());
        stm.setInt(2, dislikeBlog.getDislike_blogs_id());
        stm.setInt(3, dislikeBlog.getBlog_id());
        stm.setInt(4, dislikeBlog.getUser_id());
        stm.executeUpdate();
        System.out.println("Dislike blog added successfully");
    }

    @Override
    public List<dislike_blog> showdislike() throws SQLException {

        List<dislike_blog> dislikeBlogList = new ArrayList<>();
        String qry = "SELECT * FROM dislike_blog";
        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                dislike_blog dislike = new dislike_blog();
                dislike.setId(rs.getInt("id"));
                dislike.setDislike_blogs_id(rs.getInt("dislike_blogs_id"));
                dislike.setBlog_id(rs.getInt("blog_id"));
                dislike.setUser_id(rs.getInt("user_id"));
                dislikeBlogList.add(dislike);
            }
        }
        return dislikeBlogList;


    }

    @Override
    public void deletedislike(int id) throws SQLException {
        String req = "DELETE FROM dislike_blog WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void editdislike(dislike_blog dislikeBlog) throws SQLException {
//        String req = "UPDATE dislike_blog SET  dislike_blog = ? WHERE id = ?";
//        PreparedStatement pstm = cnx.prepareStatement(req);
//        pstm.setInt(1, dislike_blog.getDislike_blogs_id());
//        pstm.executeUpdate();
    }

    public int countdisLikesByBlogId(int blogId) throws SQLException {
        String req = "SELECT COUNT(*) FROM dislike_blog WHERE blog_id = ?";
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
