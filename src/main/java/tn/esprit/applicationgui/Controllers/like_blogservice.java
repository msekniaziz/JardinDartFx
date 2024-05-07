package tn.esprit.applicationgui.Controllers;

import tn.esprit.applicationgui.utils.MyDataBase;
import tn.esprit.applicationgui.entites.like_blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class like_blogservice implements likeservice<like_blog> {

    private Connection cnx;
    private like_blog book;

    public like_blogservice() {
        cnx = MyDataBase.getInstance().getConx();

    }



    @Override
    public void addlike(like_blog like_blog) throws SQLException {
        String req = "INSERT INTO like_blog (id, like_blog, blog_id, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = cnx.prepareStatement(req);
        stm.setInt(1, like_blog.getId());
        stm.setInt(2, like_blog.getLike_blog());
        stm.setInt(3, like_blog.getBlog_id());
        stm.setInt(4, like_blog.getUser_id());
        stm.executeUpdate();
        System.out.println("Reponse ajoutée");

    }



    @Override
    public List<like_blog> showlike(int user_id) throws SQLException {
        List<like_blog> likeBlogList = new ArrayList<>();
        String qry = "SELECT * FROM like_blog";
        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                like_blog like;
                like = new like_blog(1, book.getId(), user_id);
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
}