    package tn.esprit.pifx.controllers.prodR;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.control.*;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextArea;
    import javafx.scene.control.TextField;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.util.StringConverter;
    import tn.esprit.pifx.models.ProdR;
    import tn.esprit.pifx.models.PtCollect;
    import tn.esprit.pifx.controllers.front_top;
    import tn.esprit.pifx.models.TypeDispo;
    import tn.esprit.pifx.services.ProdRService;
    import tn.esprit.pifx.services.PtCollectService;
//    import tn.esprit.pifx.services.TypeDispoService;
    import tn.esprit.pifx.services.TypeDispoService;
    import tn.esprit.pifx.test.HelloApplication;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.List;


    public class AddProdR {
        private File selectedImageFile;
        @FXML
        private ImageView imageid;
        @FXML
        private TextField userIdField,nomPField;

        @FXML
        private TextArea descriptionArea;

        @FXML
        private ComboBox<PtCollect> ptcComboBox;

        @FXML
        private ComboBox<TypeDispo> typeComboBox;

        private ProdRService prodRService;
        private front_top menuc;
        @FXML
        private Label ptcCommentLabel;

        @FXML
        private Label typeCommentLabel;

        @FXML
        private Label descriptionCommentLabel;

        @FXML
        private Label nomPCommentLabel;


        public void initialize(PtCollect ptCollect) {
            prodRService = new ProdRService(); // Initialisation de l'instance de ProdRService
            menuc = new front_top();
            fillPtcComboBox();
            ptcComboBox.setValue(ptCollect);
            ptcComboBox.setDisable(true);
            ptcComboBox.setStyle("-fx-opacity: 1;");

         /*   PtCollect pt = ptcComboBox.getValue();
            List<TypeDispo> associatedTypes = pt.getType();
            typeComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(TypeDispo p) {
                    return p != null ? p.getNom() : null;
                }

                @Override
                public TypeDispo fromString(String string) {
                    return null;
                }
            });

            typeComboBox.getItems().addAll(associatedTypes);*/
            fillTypeComboBox();

        }

        private List<TypeDispo> retrieveTypeFromDatabase() {

            TypeDispoService ts = new TypeDispoService();
            ArrayList<TypeDispo> tl = (ArrayList<TypeDispo>) ts.recuperer();
            return tl;
        }
        private void fillTypeComboBox() {

            List<TypeDispo> tl = retrieveTypeFromDatabase();

            // Configurez le ComboBox pour afficher le nom de l'association à l'aide d'une expression lambda
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

            // Ajoutez les associations au ComboBox
            typeComboBox.getItems().addAll(tl);
        }




        @FXML
        public void ajouterProdR() {
            if (!validerSaisie()) {
                return;
            }

            int userId = Integer.parseInt(userIdField.getText());
            String description = descriptionArea.getText();
            String nomP = nomPField.getText();
            String justificatif = selectedImageFile.getPath();
            PtCollect selectedPt = ptcComboBox.getValue();
            TypeDispo selectedt = typeComboBox.getValue();

            if (selectedt == null) {
                afficherAlerte(Alert.AlertType.ERROR, "Error", "Please select a Type Prod");
                return;
            }


            try {
                ProdR newProdR = new ProdR(userId, selectedPt.getId(), selectedt.getId().intValue(), false, description, nomP, justificatif);
                if (selectedImageFile != null) {
                    prodRService.uploadImage(selectedImageFile);
                }
                prodRService.ajouter(newProdR);
                afficherAlerte(Alert.AlertType.CONFIRMATION, "Success", "Product added successfully");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pifx/prodR/ViewProdR.fxml"));
                nomPField.getScene().setRoot(fxmlLoader.load());

            } catch (Exception e) {
                afficherAlerte(Alert.AlertType.ERROR, "Error", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        public void afficherListe(ActionEvent actionEvent) {
            menuc.navigateToFXML("/tn/esprit/pifx/prodR/ViewProdR.fxml",ptcComboBox);
        }


        private boolean validerSaisie() {
            boolean isValid = true; // Variable pour vérifier la validité de la saisie

            String description = descriptionArea.getText();
            if (description.length() < 8) {
                descriptionCommentLabel.setText("Minimum 8 caracteres");
                isValid = false;
            } else {
                descriptionCommentLabel.setText("");
            }
            String nomP = nomPField.getText();
            if (nomP.length() < 3 || nomP.length() > 12) {
                nomPCommentLabel.setText("Erreur ; le nom doit avoir minimum 3 caracteres et" +
                        " maximum 12");
                isValid = false;

            } else {
                nomPCommentLabel.setText("");
            }


            PtCollect selectedPt = ptcComboBox.getValue();
            if (selectedPt == null) {
                ptcCommentLabel.setText("Il faut remplir ce champ");
                isValid = false;
            }

            int ptc_id = selectedPt.getId();
            if (ptc_id == 0) {
                ptcCommentLabel.setText("Il faut remplir ce champ");
                isValid = false;

            } else {
                ptcCommentLabel.setText("");
            }
            //kkkkkkkk
            TypeDispo selectedt = typeComboBox.getValue();
            if (selectedt == null || selectedt.getId() == null) {
                typeCommentLabel.setText("Il faut remplir ce champ");
                isValid = false;
            } else {
                typeCommentLabel.setText("");
            }

            int ty_id = selectedt.getId().intValue();
            if (ty_id == 0) {
                typeCommentLabel.setText("Il faut remplir ce champ");
                isValid = false;
            }

            return isValid;
        }

        private boolean estEntier(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private void afficherAlerte(Alert.AlertType type, String title, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }
        private List<PtCollect> retrievePtcFromDatabase() {

            PtCollectService ptcs = new PtCollectService();
            ArrayList<PtCollect> ptc = (ArrayList<PtCollect>) ptcs.recuperer();
            return ptc;
        }

        private void fillPtcComboBox() {

            List<PtCollect> ptcs = retrievePtcFromDatabase();

            // Configurez le ComboBox pour afficher le nom de l'association à l'aide d'une expression lambda
            ptcComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(PtCollect p) {
                    return p != null ? p.getNomPc() : null;
                }

                @Override
                public PtCollect fromString(String string) {
                    return null; // Vous n'avez pas besoin de cette méthode pour un ComboBox de sélection
                }
            });

            // Ajoutez les associations au ComboBox
            ptcComboBox.getItems().addAll(ptcs);
        }


        @FXML
        void addImage(javafx.event.ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                selectedImageFile = selectedFile;
                javafx.scene.image.Image image = new Image(selectedFile.toURI().toString());
                imageid.setImage(image);
            }
        }
    }
