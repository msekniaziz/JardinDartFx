package tn.jardindart.controllers.user;


import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tn.jardindart.models.* ;
import tn.jardindart.utils.DataBase ;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.Window;
import com.itextpdf.text.*;
public class AdminController {

    @FXML
    private TextField filterField;
    @FXML
    private Button btnSignout;

    @FXML
    private Label adminName;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, String> lname;

    @FXML
    private TableColumn<User, String> mail;

    @FXML
    private TableColumn<User, String> num_tel;

    @FXML
    private TableColumn<User, String> gender;

    @FXML
    private TableColumn<User, LocalDate> dn;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TableColumn<User, Integer> points;

    private ObservableList<User> listM;
    ObservableList<User> dataList;
    Window window;

    public void generatepdf(ActionEvent actionEvent) {
        Document document = new Document();
        try {
            String filePath = "C:\\Users\\azizm\\OneDrive - ESPRIT\\Bureau\\userreport.pdf";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);

            String backgroundImagePath = "C:\\Users\\azizm\\OneDrive - ESPRIT\\Bureau\\finale\\JardinDartFx - int\\src\\main\\resources\\tn.jardindart\\imagesbackground.jpg";
            PdfBackground pdfBackground  = new PdfBackground(backgroundImagePath);
            writer.setPageEvent(pdfBackground);
            document.open();


            String logoPath = "C:\\Users\\azizm\\OneDrive - ESPRIT\\Bureau\\JardinDartFx-USER - Copie\\src\\main\\resources\\images\\logo_site.png";
            try {
                com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoPath);
                logo.scaleAbsolute(50, 50);
                document.add(logo);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.GREEN);
            Paragraph title = new Paragraph("Users Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            document.add(new Paragraph("Created on : " + formattedDateTime));


            String adminName = SessionManager.getInstance().getUserId();
            document.add(new Paragraph( adminName));


            document.add(new Paragraph("JARDIN D'ART"));


            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);
            addTableHeader(table);
            addRows(table);
            document.add(table);

            document.close();
            System.out.println("PDF generated successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);



        cell.setPhrase(new Phrase("Name", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("LastName", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Mail", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date of Birth", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone Number", headerFont));
        table.addCell(cell);
    }

    private void addRows(PdfPTable table) {
        for (User user : listM) {
            table.addCell(user.getNom());
            table.addCell(user.getPrenom());
            table.addCell(user.getMail());
            table.addCell(user.getDate_birthday().toString());
            table.addCell(user.getStatus());
            table.addCell(user.getTel());

        }
    }
    public void DisplayUser() {
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dn.setCellValueFactory(new PropertyValueFactory<>("date_birthday"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        points.setCellValueFactory(new PropertyValueFactory<>("nb_points"));
        listM = DataBase.getDatauser();
        tableView.setItems(listM);
    }
    public void initialize() throws SQLException {
        DisplayUser();
        String userId = SessionManager.getInstance().getUserId();
        int id =  SessionManager.getInstance().getUserFront();
        setupActionButtonColumn();
    }

    private void setupActionButtonColumn() {
        TableColumn<User, Void> actionButtonColumn = new TableColumn<>("ACTIONS");
        Callback <TableColumn<User, Void>, TableCell<User, Void>> cellFactory = (TableColumn<User, Void> param) -> {
            final TableCell<User, Void> cell = new TableCell<User, Void>() {
                private final Button actionButton = new Button();
                {
                    actionButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        String currentStatus = user.getStatus();
                        if (currentStatus.equals("active")) {
                            showConfirmationDialog("Confirm Account Deactivation",
                                    "Are you sure you want to activate this account?", () -> {
                                        updateUserStatus(user.getId(), "inactive");
                                        DisplayUser();
                                        Image image1 = new Image(getClass().getResourceAsStream("/images/off.png"));
                                        ImageView view = new ImageView(image1);
                                        view.setFitWidth(30);
                                        view.setFitHeight(30);
                                        actionButton.setGraphic(view);
                                    });
                        } else {
                            showConfirmationDialog("Confirm Account Activation",
                                    "Are you sure you want to deactivate this account?", () -> {
                                        updateUserStatus(user.getId(), "active");
                                        DisplayUser();
                                        Image image1 = new Image(getClass().getResourceAsStream("/images/on.png"));
                                        ImageView view = new ImageView(image1);
                                        view.setFitWidth(30);
                                        view.setFitHeight(30);
                                        actionButton.setGraphic(view);
                                    });
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        User user = getTableView().getItems().get(getIndex());
                        if (user.getStatus().equals("active")) {
                            Image image1 = new Image(getClass().getResourceAsStream("/images/off.png"));
                            ImageView view = new ImageView(image1);
                            view.setFitWidth(30);
                            view.setFitHeight(30);
                            actionButton.setGraphic(view);
                        } else {
                            Image image1 = new Image(getClass().getResourceAsStream("/images/on.png"));
                            ImageView view = new ImageView(image1);
                            view.setFitWidth(30);
                            view.setFitHeight(30);
                            actionButton.setGraphic(view);
                        }
                        setGraphic(actionButton);
                    }
                }
            };
            return cell;
        };
        actionButtonColumn.setCellFactory(cellFactory);
        tableView.getColumns().add(actionButtonColumn);
    }


    private void showConfirmationDialog(String title, String content, Runnable action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(title);
        alert.setContentText(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                action.run();
            }
        });
    }
    private void updateUserStatus(int userId, String newStatus) {
        try (Connection conn = DataBase.getConnect();
             PreparedStatement ps = conn.prepareStatement("UPDATE user SET status = ? WHERE id = ?")) {
            ps.setString(1, newStatus);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> searchEmplacement(String searchTerm) throws SQLException {
        List<User> emplacements = new ArrayList<>();
        String req = "SELECT * FROM user WHERE " +
                "(nom LIKE ? OR " +
                "tel LIKE ? OR " +
                "mail LIKE ?) " +
                "AND role = 'USER'";
        Connection conn =DataBase.getConnect();
        try (PreparedStatement pre = conn.prepareStatement(req)) {
            for (int i = 1; i <= 3; i++) {
                pre.setString(i, "%" + searchTerm + "%");
            }

            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    User e = new User();
                    e.setNom(res.getString("nom"));
                    e.setPrenom(res.getString("prenom"));
                    e.setMail(res.getString("mail"));
                    e.setTel(res.getString("tel"));
                    e.setGender(res.getString("gender"));
                    e.setDate_birthday(res.getObject("date_birthday", LocalDate.class));
                    e.setStatus(res.getString("status"));
                    e.setNb_points(res.getInt("nb_points"));
                    emplacements.add(e);
                }
            }
        }
        return emplacements;
    }
    @FXML
    private void handleSearch(KeyEvent event) {
        String searchTerm = filterField.getText().toLowerCase();
        try {
            List<User> searchResults = searchEmplacement(searchTerm);
            // Clear the existing items in the table
            tableView.getItems().clear();
            // Add the search results to the table
            tableView.getItems().addAll(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}