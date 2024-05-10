package tn.jardindart.controllers.Blog;
import tn.jardindart.models.Blog;
import tn.jardindart.models.ReponseBlog;
import tn.jardindart.utils.MyDataBase;
import java.sql.Date;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReponseBlogservice implements ServiceRep <ReponseBlog>{
    private static Connection cnx;
public ReponseBlogservice(){
    cnx= MyDataBase.getInstance().getCnx();

}


    @Override
    public void addrep(ReponseBlog reponseBlog) throws SQLException {
        String req = "INSERT INTO reponse_blog (id, id_user_reponse_id, date, contenu, blog_id) VALUES ('" + reponseBlog.getId() + "','" + reponseBlog.getId_user_reponse_id() + "','" + reponseBlog.getDate() + "','" + reponseBlog.getContenu() + "','" + reponseBlog.getBlog_id() + "')";
        Statement stm = cnx.createStatement();
        stm.executeUpdate(req);
        System.out.println("reponse  ajoutée");

    }

    @Override
    public List<ReponseBlog> showrep() throws SQLException {
        List<ReponseBlog> reponseBlogList = new ArrayList<>();
        String qry = "SELECT rb.*, b.titre AS blog_titre FROM reponse_blog rb JOIN blog b ON rb.blog_id = b.id";

        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                // Déclaration et initialisation de currentDate avec la date actuelle
                Date currentDate = new Date(System.currentTimeMillis());

                // Création d'une nouvelle instance de ReponseBlog avec currentDate
                ReponseBlog reponseBlog = new ReponseBlog();

                // Mise à jour de currentDate avec la valeur de la colonne "datecurrent" du ResultSet
              //  currentDate = rs.getDate("datecurrent");

                // Mise à jour des autres attributs de reponseBlog à partir des colonnes du ResultSet
                reponseBlog.setId(rs.getInt("id"));
                reponseBlog.setDate(LocalDate.parse(rs.getString("date")));
                reponseBlog.setContenu(rs.getString("contenu"));
//reponseBlog.setBlog_id(rs.getInt("blog_id"));
//reponseBlog.setId_user_reponse_id(rs.getInt("id_user_reponse_id"));



                Blog blog = new Blog();
                blog.setId(rs.getInt("blog_id"));
                blog.setTitre(rs.getString("blog_titre"));
//blog.setUser_id(rs.getInt("user_id"));
                // Définir le blog associé à la réponse
                reponseBlog.setBlog(blog);


                reponseBlogList.add(reponseBlog);
                System.out.println("workss");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponseBlogList;

    }


    @Override
    public void deleterep(int id) throws SQLException {
        String req = "DELETE FROM reponse_blog WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();

    }

    @Override
    public void editrep(ReponseBlog reponseBlog) throws SQLException {
        String req = "UPDATE reponse_blog SET date = ?, contenu = ?,  blog_id = ?, WHERE id = ?";
        PreparedStatement pstm = cnx.prepareStatement(req);
        pstm.setString(1, String.valueOf(reponseBlog.getDate()));
        pstm.setString(2, reponseBlog.getContenu());

        pstm.setInt(3,reponseBlog.getBlog_id());
        pstm.executeUpdate();

    }

    public static List<ReponseBlog> readAllReponses(LocalDate date) {
        String query = "SELECT * FROM reponse_blog WHERE date = ?";
        List<ReponseBlog> list = new ArrayList<>();
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setObject(1, date);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ReponseBlog reponseBlog = new ReponseBlog(
                            rs.getObject("date", LocalDate.class),
                            rs.getString("contenu"),

                            rs.getInt("id"),
                            rs.getInt("blog_id"),
                            // Assuming you have a method to retrieve the Blog object based on blog_id
                            // You can adjust this part as per your implementation
                            null // Change null to the appropriate Blog object retrieval
                    );
                    list.add(reponseBlog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;    }


    @Override
    public List<ReponseBlog> readAll() {
        String query = "SELECT * FROM reponse_blog";
        List<ReponseBlog> list = new ArrayList<>();
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ReponseBlog transport = new ReponseBlog(
                        rs.getObject("date", LocalDate.class),
                        rs.getString("contenu"),

                        rs.getInt("id"),
                        rs.getInt("blog_id"),null
                );
                // Assuming num_t is the primary key
                transport.setBlog_id(rs.getInt(1));
                list.add(transport);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
            throw new RuntimeException(e); // If you prefer to throw a RuntimeException
        }
        return list;

    }



}
