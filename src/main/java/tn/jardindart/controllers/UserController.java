package tn.jardindart.controllers ;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;
import tn.jardindart.utils.DataBase;
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

public class UserController extends HomeON implements Initializable  {

    @FXML
    private Label labelpassword;

    @FXML
    private Label enterpassword;
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
    private Button EditButton;
    @FXML
    private Label numcheck;
    @FXML
    private Label passcheck;
    @FXML
    private Button resetbutton;
    private String codeFromSMS;

    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label labelcheckpassword;


    @FXML
    private PasswordField Passwordcheckedit;
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
            String query = "SELECT nom, prenom FROM user WHERE tel = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, num);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String fullName = resultSet.getString("nom") + " " + resultSet.getString("prenom");
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                        "Check your phone for your code");
                Random random = new Random();
                int code = random.nextInt(10000);
                codeFromSMS = String.format("%04d", code);
                codefield.setVisible(true);
                codecheck.setVisible(true);
                CodeButton1.setVisible(true);
                numcheck.setVisible(false);
                final String ACCOUNT_SID = "AC826dd4583d21e1d761edfdbefca0daf8";;
                final String AUTH_TOKEN = "6dfb8cfec4006bd5b1df833c60c9eca2";
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                                new PhoneNumber("+21655614560"),
                                new PhoneNumber("+14845529358"),
                                "Hi " + fullName + " , this is your code for password reset: " + codeFromSMS)
                        .create();
            }
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
            confirmPassword.setVisible(true);
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
        String ConfirmPassword = confirmPassword.getText();
        if (checkPasswordForgot()) {
            if (newPassword.equals(ConfirmPassword)) {

                String query = "UPDATE user SET password = ? , confirmpassword =? WHERE tel = ?";
                PreparedStatement preparedStatement = DataBase.getConnect().prepareStatement(query);
                String hashedPwd = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
                String hashedConfirmPwd = BCrypt.withDefaults().hashToString(12, ConfirmPassword.toCharArray());
                preparedStatement.setString(1, hashedPwd);
                preparedStatement.setString(2, hashedConfirmPwd);
                preparedStatement.setString(3, numField.getText());
                preparedStatement.executeUpdate();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                        "User password updated successfully");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Password and confirm password are not compatible");
            }
        }
    }
    public void EditPasswordFront(ActionEvent actionEvent) {
        String passwordnew = Passwordfield.getText();
        String ConfirmpasswordNew = ConfirmPasswordField.getText();
        if (password()) {
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
                    BCrypt.Result result = BCrypt.verifyer().verify(oldPasswordfield.getText().toCharArray(), hashedbase);
                    if (result.verified) {
                        if (passwordnew.equals(ConfirmpasswordNew)) {
                            String hashedPwd = BCrypt.withDefaults().hashToString(12, Passwordfield.getText().toCharArray());
                            String query_edit = "UPDATE user SET password = ? , confirmpassword = ? WHERE id = ?";
                            PreparedStatement statement1 = con.prepareStatement(query_edit);
                            statement1.setString(1, hashedPwd);
                            statement1.setString(2, hashedPwd);
                            statement1.setInt(3, userId);
                            int rowsAffected = statement1.executeUpdate();
                            if (rowsAffected > 0) {
                                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                                        "User password updated successfully , Session expired in 5 seconds");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
                                    SessionManager.getInstance().cleanUserSessionFront();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Login.fxml"));
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
        if (oldPasswordfield.getText().isEmpty()) {
            checkOldPassword.setVisible(true);
            checkOldPassword.setText("Old password cannot be blank.");
            checkOldPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        if (Passwordfield.getText().isEmpty()) {
            checkPassword.setVisible(true);
            checkPassword.setText("New password cannot be blank.");
            checkPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }

        if (ConfirmPasswordField.getText().isEmpty()) {
            checkConfirmPassword.setVisible(true);
            checkConfirmPassword.setText("Confirm password cannot be blank.");
            checkConfirmPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }

        if (!CheckPasswordConstraint(oldPasswordfield.getText())) {
            checkOldPassword.setVisible(true);
            checkOldPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkOldPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }


        if (!CheckPasswordConstraint(Passwordfield.getText())) {
            checkPassword.setVisible(true);
            checkPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }

        if (!CheckPasswordConstraint(ConfirmPasswordField.getText())) {
            checkConfirmPassword.setVisible(true);
            checkConfirmPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkConfirmPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }

        if (verif) {
            checkOldPassword.setVisible(false);
            checkPassword.setVisible(false);
            checkConfirmPassword.setVisible(false);
        }
        return verif;
    }

    public boolean checkPasswordForgot()
    {
        boolean verif = true;
        if (newpasswordfield.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
            labelcheckpassword.setVisible(true);
            labelcheckpassword.setText("Fields cannot be blank.");
            labelcheckpassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPasswordConstraint(newpasswordfield.getText()) || !CheckPasswordConstraint(confirmPassword.getText())) {
            labelcheckpassword.setVisible(true);
            labelcheckpassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            labelcheckpassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            labelcheckpassword.setVisible(false);
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
                    String query_password = "SELECT mail FROM user WHERE id = ?";
                    PreparedStatement preparedStatement = DataBase.getConnect().prepareStatement(query_password);
                    preparedStatement.setInt(1, userId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next())
                    {
                        System.out.println("nafseh 1");

                        String mail =  resultSet.getString("mail");
                        System.out.println("nafseh 2 ");

                        if (mail.equals(Mailfield.getText()))
                       {
                           System.out.println("nafseh 3 ");
                           System.out.println("same mail");
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
                           } else {
                               AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                       "Failed to update user information.");
                           }
                           statement.close();

                       }
                       else
                       {
                           if (Passwordcheckedit.getText().isEmpty())
                           {
                               enterpassword.setVisible(true);
                               Passwordcheckedit.setVisible(true);
                               labelpassword.setVisible(true);
                               labelpassword.setText("Field can not be blank");
                               labelpassword.setStyle("-fx-text-fill: red");

                           }else {
                               enterpassword.setVisible(true);
                               Passwordcheckedit.setVisible(true);
                               labelpassword.setVisible(false);
                               System.out.println("different mail");
                               boolean verif = checkpasswordFront(userId, Passwordcheckedit.getText());
                               if (verif) {
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
                                               "User mail updated successfully , Session expired in 5 seconds");
                                       Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
                                           SessionManager.getInstance().cleanUserSessionFront();
                                           FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Login.fxml"));
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
                                       enterpassword.setVisible(false);
                                       Passwordcheckedit.setVisible(false);
                                   } else {
                                       AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                               "Failed to update user information.");
                                   }
                                   statement.close();
                               } else {
                                   AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                           "Incorrect password");
                               }
                           }
                       }
                    }

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
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        enterpassword.setVisible(false);

    }

    public boolean checkpasswordFront(int idUser , String password) throws SQLException {
        String query_password = "SELECT password FROM user WHERE id = ?";
        PreparedStatement preparedStatement = DataBase.getConnect().prepareStatement(query_password);
        preparedStatement.setInt(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("found");
            String password_crypted = resultSet.getString("password");
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), password_crypted);
            if (result.verified)
            {
                return true ;
            }
        }
                return false ;
        }

}
