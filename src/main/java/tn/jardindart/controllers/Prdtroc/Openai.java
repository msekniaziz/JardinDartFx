package tn.jardindart.controllers.Prdtroc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Openai {

    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";
    private static final String API_KEY = "sk-proj-z1ykyyNnmcBkSJ96yK5fT3BlbkFJBLj89fj5aA3aSMwTKLHk";

    @FXML
    private Label answerLabel;

    @FXML
    private TextArea questionTextArea;
    @FXML
    public void market(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Market.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);

            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
    @FXML
    void ajouter_prodtroc(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/addproduittr.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);
            ComboBox<String> catrgtroComboBox = (ComboBox<String>) root.lookup("#catrgtro");
            catrgtroComboBox.getItems().addAll("house", "garden");
            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }}


    @FXML
    void askQuestion(ActionEvent event) {
        // Get the question from the text area
        String question = questionTextArea.getText();

        // Call the method from the OpenaiService class to get the answer
        String answer = OpenaiService.index11(question);

        // Set the answer in the answerLabel
        answerLabel.setText(answer);
    }

    public static class OpenaiService {

        public static String index11(String question) {
            if (question == null || question.isEmpty()) {
                return "I'm sorry, I can only answer questions related to JardinDars products and services.";
            }

            try {
                String answer;
                if (question.matches("(?i).*conditions|terms|utilisation|propriété.*")) {
                    answer = getTermsAndConditions();
                } else if (question.matches("(?i).*Jardindart|site|buy.*")) {
                    answer = "JardinDars is a website to help you buy, sell, and exchange products easily and safely.";
                } else if (question.matches("(?i).*troc|echange|house|garden|exchange.*")) {
                    answer = "Our site helps you exchange your products easily and quickly with the security of your home. You can easily add a product to get rid of.";
                } else if (question.matches("(?i).*recylage|environement|eco|green.*")) {
                    answer = "you can easily recycle any product by depositing it in any dropping point you can consult the page associated the recycling";
                } else if (question.matches("(?i).*blog|comment|like|want.*")) {
                    answer = "you can add a blog or comment on one leave a like and dont forget to share it with your friends";
                } else if (question.matches("(?i).*don|donnation|give|charity|want.*")) {
                    answer = "you can donate money easily from our site its very easy and secure";
                } else {
                    answer = getAnswerFromAPI(question);
                }
                return answer;
            } catch (Exception e) {
                e.printStackTrace();
                return "I'm sorry, I can only answer questions related to JardinDars products and services. Please try again.";
            }
        }

        private static String getTermsAndConditions() {
            return "  'En utilisant l\\'application JardinDars, vous acceptez les présentes conditions d\\'utilisation. Veuillez les lire attentivement avant d\\'utiliser l\\'application.\n" +
                    "        \n" +
                    "        Utilisation de l\\'application\n" +
                    "        JardinDars est une application de logistique qui fournit des services de livraison et de location de véhicules. L\\'utilisation de l\\'application est réservée aux personnes âgées de 18 ans ou plus.\n" +
                    "    \n" +
                    "        Inscription et compte utilisateur\n" +
                    "        Pour utiliser certains services de l\\'application, vous devez créer un compte utilisateur en fournissant des informations personnelles précises et à jour. Vous êtes entièrement responsable de la protection et de la confidentialité de votre compte utilisateur. Vous ne devez pas partager votre compte avec d\\'autres personnes et vous êtes entièrement responsable de toutes les activités effectuées sous votre compte.\n" +
                    "    \n" +
                    "        Conditions de paiement\n" +
                    "        L\\'utilisation de certains services de l\\'application peut entraîner des frais. Vous êtes entièrement responsable du paiement de tous les frais liés à l\\'utilisation de l\\'application. Les modes de paiement acceptés sont ceux spécifiés dans l\\'application.\n" +
                    "    \n" +
                    "        Propriété intellectuelle\n" +
                    "        Tous les droits de propriété intellectuelle associés à l\\'application et à son contenu, y compris mais sans s\\'y limiter, les marques, les logos, les textes, les images, les graphiques, les sons et les vidéos, sont la propriété d\\'Amena ou de ses fournisseurs de contenu. Vous ne devez pas copier, reproduire, distribuer, transmettre, afficher, vendre, concéder sous licence ou exploiter de toute autre manière tout contenu de l\\'application sans l\\'autorisation écrite préalable d\\'Amena.\n" +
                    "    \n" +
                    "        Limitation de responsabilité\n" +
                    "        JardinDars ne peut garantir la qualité, la fiabilité, l\\'exactitude ou l\\'exhaustivité de tout contenu de l\\'application. L\\'utilisation de l\\'application est à vos risques et périls. Amena ne peut être tenu responsable de tout dommage résultant de l\\'utilisation ou de l\\'incapacité à utiliser l\\'application, y compris les dommages directs, indirects, accessoires, spéciaux ou consécutifs.\n" +
                    "\n" +
                    "        Modification des conditions d\\'utilisation\n" +
                    "        JardinDars se réserve le droit de modifier les présentes conditions d\\'utilisation à tout moment sans préavis. Il est de votre responsabilité de vérifier régulièrement les conditions d\\'utilisation pour être informé des modifications éventuelles.\n" +
                    "        \n" +
                    "        En utilisant l\\'application JardinDars, vous acceptez les présentes conditions d\\'utilisation. Si vous ne les acceptez pas, veuillez ne pas utiliser l\\'application.\n" +
                    "        Limitation de responsabilité\n" +
                    "        JardinDars ne sera pas responsable des dommages directs, indirects, spéciaux, consécutifs ou accessoires découlant de l\\'utilisation ou de l\\'impossibilité d\\'utiliser la plateforme ou de son contenu.\n" +
                    "        \n" +
                    "        Droit applicable\n" +
                    "        Ces conditions sont régies par les lois en vigueur en Tunisie. Tout litige relatif à ces conditions sera soumis aux tribunaux compétents de France.\n" +
                    "        \n" +
                    "        Contact\n" +
                    "        Si vous avez des questions concernant ces conditions, vous pouvez contacter Amena à l\\'adresse suivante : contact@amena.com.';";
        }
        private static String getAnswerFromAPI(String question) throws Exception {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            String postData = "{"
                    + "\"prompt\": \"" + question + "\","
                    + "\"temperature\": 0.7,"
                    + "\"max_tokens\": 4000,"
                    + "\"top_p\": 1,"
                    + "\"frequency_penalty\": 0.5,"
                    + "\"presence_penalty\": 0"
                    + "}";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(postData);
                wr.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    String jsonResponse = response.toString();
                    // Use regex to extract email address from the answer if present
                    Pattern pattern = Pattern.compile("(?<=contact@jardindart.com).*?$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(jsonResponse);
                    if (matcher.find()) {
                        return matcher.group().trim();
                    }
                    return jsonResponse;
                }
            } else {
                // Handle different HTTP response codes
                if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    return "Bad request: Please check your input and try again.";
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    return "Unauthorized: Please check your API key.";
                } else {
                    return "Failed to retrieve response from API. Response code: " + responseCode;
                }
            }
        }

    }
}
