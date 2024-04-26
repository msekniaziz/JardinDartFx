package tn.esprit.pifx.controllers.ptCollect;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.services.TypeDispoService;

import java.util.List;
import java.util.Optional;

public class ListType {
    @FXML
    private TextField nomTypeField;
    @FXML
    private TableView<TypeDispo> typeDispoTable;

    @FXML
    private TableColumn<TypeDispo, Integer> idColumn;

    @FXML
    private TableColumn<TypeDispo, String> nomColumn;

    private TypeDispoService typeDispoService;

    public void initialize() {
        typeDispoService = new TypeDispoService();
        afficherListeTypeDispo();
        configurerCellulesActions();
    }

    private void afficherListeTypeDispo() {
        List<TypeDispo> typeDispoList = typeDispoService.recuperer();
        typeDispoTable.getItems().addAll(typeDispoList);
        ObservableList<TypeDispo> observableProdRList = FXCollections.observableArrayList(typeDispoList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

        typeDispoTable.setItems(observableProdRList);
    }

    private void configurerCellulesActions() {
        TableColumn<TypeDispo, Void> actionsColumn = new TableColumn<>("Actions");

        // Bouton de modification
        Button modifierButton = new Button("Modifier");
        modifierButton.setOnAction(event -> {
            TypeDispo typeDispo = typeDispoTable.getSelectionModel().getSelectedItem();
            if (typeDispo != null) {
                // Implémentez ici la logique pour modifier le type de produit sélectionné
                System.out.println("Modification du type de produit : " + typeDispo.getNom());
                boolean result = afficherDialogueModification(typeDispo);
                typeDispoService.modifier(typeDispo);
                if (result) {
                    // Le type de produit a été modifié avec succès
                    System.out.println("Le type de produit a été modifié avec succès.");
                } else {
                    // La modification du type de produit a échoué
                    System.out.println("La modification du type de produit a échoué.");
                }
            } else {
                // Affichez un message d'erreur ou invitez l'utilisateur à sélectionner un élément
                System.out.println("Veuillez sélectionner un type de produit à modifier.");
            }
        });

        // Bouton de suppression
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setOnAction(event -> {
            TypeDispo typeDispo = typeDispoTable.getSelectionModel().getSelectedItem();
            if (typeDispo != null) {
                // Affichez une boîte de dialogue de confirmation avant de supprimer
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de la suppression");
                alert.setHeaderText("Voulez-vous vraiment supprimer ce type de produit ?");
                alert.setContentText("Cette action est irréversible.");

                // Option de confirmation
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean deleteResult = typeDispoService.supprimer(typeDispo.getId());
                    if (deleteResult) {
                        // Le type de produit a été supprimé avec succès
                        System.out.println("Le type de produit a été supprimé avec succès.");
                        typeDispoTable.getItems().remove(typeDispo);
                    } else {
                        // La suppression du type de produit a échoué
                        System.out.println("La suppression du type de produit a échoué.");
                    }
                }
            } else {
                // Affichez un message d'erreur ou invitez l'utilisateur à sélectionner un élément
                System.out.println("Veuillez sélectionner un type de produit à supprimer.");
            }
        });

        // Ajoutez les boutons à la colonne des actions
        actionsColumn.setCellFactory(param -> new TableCell<TypeDispo, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(modifierButton, supprimerButton);
                    setGraphic(buttons);
                }
            }
        });

        // Ajoutez la colonne des actions à la table
        typeDispoTable.getColumns().add(actionsColumn);
    }

    private boolean afficherDialogueModification(TypeDispo typeDispo) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Modification du type de produit");
        dialog.setHeaderText("Modifier le type de produit");

        // Bouton de validation
        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        // Création des champs de saisie
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nomTextField = new TextField();
        nomTextField.setText(typeDispo.getNom());

        gridPane.add(new Label("Nom :"), 0, 0);
        gridPane.add(nomTextField, 1, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Validation des données lors de la soumission du formulaire
        dialog.setResultConverter(buttonType -> {
            if (buttonType == validerButtonType) {
                // Valider les modifications et retourner true
                typeDispo.setNom(nomTextField.getText());
                return true;
            }
            // Annuler les modifications et retourner false
            return false;
        });

        Optional<Boolean> result = dialog.showAndWait();
        return result.orElse(false);
    }

    @FXML
    public void ajouterType() {

        String nomType = nomTypeField.getText().trim();
        if (!nomType.isEmpty()) {
            TypeDispo nouveauType = new TypeDispo();
            nouveauType.setNom(nomType);
            boolean ajoutReussi = typeDispoService.ajouter(nouveauType);
            if (ajoutReussi) {
                // Si l'ajout est réussi, actualiser le tableau
                // Réinitialiser le champ de saisie
                nomTypeField.clear();
            } else {
                // Gérer l'échec de l'ajout (affichage d'un message d'erreur, etc.)
            }
        } else {
            // Gérer le cas où le champ est vide
        }
    }
}