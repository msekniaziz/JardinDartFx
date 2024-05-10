    package tn.jardindart.controllers.prodR;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.util.StringConverter;
    import tn.jardindart.controllers.user.SessionManager;
    import tn.jardindart.models.PtCollect;
    import tn.jardindart.controllers.front_top;
    import tn.jardindart.models.ProdR;
    import tn.jardindart.models.TypeDispo;
    import tn.jardindart.services.ProdRService;
    import tn.jardindart.services.PtCollectService;
    import tn.jardindart.services.TypeDispoService;
    import tn.jardindart.test.HelloApplication;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.List;


    public class AddProdR {
        private File selectedImageFile;
        @FXML
        private ImageView imageid;
        @FXML
        private TextField nomPField;

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
            int id = SessionManager.getInstance().getUserFront();

            SessionManager.getInstance().setUserFront(id);
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

                ProdR newProdR = new ProdR(id, selectedPt.getId(), selectedt.getId().intValue(), false, description, nomP, justificatif);
                if (selectedImageFile != null) {
                    prodRService.uploadImage(selectedImageFile);
                }
                prodRService.ajouter(newProdR);
                afficherAlerte(Alert.AlertType.CONFIRMATION, "Success", "Product added successfully");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/prodR/ViewProdR.fxml"));
                nomPField.getScene().setRoot(fxmlLoader.load());

            } catch (Exception e) {
                afficherAlerte(Alert.AlertType.ERROR, "Error", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        public void afficherListe(ActionEvent actionEvent) {
            menuc.navigateToFXML("/tn.jardindart/prodR/ViewProdR.fxml",ptcComboBox);
        }


        private boolean validerSaisie() {
            String description = descriptionArea.getText();
            if (description.length() < 8) {
                descriptionCommentLabel.setText("Minimum 8 caracteres");
                return false;
            } else {
                descriptionCommentLabel.setText("");
            }
            String nomP = nomPField.getText();
            if (nomP.length() < 3 || nomP.length() > 12) {
                nomPCommentLabel.setText("Erreur ; le nom doit avoir minimum 3 caracteres et" +
                        " maximum 12");
                return false;

            } else {
                nomPCommentLabel.setText("");
            }


            PtCollect selectedPt = ptcComboBox.getValue();
            if (selectedPt == null) {
                ptcCommentLabel.setText("Il faut remplir ce champ");
                return false;
            }

            int ptc_id = selectedPt.getId();
            if (ptc_id == 0) {
                ptcCommentLabel.setText("Il faut remplir ce champ");
                return false;

            } else {
                ptcCommentLabel.setText("");
            }
            //kkkkkkkk
            TypeDispo selectedt = typeComboBox.getValue();
            if (selectedt == null || selectedt.getId() == null) {
                typeCommentLabel.setText("Il faut remplir ce champ");
            return false;
            } else {
                typeCommentLabel.setText("");
            }

            int ty_id = selectedt.getId().intValue();
            if (ty_id == 0) {
                typeCommentLabel.setText("Il faut remplir ce champ");
                return false;
            }

            return true;
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
        void addImage(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                selectedImageFile = selectedFile;
                Image image = new Image(selectedFile.toURI().toString());
                imageid.setImage(image);
            }
        }
    }
