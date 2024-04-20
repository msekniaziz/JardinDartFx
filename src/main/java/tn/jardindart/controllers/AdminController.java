package tn.jardindart.controllers;

import helper.AlertHelper;
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
        adminName.setText(String.valueOf(userId));
        ActivateUserBack();
        InactiveUserBack();
        search_user();
    }

    @FXML
    void LogoutAdmin() {
        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) btnSignout;
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn.jardindart/Login.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String generateUniqueToken() {
        return UUID.randomUUID().toString();
    }
    private void ActivateUserBack() {

        String token = generateUniqueToken();
        TableColumn<User, Void> activateButtonColumn = new TableColumn<>("ACTIONS");
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = (TableColumn<User, Void> param) -> {
            final TableCell<User, Void> cell = new TableCell<>() {
               private static Button activateButton = new Button("Activate");
                {
                    activateButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Confirm Account Activation");
                        alert.setContentText("Are you sure you want to activate this account?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            updateUserStatus(user.getId(), "active");
                            DisplayUser();
                        }
                    });
                }
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(activateButton);
                    }
                }
            };
            return cell;
        };
        activateButtonColumn.setCellFactory(cellFactory);
        tableView.getColumns().add(activateButtonColumn);
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
    private void InactiveUserBack() {
        TableColumn<User, Void> activateButtonColumn = new TableColumn<>("ACTIONS");
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = (TableColumn<User, Void> param) -> {
            final TableCell<User, Void> cell = new TableCell<>() {
                private final Button InactivateButton = new Button("Inactivate");
                {
                        InactivateButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Confirm Account Deactivation");
                        alert.setContentText("Are you sure you want to deactivate this account?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            updateUserStatusInactive(user.getId(), "inactive");
                            DisplayUser();
                        }
                    });
                }
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(InactivateButton);
                    }
                }
            };
            return cell;
        };
        activateButtonColumn.setCellFactory(cellFactory);
        tableView.getColumns().add(activateButtonColumn);
    }
    private void updateUserStatusInactive(int userId, String newStatus) {
        try (Connection conn = DataBase.getConnect();
             PreparedStatement ps = conn.prepareStatement("UPDATE user SET status = ? WHERE id = ?")) {
            ps.setString(1, newStatus);
            ps.setInt(2, userId);
            ps.executeUpdate();
            DisplayUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
