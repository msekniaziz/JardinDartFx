package tn.esprit.applicationgui.Controllers;


import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Blogservice implements Service<Blog>{
  private  Connection cnx;

    public Blogservice(){
        cnx= MyDataBase.getInstance().getConx();

    }


    @Override
    public void addblog(Blog blog) throws SQLException {
        String req = "INSERT INTO blog (status, titre, contenu_blog, category,image_blog) VALUES ('" + blog.getStatus() + "','" + blog.getTitre() + "','" + blog.getContenu_blog() + "','" + blog.getCategory() + "','" + blog.getImage_blog() + "')";
        Statement stm = cnx.createStatement();
        stm.executeUpdate(req);
        System.out.println("blog  ajoutée");

    }

    @Override
    public List<Blog> showblog() throws SQLException {

        List<Blog> blogList = new ArrayList<>();
        String qry = "SELECT * FROM blog";

        try (  PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                 Blog blog = new Blog(1, "blog", "garden", "blog1");
                blog.setId(rs.getInt("id"));
                blog.setStatus(rs.getInt("status"));
                blog.setTitre(rs.getString("titre"));
                blog.setContenu_blog(rs.getString("contenu_blog"));
                blog.setCategory(rs.getString("category"));
                blog.setImage_blog(rs.getString("image_blog"));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blogList;
    }

    @Override
    public void deleteblog(int id) throws SQLException {
        String req = "DELETE FROM blog WHERE id = ?";

      PreparedStatement ps = cnx.prepareStatement(req);
      ps.setInt(1,id);
      ps.executeUpdate();

    }

    @Override
    public void editblog(Blog blog) throws SQLException {
        String req = "UPDATE blog SET status = ?, titre = ?, contenu_blog = ?, category = ? , image_blog = ? , WHERE id = ?";
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, blog.getStatus());
            pstm.setString(2, blog.getTitre());
            pstm.setString(3, blog.getContenu_blog());
            pstm.setString(4, blog.getCategory());
            pstm.setString(5, blog.getImage_blog());
            pstm.setInt(6, blog.getId());
            pstm.executeUpdate();
        }
    }









