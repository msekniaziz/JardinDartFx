package User;

import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
public class RegisterController implements Initializable {
    private final Connection con;

    @FXML
    private Label checkMail;

    @FXML
    private Label checkPhoneNumber;

    @FXML
    private Label checklastName;

    @FXML
    private Label checkfirstName;

    @FXML
    private Label checkAge;

    @FXML
    private Label checkPassword;

    @FXML
    private Label checkConfirmPassword;

    @FXML
    private Label checkDateBirthday;

    @FXML
    private Label checkGender;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField password;

    @FXML
    private TextField agefield;

    @FXML
    private TextField phoneNumber;
    @FXML
    private ImageView imageView;
    @FXML
    private Button registerButton;
    Window window;
    @FXML
    private WebView webView;
    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        gender.getItems().addAll("Man", "Women");
        agefield.setText("15");
        checkfirstName.setVisible(false);
        checklastName.setVisible(false);
        checkMail.setVisible(false);
        checkPhoneNumber.setVisible(false);
        checkGender.setVisible(false);
        checkDateBirthday.setVisible(false);
        checkAge.setVisible(false);
        checkPassword.setVisible(false);
        checkConfirmPassword.setVisible(false);
        Image image1 = new Image("file:src/images/logo_site.png");
        imageView.setImage(image1);
    }

    private boolean checkIsValidated() {
        boolean verif = true ;
        if (firstName.getText().isEmpty()) {
            checkfirstName.setVisible(true);
            checkfirstName.setText("First name field cannot be blank.");
            checkfirstName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckFirstNameConstraint(firstName.getText())) {
            checkfirstName.setVisible(true);
            checkfirstName.setText("First name must contain only characters [a-z], minimum length is 3");
            checkfirstName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkfirstName.setVisible(false);
        }

        if (lastName.getText().isEmpty()) {
            checklastName.setVisible(true);
            checklastName.setText("Last name field cannot be blank.");
            checklastName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckLastNameConstraint(lastName.getText())) {
            checklastName.setVisible(true);
            checklastName.setText("Last name must contain only characters [a-z], minimum length is 3");
            checklastName.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checklastName.setVisible(false);
        }

        if (phoneNumber.getText().isEmpty()) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("Phone Number field cannot be blank.");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPhoneNumberConstraint(phoneNumber.getText())) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("The telephone number must consist of exactly 8 digits.");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (isAlreadyRegisteredWithPhoneNumber()) {
            checkPhoneNumber.setVisible(true);
            checkPhoneNumber.setText("Phone Number already exists");
            checkPhoneNumber.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkPhoneNumber.setVisible(false);
        }
        if (agefield.getText().isEmpty()) {
            checkAge.setVisible(true);
            checkAge.setText("Age text field cannot be blank.");
            checkAge.setStyle("-fx-text-fill: red;");
            verif = false ;
        } else if (!CheckAgeConstraint(agefield.getText())) {
            checkAge.setVisible(true);
            checkAge.setText("Age Minimum is 15 , only digits characters");
            checkAge.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else{
            checkAge.setVisible(false);
        }

        if (email.getText().isEmpty()) {
            checkMail.setVisible(true);
            checkMail.setText("Mail field cannot be blank.");
            checkMail.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (isAlreadyRegisteredWithMail()) {
            checkMail.setVisible(true);
            checkMail.setText("Mail already exists");
            checkMail.setStyle("-fx-text-fill: red;");
        } else if (!isValidEmail(email.getText())) {
            checkMail.setVisible(true);
            checkMail.setText("Invalid email format.");
            checkMail.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkMail.setVisible(false);
        }

        if (gender.getValue() == null) {
            checkGender.setVisible(true);
            checkGender.setText("Gender field cannot be blank.");
            checkGender.setStyle("-fx-text-fill: red;");
            verif = false ;
        }else{
            checkGender.setVisible(false);
        }
        if (dateBirthday.getValue() == null) {
            checkDateBirthday.setVisible(true);
            checkDateBirthday.setText("Birthday Date field cannot be blank.");
            checkDateBirthday.setStyle("-fx-text-fill: red;");
            verif = false ;
        }else{
            checkDateBirthday.setVisible(false);
        }
        if (password.getText().isEmpty()) {
            checkPassword.setVisible(true);
            checkPassword.setText("Password field cannot be blank.");
            checkPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPasswordConstraint(password.getText())) {
            checkPassword.setVisible(true);
            checkPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            checkPassword.setVisible(false);
        }

        if (confirmPassword.getText().isEmpty()) {
            checkConfirmPassword.setVisible(true);
            checkConfirmPassword.setText("Confirm Password field cannot be blank.");
            checkConfirmPassword.setStyle("-fx-text-fill: red;");
            verif = false ;
        }else if (!CheckPasswordConstraint(confirmPassword.getText())) {
            checkConfirmPassword.setVisible(true);
            checkConfirmPassword.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            checkConfirmPassword.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else{
            checkConfirmPassword.setVisible(false);
        }
        if (!verif) {
            return false;
        }
        return true;

    }
    public RegisterController() {
        DataBase dataBase = new DataBase();
        con = dataBase.getConnect();
    }

    @FXML
    private void showLoginStage() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();
    }

    @FXML
    public void register(ActionEvent actionEvent) {
        if (checkIsValidated()) {
            User user = new User();
            window = registerButton.getScene().getWindow();
            String nom = firstName.getText();
            String prenom = lastName.getText();
            String mail = email.getText();
            String tel = phoneNumber.getText();
            String genderValue = gender.getValue();
            String pwd = password.getText();
            String confirmPwd = confirmPassword.getText();
            String age = agefield.getText();
            LocalDate dateBirthdayValue = dateBirthday.getValue();

            if (pwd.equals(confirmPwd)) {
                String hashedPwd = DigestUtils.sha1Hex(pwd);
                String hashedConfirmPwd = DigestUtils.sha1Hex(confirmPwd);
                boolean isUserAdded = user.addUser(nom, prenom, mail, tel, genderValue, hashedPwd, 0, age, dateBirthdayValue, hashedConfirmPwd);
                if (isUserAdded) {
                    clearForm();
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
                            "You have registered successfully.");
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Failed to register user. Please try again later.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Password and confirm password do not match.");
            }
        }
    }


    private boolean isAlreadyRegisteredWithMail() {
        PreparedStatement ps;
        ResultSet rs;
        boolean MailExist = false;
        String query = "select * from user WHERE mail = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email.getText());
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
            ps.setString(1, phoneNumber.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                PhoneExist = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return PhoneExist;
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
    private boolean CheckAgeConstraint(String test) {
        if (test.length() != 2)
            return false;
        char firstChar = test.charAt(0);
        char secondChar = test.charAt(1);
        if (!(firstChar >= '1' && firstChar <= '9'))
            return false;
        if (!(secondChar >= '0' && secondChar <= '9'))
            return false;
        int age = Integer.parseInt(test);
        if (age <= 15)
            return false;
        return true;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public boolean CheckPasswordConstraint(String test) {
        // Check if the password length is at least 8 characters
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

    private void clearForm() {
        firstName.clear();
        lastName.clear();
        email.clear();
        phoneNumber.clear();
        password.clear();
        confirmPassword.clear();
        agefield.clear();
        gender.valueProperty().setValue(null);
        dateBirthday.valueProperty().setValue(null);
    }


}
