package User ;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.regex.Pattern;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class UserController implements Initializable {

    @FXML
    private TextField Mailfield;

    @FXML
    private TextField PhoneNumberfield;

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    @FXML
    private Label NameUser;

    @FXML
    private Label checkfirstName;
    @FXML
    private Label checklastName;
    @FXML
    private Label checkMail;
    @FXML
    private Label checkPhoneNumber;
    private Connection con;

    @FXML
    private Label checkConfirmPassword;

    @FXML
    private Label checkOldPassword;

    @FXML
    private Label checkPassword;

    @FXML
    private PasswordField oldPasswordfield;
    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private PasswordField Passwordfield;

    @FXML
    private Button CodeButton;

    @FXML
    private Button CodeButton1;

    @FXML
    private Button backbutton;

    @FXML
    private Label codecheck;

    @FXML
    private TextField codefield;

    @FXML
    private TextField newpasswordfield;

    @FXML
    private TextField numField;

    @FXML
    private Label numcheck;
    @FXML
    private Label passcheck;

    @FXML
    private Button resetbutton;
    private String codeFromSMS;

    Window window;
    String tel ;
    String mail ;
    @FXML
    private HBox Logout;

    public void backtologin(ActionEvent actionEvent) {
        Stage forgotPasswordStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        forgotPasswordStage.close();
    }

    @FXML
    void showcode(ActionEvent event) throws SQLException {
        String num = numField.getText();
        if (isPhoneNumberAvailable(num)) {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                    "Check your phone for your code");
            Random random = new Random();
            int code = random.nextInt(10000);
            codeFromSMS = String.format("%04d", code);
            codefield.setVisible(true);
            codecheck.setVisible(true);
            CodeButton1.setVisible(true);
            numcheck.setVisible(false);
            final String ACCOUNT_SID = "AC4116c3496ed4b8c8c4ebaa258b3f4563";
            final String AUTH_TOKEN = "c2faec21251e3e62cebb6137416a0f6d";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber("+21651457111"),
                            new PhoneNumber("+12054152034"),
                            "Hi, this is your code for password reset: " + codeFromSMS)
                    .create();
        } else {
            numcheck.setText("Invalid phone number");
            numcheck.setStyle("-fx-text-fill: red");
            numcheck.setVisible(true);
        }
    }

    private boolean isPhoneNumberAvailable(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM user WHERE tel = ?";
        PreparedStatement preparedStatement = DataBase.getConnect().prepareStatement(query);
        preparedStatement.setString(1, phoneNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();

    }
    @FXML
    void setchangepassword(ActionEvent event) {
        String codeEntered = codefield.getText();

        if (codeEntered.equals(codeFromSMS)) {
            codecheck.setText("Code is correct");
            codecheck.setStyle("-fx-text-fill: green");
            codecheck.setVisible(true);
            newpasswordfield.setVisible(true);
            resetbutton.setVisible(true);
            codecheck.setVisible(false);
        } else {
            codecheck.setText("Code is incorrect");
            codecheck.setStyle("-fx-text-fill: red");
            codecheck.setVisible(true);
        }
    }
    @FXML
    void changepassword(ActionEvent event) throws SQLException {
        String newPassword = newpasswordfield.getText();
        String query = "UPDATE user SET password = ? , confirmpassword =? WHERE tel = ?";
        PreparedStatement preparedStatement = DataBase.getConnect().prepareStatement(query);
        String hashedPwd = DigestUtils.sha1Hex(newPassword);
        preparedStatement.setString(1, hashedPwd);
        preparedStatement.setString(2, hashedPwd);
        preparedStatement.setString(3, numField.getText());
        preparedStatement.executeUpdate();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                "User password updated successfully");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void EditPasswordFront(ActionEvent actionEvent) {
        String passwordnew = Passwordfield.getText();
        String ConfirmpasswordNew = ConfirmPasswordField.getText();
        if (password()) {
            String oldPassword = oldPasswordfield.getText();
            String hashedOldPwd = DigestUtils.sha1Hex(oldPassword);
            DataBase dataBase = new DataBase();
            con = dataBase.getConnect();
            int userId = SessionManager.getInstance().getUserFront();

            try {
                String query = "SELECT password FROM user WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String hashedbase = resultSet.getString("password");
                    if (hashedbase.equals(hashedOldPwd)) {
                        if (passwordnew.equals(ConfirmpasswordNew)) {
                            String hashedPwd = DigestUtils.sha1Hex(passwordnew);
                            String query_edit = "UPDATE user SET password = ? , confirmpassword = ? WHERE id = ?";
                            PreparedStatement statement1 = con.prepareStatement(query_edit);
                            statement1.setString(1, hashedPwd);
                            statement1.setString(2, hashedPwd);
                            statement1.setInt(3, userId);
                            int rowsAffected = statement1.executeUpdate();
                            if (rowsAffected > 0) {
                                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                                        "User password updated successfully , Session expired in 10 seconds");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                                    SessionManager.getInstance().cleanUserSessionFront();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                                    Parent root = null;
                                    try {
                                        root = loader.load();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Stage stage = (Stage) Passwordfield.getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                }));
                                timeline.setCycleCount(1);
                                timeline.play();
                            } else {
                                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                        "Failed to edit user password, try again");
                            }
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                    "Password and confirm password are not compatible");
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                "Incompatible Old password");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean password() {
        boolean verif = true;

        if (oldPasswordfield.getText().isEmpty() || Passwordfield.getText().isEmpty() || ConfirmPasswordField.getText().isEmpty()) {
            checkOldPassword.setVisible(true);
            checkOldPassword.setText("Fields cannot be blank.");
            checkOldPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPasswordConstraint(Passwordfield.getText()) || !CheckPasswordConstraint(ConfirmPasswordField.getText())) {
            checkOldPassword.setVisible(true);
            checkOldPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkOldPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkOldPassword.setVisible(false);
        }
        return verif;
    }

    private boolean checkIsValidated() {
        boolean verif = true ;
        if (firstname.getText().isEmpty()) {
            checkfirstName.setVisible(true);
            checkfirstName.setText("First name field cannot be blank.");
            checkfirstName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckFirstNameConstraint(firstname.getText())) {
            checkfirstName.setVisible(true);
            checkfirstName.setText("First name must contain only characters [a-z], minimum length is 3");
            checkfirstName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkfirstName.setVisible(false);
        }
        if (lastname.getText().isEmpty()) {
            checklastName.setVisible(true);
            checklastName.setText("Last name field cannot be blank.");
            checklastName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckLastNameConstraint(lastname.getText())) {
            checklastName.setVisible(true);
            checklastName.setText("Last name must contain only characters [a-z], minimum length is 3");
            checklastName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checklastName.setVisible(false);
        }

        if (PhoneNumberfield.getText().isEmpty()) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("Phone Number field cannot be blank.");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPhoneNumberConstraint(PhoneNumberfield.getText())) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("The telephone number must consist of exactly 8 digits.");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else if (isAlreadyRegisteredWithPhoneNumber() && !PhoneNumberfield.getText().equals(tel) ) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("Phone Number already exists");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else {
            checkPhoneNumber.setVisible(false);
        }
        if (Mailfield.getText().isEmpty()) {
            checkMail.setVisible(true);
            checkMail.setText("Mail field cannot be blank.");
            checkMail.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!isValidEmail(Mailfield.getText())) {
            checkMail.setVisible(true);
            checkMail.setText("Invalid email format.");
            checkMail.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else if (isAlreadyRegisteredWithMail() && !Mailfield.getText().equals(mail)) {
            checkMail.setVisible(true);
            checkMail.setText("Mail already exists");
            checkMail.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkMail.setVisible(false);
        }
        return verif;
    }

    public void EditFrontUser(ActionEvent actionEvent) {
            if (checkIsValidated()) {
                try {
                    int userId = SessionManager.getInstance().getUserFront();
                    String query = "UPDATE user SET nom = ?, prenom = ?, mail = ?, tel = ? WHERE id = ?";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setString(1, firstname.getText());
                    statement.setString(2, lastname.getText());
                    statement.setString(3, Mailfield.getText());
                    statement.setString(4, PhoneNumberfield.getText());
                    statement.setInt(5, userId);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                                "User information updated successfully.");
                        String updatedName = firstname.getText() + " " + lastname.getText() ;
                        NameUser.setText(updatedName);
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                "Failed to update user information.");
                    }
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    public boolean CheckPasswordConstraint(String test) {
        if (test.length() < 8)
            return false;
        boolean hasUpperCase = false;
        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        String specialCharacters = "!@#$%^&*()-_+=<>?";
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (Character.isUpperCase(c))
                hasUpperCase = true;
            if (specialCharacters.contains(String.valueOf(c)))
                hasSpecialChar = true;
            if (Character.isDigit(c))
                hasDigit = true;
        }
        return hasUpperCase && hasSpecialChar && hasDigit;
    }

    private boolean CheckLastNameConstraint(String test) {
        if (test.length() < 3) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (!(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    private boolean CheckPhoneNumberConstraint(String test) {
        if (test.length() != 8) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean CheckFirstNameConstraint(String test) {
        if (test.length() < 3) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (!(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }
    private boolean isAlreadyRegisteredWithMail() {
        PreparedStatement ps;
        ResultSet rs;
        boolean MailExist = false;
        String query = "select * from user WHERE mail = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, Mailfield.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                MailExist = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return MailExist;
    }
    private boolean isAlreadyRegisteredWithPhoneNumber() {
        PreparedStatement ps;
        ResultSet rs;
        boolean PhoneExist = false;
        String query = "select * from user WHERE tel = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, PhoneNumberfield.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                PhoneExist = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return PhoneExist;
    }
    @FXML
    private Button loginButton;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DataBase dataBase = new DataBase();
        con = dataBase.getConnect();
        int userId = SessionManager.getInstance().getUserFront();
        String username = SessionManager.getInstance().getUserId();
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                firstname.setText(resultSet.getString("nom"));
                lastname.setText(resultSet.getString("prenom"));
                Mailfield.setText(resultSet.getString("mail"));
                PhoneNumberfield.setText(resultSet.getString("tel"));
                tel = resultSet.getString("tel");
                mail = resultSet.getString("mail");
                NameUser.setText(username);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GoToLogout(MouseEvent mouseEvent) {
        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) Logout;
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
}
