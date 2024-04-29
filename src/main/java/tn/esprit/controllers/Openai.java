package tn.esprit.controllers;

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

    // private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";
    // private static final String API_KEY = "sk-proj-y0fbBY6rPVIz4gqlIBv4T3BlbkFJoJpwIAtpIsIPYJEbv7is";

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
                if (question.matches("(?i).*conditions|termes|utilisation|propriété.*")) {
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
            return "En utilisant l'application JardinDars, vous acceptez les présentes conditions d'utilisation...";
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
                    Pattern pattern = Pattern.compile("(?<=contact@amena.com).*?$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(jsonResponse);
                    if (matcher.find()) {
                        return matcher.group().trim();
                    }
                    return jsonResponse;
                }
            } else {
                throw new RuntimeException("Failed to retrieve response from API. Response code: " + responseCode);
            }
        }
    }
}
