package tn.esprit.applicationgui.Controllers;

import javafx.scene.control.TableView;
import  tn.esprit.applicationgui.Controllers.Cardblog;
import javafx.collections.ObservableList;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.utils.MyDataBase;

import java.net.http.HttpClient;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.http.impl.client.HttpClients;

import javafx.scene.control.Alert;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Blogservice implements Service<Blog>{
  private  Connection cnx;
    private CloseableHttpClient httpClient; // Déclaration de la variable ici

    public Blogservice(){
        cnx= MyDataBase.getInstance().getConx();
        httpClient = HttpClients.createDefault(); // Initialisation de la variable

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
        String qry = "SELECT * FROM blog WHERE status=1";
//        PreparedStatement pre = cnx.prepareStatement(qry);
//        pre.setInt(1, 1);

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
    public void updatestatus(Blog blog)
            throws SQLException   {

        String req = "UPDATE `blog` SET `status`=? WHERE `id`=?";
        PreparedStatement pstm = cnx.prepareStatement(req);

        pstm = cnx.prepareStatement(req);

        pstm.setInt(1, blog.getStatus());
        pstm.setInt(2, blog.getId());

        pstm.executeUpdate();
        System.out.println("Statut du produit modifié avec succès");
    }
    @Override
    public void deleteblog(int id) throws SQLException {
        String req = "DELETE FROM blog WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public List<Blog> searchBlogByTitle(String title) throws SQLException {
        List<Blog> blogList = showblog(); // Fetch all blog entries

        List<Blog> searchResults = new ArrayList<>();

        // Loop through the blog entries to find the ones matching the title
        for (Blog blog : blogList) {
            if (blog.getTitre().toLowerCase().contains(title.toLowerCase())) {
                searchResults.add(blog);
            }
        }

        return searchResults;
    }
    public List<Blog> searchEmplacement(String searchTerm) throws SQLException {
        List<Blog> blogList = new ArrayList<>();
        String qry = "SELECT * FROM blog WHERE titre LIKE ? OR contenu_blog LIKE ? OR category LIKE ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            for (int i = 1; i <= 3; i++) {
                pstm.setString(i, "%" + searchTerm + "%");
            }
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setTitre(rs.getString("titre"));
                    blog.setContenu_blog(rs.getString("contenu_blog"));
                    blog.setCategory(rs.getString("category"));
                    blogList.add(blog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogList;
    }






//
//    @Override
//    public void EditBlog(Blog blog) throws SQLException {
//        String req = "UPDATE blog SET status = ?, titre = ?, contenu_blog = ?, category = ?, image_blog = ? WHERE id = ?";
//        PreparedStatement pstm = cnx.prepareStatement(req);
//        pstm.setInt(1, blog.getStatus());
//        pstm.setString(2, blog.getTitre());
//        pstm.setString(3, blog.getContenu_blog());
//        pstm.setString(4, blog.getCategory());
//        pstm.setString(5, blog.getImage_blog());
//        pstm.setInt(6, blog.getId());
//        pstm.executeUpdate();
//    }

    private TableView<Blog> tableView;

    // Setter method to set the TableView reference
    public void setTableView(TableView<Blog> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void EditBlog(Blog blog) throws SQLException {
        String req = "UPDATE blog SET status = ?, titre = ?, contenu_blog = ?, category = ?, image_blog = ? WHERE id = ?";
        PreparedStatement pstm = cnx.prepareStatement(req);
        pstm.setInt(1, blog.getStatus());
        pstm.setString(2, blog.getTitre());
        pstm.setString(3, blog.getContenu_blog());
        pstm.setString(4, blog.getCategory());
        pstm.setString(5, blog.getImage_blog());
        pstm.setInt(6, blog.getId());
        pstm.executeUpdate();

        // Refresh TableView after edit
        if (tableView != null) {
            // Update the data source of the TableView
            ObservableList<Blog> Cardblog = tableView.getItems();
            Cardblog.setAll(showblog());
        }
    }

    //back
      public List<Blog> showblogback() throws SQLException {

          List<Blog> blogList = new ArrayList<>();
          String qry = "SELECT * FROM blog Where status=0 ";
//        PreparedStatement pre = cnx.prepareStatement(qry);
//        pre.setInt(1, 1);

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
//    public List<Blog> getTop3Blogs(List<Blog> blogs) {
//        blogs.sort(Comparator.comparingInt(Blog::getlike_blog).reversed());
//
//        List<Blog> top3Blogs = new ArrayList<>();
//        for (int i = 0; i < Math.min(3, blogs.size()); i++) {
//            top3Blogs.add(blogs.get(i));
//        }
//
//        return top3Blogs;
//    }

    public void detectBadWords(String title, String content) {
        try {
            // Créer un objet HttpClient
            CloseableHttpClient client = HttpClients.createDefault();

            // Créer un objet HttpPost avec l'URL de l'API
            HttpPost post = new HttpPost("https://profanity-toxicity-detection-for-user-generated-content.p.rapidapi.com/");

            // Définir les en-têtes requis
            post.setHeader("X-RapidAPI-Key", "7f8f4854fcmsh18a104a62e08b06p15fbcfjsn6a9cf9faa202");
            post.setHeader("X-RapidAPI-Host", "profanity-toxicity-detection-for-user-generated-content.p.rapidapi.com");
            post.setHeader("Content-Type", "application/json");

            // Construire le corps de la requête avec les données du blog
            String data = "{\"title\": \"" + title + "\", \"content\": \"" + content + "\"}";

            // Ajouter les données à la requête
            post.setEntity(new StringEntity(data));

            // Exécuter la requête et obtenir la réponse
            HttpResponse response = client.execute(post);

            // Lire la réponse de l'API
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }

            // Afficher la réponse dans une boîte de dialogue
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profanity Detection Result");
            alert.setHeaderText(null);
            alert.setContentText(responseContent.toString());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}













