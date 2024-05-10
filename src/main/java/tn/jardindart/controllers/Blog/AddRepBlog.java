package tn.jardindart.controllers.Blog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.ReponseBlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import tn.jardindart.test.HelloApplication;


public class AddRepBlog {
    ReponseBlog reponseBlog = new ReponseBlog();

    @FXML
    private DatePicker daterepField;


    @FXML
    private TextField blog_id;


    @FXML
    private TextArea dectro;

    @FXML
    private TextField statuspt;
    @FXML
    void AddRepBlog(ActionEvent event) {
        ReponseBlogservice reponseBlogService = new ReponseBlogservice();

        // Retrieve values from input fields
        String date = daterepField.getValue().toString();
        String contenu = dectro.getText();
        String status = statuspt.getText();
        int blogId = Integer.parseInt(blog_id.getText()); // Récupérer l'identifiant du blog à partir du champ blog_id
        int id1 = SessionManager.getInstance().getUserFront();

        SessionManager.getInstance().setUserFront(id1);
        // Assign values to the ReponseBlog object
        reponseBlog.setId_user_reponse_id(id1);
        reponseBlog.setDate(LocalDate.parse(date));
        reponseBlog.setContenu(contenu);
        reponseBlog.setBlog_id(blogId);

        try {
            // Call the addRepBlog method from the service
            reponseBlogService.addrep(reponseBlog);
            if (containsBadWords(reponseBlog.getContenu())) {
                System.out.println("bad word detected !");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Bad Word Detected !");
                alert.setContentText("Bad Word Detected !");
                alert.showAndWait();
                return;
            }
            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Response added successfully");
            alert.showAndWait();

            // Clear input fields after adding
            daterepField.getEditor().clear();
            dectro.clear();
            statuspt.clear();
        } catch (SQLException e) {
            // In case of error, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding response: " + e.getMessage());
            alert.showAndWait();
        }
    }
    ReponseBlog rep;
    public ReponseBlog setBlog(ReponseBlog book,int a) {
        this.rep = book;

        // Set existing book information to the labels and text fields
        book.setId(rep.getId());
       // daterepField.setValue(book.getDate());
        dectro.setText(book.getContenu());
       // statuspt.setText(book.getStatus());
        return book;
        // Load the image
//        Image image = new Image(book.getImage()); // Assuming book.getImage() returns the path to the image file

        // Set the image to the ImageView
//        imgb.setImage(image);
    }


    public ReponseBlog setprod(ReponseBlog book,int a) {
        this.reponseBlog = book;

        // Set existing book information to the labels and text fields
        this.reponseBlog = book;

        // Set existing book information to the labels and text fields
        book.setId(reponseBlog.getId());
        blog_id.setText(String.valueOf(a));
        //   dectro.setText(book.getContenu());
        //daterepField.sett (book.getDate());
        return book;
    }

    public boolean containsBadWords(String comment) {
        try {
            File file = new File("src/main/resources/badwords.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String badWord = scanner.nextLine();
                if (comment.toLowerCase().contains(badWord.toLowerCase())) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}