package User;

import com.twilio.rest.supersim.v1.UsageRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import javafx.stage.Modality;
import javafx.stage.Stage;
import helper.AlertHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import javafx.stage.Window;
import org.w3c.dom.events.MouseEvent;

public class LoginController implements Initializable {
    private final Connection con;
    Window window ;

    @FXML
    private Label CheckPasswordLogin;

    @FXML
    private Label checkMailLogin;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordFieldLogin;

    @FXML
    private TextField mailFieldLogin;
    @FXML
    private Label captchaLabel;
    @FXML
    private ImageView generateCap;
    @FXML
    private TextField tfCaptcha;
    @FXML
    private Label checkrecaptcha;

    @FXML
    private ImageView imageView;

    @FXML
    private void generateCaptcha() {
        String captcha = generateRandomString(6);
        captchaLabel.setText(captcha);
    }
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }

    public void login() {
        if (checkIsValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String hashedPwd = DigestUtils.sha1Hex(passwordFieldLogin.getText());
            String query = "SELECT * FROM user WHERE mail = ? AND password = ?";
            try {
                ps = con.prepareStatement(query);
                ps.setString(1, mailFieldLogin.getText());
                ps.setString(2, hashedPwd);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    String status = rs.getString("status");
                    if ("ADMIN".equals(role)) {
                            String nom= rs.getString("nom") ;
                            String prenom = rs.getString("prenom");
                            String fullName = nom + " " + prenom ;
                            SessionManager.getInstance().setUserId(fullName);
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            stage.close();
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPannel.fxml")));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Admin Panel");
                            stage.show();
                    }
                    else if("USER".equals(role)) {
                        if ("active".equals(status))
                        {
                            String nom= rs.getString("nom") ;
                            String prenom = rs.getString("prenom");
                            int id = rs.getInt("id");
                            String fullName = nom + " " + prenom ;
                            SessionManager.getInstance().setUserId(fullName);
                            SessionManager.getInstance().setUserFront(id);
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            stage.close();
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeON.fxml")));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("User Panel");
                            stage.show();
                        }
                        else {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                    "User is not activated yet.");
                        }
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Invalid username and password.");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public LoginController() {
        DataBase dataBase = new DataBase();
        con = dataBase.getConnect();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkMailLogin.setVisible(false);
        CheckPasswordLogin.setVisible(false);
        checkrecaptcha.setVisible(false);
        generateCaptcha();
        Image image1 = new Image("file:src/images/RecaptchaLogo.svg.png");
        generateCap.setImage(image1);
        Image image2 = new Image("file:src/images/logo_site.png");
        imageView.setImage(image2);
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

    private void clearForm() {
        mailFieldLogin.clear();
        passwordFieldLogin.clear();

    }

    private boolean checkIsValidated() {
        boolean verif = true ;
        if (mailFieldLogin.getText().isEmpty()) {
            checkMailLogin.setVisible(true);
            checkMailLogin.setText("Mail field cannot be blank.");
            checkMailLogin.setStyle("-fx-text-fill: red;");
            verif = false;
        }else if (!isValidEmail(mailFieldLogin.getText())) {
            checkMailLogin.setVisible(true);
            checkMailLogin.setText("Invalid email format.");
            checkMailLogin.setStyle("-fx-text-fill: red;");
            verif = false;
        }else {
            checkMailLogin.setVisible(false);
        }
        if (passwordFieldLogin.getText().isEmpty()) {
            CheckPasswordLogin.setVisible(true);
            CheckPasswordLogin.setText("Password field cannot be blank.");
            CheckPasswordLogin.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPasswordConstraint(passwordFieldLogin.getText())) {
            CheckPasswordLogin.setVisible(true);
            CheckPasswordLogin.setText("Password must be strong: at least one uppercase letter, one special character, and one digit.");
            CheckPasswordLogin.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            CheckPasswordLogin.setVisible(false);
        }
        if (!tfCaptcha.getText().equals(captchaLabel.getText()))
        {
            checkrecaptcha.setVisible(true);
            checkrecaptcha.setText("incorrect code , try again");
            checkrecaptcha.setStyle("-fx-text-fill: red;");
            verif = false;
        }
        else {
            checkrecaptcha  .setVisible(false);
        }
        return verif;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public void showSignInStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignIn.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User SignIn");
        stage.show();
    }
    public void changepassword(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Stage forgotPasswordStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ForgotPassword.fxml")));
        Scene scene = new Scene(root);
        forgotPasswordStage.setScene(scene);
        forgotPasswordStage.setTitle("Reset Password");
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        forgotPasswordStage.initOwner(currentStage);
        forgotPasswordStage.initModality(Modality.WINDOW_MODAL);
        forgotPasswordStage.show();
    }
}
