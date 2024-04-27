package tn.jardindart.controllers;

import helper.AlertHelper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.jardindart.entites.* ;
import tn.jardindart.utils.DataBase ;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Pagination;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javafx.stage.Window;
import javafx.scene.control.Button;


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
        search_user();
    }

    void search_user() {
        mail.setCellValueFactory(new PropertyValueFactory<User,String>("mail"));
        num_tel.setCellValueFactory(new PropertyValueFactory<User,String>("tel"));
        dataList = DataBase.getDatauser();
        tableView.setItems(dataList);
        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(person.getMail()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (person.getTel().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
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
                                    "Are you sure you want to deactivate this account?", () -> {
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
                                    "Are you sure you want to activate this account?", () -> {
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
}
