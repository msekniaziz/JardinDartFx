package User;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import javafx.scene.paint.Color;

public class AdminController {

    @FXML
    private TextField filterField;
    @FXML
    private TextField SearchBarUser;

    @FXML
    private ImageView SearchButtonUserClick;
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
    @FXML
    private Pagination pagination;

    public void DisplayUser() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dn.setCellValueFactory(new PropertyValueFactory<>("date_birthday"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        points.setCellValueFactory(new PropertyValueFactory<>("nb_points"));
        listM = DataBase.getDatauser();
    }
    public void initialize() throws SQLException {
        String userId = SessionManager.getInstance().getUserId();
        int id =  SessionManager.getInstance().getUserFront();

        adminName.setText(String.valueOf(userId));
        ActivateUserBack();
        InactiveUserBack();
        DisplayUser();
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void search_user() {
        id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
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
                if (String.valueOf(person.getId()).toLowerCase().contains(lowerCaseFilter)) {
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
    private void ActivateUserBack() {
        TableColumn<User, Void> activateButtonColumn = new TableColumn<>("ACTIONS");
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = (TableColumn<User, Void> param) -> {
            final TableCell<User, Void> cell = new TableCell<>() {
                private final Button activateButton = new Button("Activate");
                {
                    activateButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            updateUserStatus(user.getId(), "active");
                        DisplayUser();
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
    private void updateUserStatusInactive(int userId, String newStatus) {
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
                        updateUserStatusInactive(user.getId(), "inactive");
                        DisplayUser();
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

}
