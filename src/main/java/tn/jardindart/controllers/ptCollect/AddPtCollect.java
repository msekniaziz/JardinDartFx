package tn.jardindart.controllers.ptCollect;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;
import tn.jardindart.models.PtCollect;
import tn.jardindart.models.TypeDispo;
import tn.jardindart.services.PtCollectService;
import tn.jardindart.services.TypeDispoService;
import tn.jardindart.test.HelloApplication;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddPtCollect {

    @FXML
    private TextField nomPcField, adressePcField, latitudePcField, longitudePcField;

    @FXML
    private CheckComboBox<TypeDispo> typeComboBox;
    @FXML
    private WebView webView;
    @FXML
    private AnchorPane ap;

    private List<PtCollect> collectPoints;
    private WebEngine webEngine;
    private PtCollectService ptCollectService;

    public void initialize() {
        ptCollectService = new PtCollectService();
        fillTypeComboBox();

        collectPoints = ptCollectService.recuperer();

        StringBuilder jsCode = new StringBuilder();

        jsCode.append("var map = L.map('map',{  zoomControl: false\n }).setView([33.9184, 8.1339], 20);");
        jsCode.append("L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {");
        jsCode.append("id: 'mapbox/streets-v11',"); // Le style de carte à utiliser
        jsCode.append("accessToken: 'sk.eyJ1IjoibXNla25pYXppeiIsImEiOiJjbHZqaGlrOHoxcGh0MnFudnR5OGhsZHRjIn0.vV3OYdILck7HbE-3e7nzBg',"); // Remplacez 'VOTRE_TOKEN_MAPBOX' par votre token MapboxjsCode.append("}).addTo(map);");
        jsCode.append("}).addTo(map);");

//        jsCode.append("map.dragging.disable();\n");
        jsCode.append("map.touchZoom.disable();\n");
        jsCode.append("map.doubleClickZoom.disable();\n");
//        jsCode.append("map.scrollWheelZoom.disable();\n");
        jsCode.append("map.boxZoom.disable();\n");
        jsCode.append("map.keyboard.disable();\n");
        jsCode.append("var markers = [];");

        for (PtCollect collectPoint : collectPoints) {
            String popupContent = generatePopupContent(collectPoint);
            jsCode.append("var marker = L.marker([" + collectPoint.getLatitudePc() + ", " + collectPoint.getLongitudePc() + "])");
            jsCode.append(".addTo(map)");
            jsCode.append(".bindPopup('" + popupContent + "');");
            jsCode.append("markers.push(marker);");
        }

        WebEngine engine = webView.getEngine();

        engine.loadContent(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Map</title>" +
                        "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" integrity=\"sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=\" crossorigin=\"\">" +
                        "    <style>" +
                        "        #map {" +
                        "            height: 500px;" +
                        "            width: 90%;" +
                        "            left: 5%;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "<div id=\"map\"></div>" +
                        "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\" integrity=\"sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=\" crossorigin=\"\"></script>" +
                        "<script>" +
                        jsCode.toString() +
                        "</script>" +

                        "</body>" +
                        "</html>"
        );


        webEngine = webView.getEngine();
        webView.setOnMouseClicked(event -> {
            // Récupérez les coordonnées de latitude et de longitude du point cliqué
            double latitude = (double) webEngine.executeScript("map.getCenter().lat;");
            double longitude = (double) webEngine.executeScript("map.getCenter().lng;");
            latitudePcField.setText(String.valueOf(latitude));
            longitudePcField.setText(String.valueOf(longitude));
        });
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                initializeJavaConnector();
            }
        });


    }


    // Ajoutez cette méthode à votre classe interne JavaConnector pour appeler la méthode de recherche JavaScript


    public void initializeJavaConnector() {
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaConnector", new JavaConnector());
        webEngine.executeScript("map.on('click', function(e) { javaConnector.onLocationSelected(e.latlng.lat, e.latlng.lng); });");}

    public class JavaConnector {

        public void onLocationSelected(double latitude, double longitude) {
            Platform.runLater(() -> {
                latitudePcField.setText(String.valueOf(latitude));
                longitudePcField.setText(String.valueOf(longitude));
            });
        }
    }

    private List<TypeDispo> retrieveTypeFromDatabase() {
        TypeDispoService ts = new TypeDispoService();
        ArrayList<TypeDispo> tl = (ArrayList<TypeDispo>) ts.recuperer();
        return tl;
    }

    private void fillTypeComboBox() {
        List<TypeDispo> tl = retrieveTypeFromDatabase();

        typeComboBox.setConverter(new StringConverter<TypeDispo>() {
            @Override
            public String toString(TypeDispo p) {
                return p != null ? p.getNom() : null;
            }

            @Override
            public TypeDispo fromString(String string) {
                return null;
            }
        });

        typeComboBox.getItems().addAll(tl);
    }

    @FXML
    private void ajouterPtCollect() {
        if (!validerSaisie()) {
            return; // Sortir de la méthode si la saisie n'est pas valide
        }

        String nomPc = nomPcField.getText();
        String adressePc = adressePcField.getText();
        Float latitudePc = Float.parseFloat(latitudePcField.getText());
        Float longitudePc = Float.parseFloat(longitudePcField.getText());
        List<TypeDispo> selectedTypes = typeComboBox.getCheckModel().getCheckedItems();

        // Ajouter le point de collecte pour obtenir l'ID auto-incrémenté
        PtCollect ptCollect = new PtCollect(nomPc, adressePc, latitudePc, longitudePc);
        ptCollectService.ajouter(ptCollect);

        int IdP = ptCollectService.recupererDernierIdPtCollect();
        // Créer un nouvel objet PtCollect avec l'ID et les types de disponibilité sélectionnés
        PtCollect ptCollectWithType = new PtCollect(IdP, nomPc, adressePc, latitudePc, longitudePc, selectedTypes);

        // Ajouter les types de disponibilité associés au point de collecte
        ptCollectService.ajouterAvecType(ptCollectWithType);

        afficherAlerte(Alert.AlertType.INFORMATION, "Succes", "Point de collecte ajouté avec succès.");

        viderChamps();
    }

    private boolean validerSaisie() {
        // Validation du nom du point de collecte
        String nomPc = nomPcField.getText();
        if (nomPc.isEmpty() || nomPc.length() < 3 || nomPc.length() > 12) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "Le nom du point de collecte doit contenir entre 3 et 12 caractères.");
            return false;
        }

        // Validation de l'adresse du point de collecte (peut être vide)

        // Validation de la latitude
        String latitudeStr = latitudePcField.getText();
        if (!estNombreFloat(latitudeStr)) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "La latitude doit être un nombre décimal.");
            return false;
        }

        // Validation de la longitude
        String longitudeStr = longitudePcField.getText();
        if (!estNombreFloat(longitudeStr)) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "La longitude doit être un nombre décimal.");
            return false;
        }

        return true;
    }

    private void afficherAlerte(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean estNombreFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void viderChamps() {
        nomPcField.clear();
        adressePcField.clear();
        latitudePcField.clear();
        longitudePcField.clear();
    }

    @FXML
    public void afficherListePt(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/ptCollect/ListPtCollect.fxml"));
        try {
            nomPcField.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePopupContent(PtCollect collectPoint) {
        StringBuilder popupContent = new StringBuilder();
        popupContent.append("<div class=\"text-center\" style=\"font-family: Muli, sans-serif; font-weight: bold;\">")
                .append(collectPoint.getNomPc())
                .append("</div>");

        // Ajoutez ici le contenu du popup
        // Parcourez les types disponibles et ajoutez-les au contenu du popup
        List<TypeDispo> typesDisponibles = collectPoint.getType();
        if (!typesDisponibles.isEmpty()) {
            popupContent.append("<div style=\"font-family: Muli, sans-serif; border-radius: 30px; border: 1px solid green; padding: 10px; margin-top: 5px;\">");
            popupContent.append("Types disponibles : ");
            for (TypeDispo typeDispo : typesDisponibles) {
                popupContent.append(typeDispo.getNom()).append(", ");
            }
            popupContent.delete(popupContent.length() - 2, popupContent.length()); // Supprime la virgule et l'espace en trop à la fin
            popupContent.append("</div>");
        }

//        popupContent.append("<br><a href=\"#\" onclick=\"menuc.navigateToFXML('/tn.jardindart/prodR/AddProdR.fxml', webView)\">Déposer un produit à recycler ici</a>");

        return popupContent.toString();
    }

}