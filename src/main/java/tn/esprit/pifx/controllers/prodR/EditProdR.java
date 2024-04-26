package tn.esprit.pifx.controllers.prodR;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import tn.esprit.pifx.controllers.front_top;
import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.services.ProdRService;
import tn.esprit.pifx.services.PtCollectService;
import tn.esprit.pifx.services.TypeDispoService;

import java.util.ArrayList;
import java.util.List;

public class EditProdR {



    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField nomPField,justificatifField;

    @FXML
    private ComboBox<PtCollect> ptcComboBox;

    @FXML
    private ComboBox<TypeDispo> typeComboBox;
    @FXML
    private Label ptcCommentLabel;

    @FXML
    private Label typeCommentLabel;

    @FXML
    private Label descriptionCommentLabel;

    @FXML
    private Label nomPCommentLabel;
    private front_top menuc;


    private int id;
    public ProdRService prodRService = new ProdRService();


    public EditProdR() {
    }
    public void initialize(int id) {
        this.id = id;
        fillPtcComboBox();
        fillTypeComboBox();
        populateFields();
        menuc=new front_top();
    }
    private void populateFields() {
        ProdR p2u = prodRService.getById(id); // Remplacez getById par la méthode que vous utilisez pour récupérer un don par son ID

        if (p2u != null) {
            descriptionArea.setText(p2u.getDescription());
            nomPField.setText(p2u.getNomP());
            justificatifField.setText(p2u.getJustificatif());
            int pId = p2u.getPtcId();
            for (PtCollect ptc : ptcComboBox.getItems()) {
                if (ptc.getId() == pId) {
                    ptcComboBox.setValue(ptc);
                    break;
                }
            }
            int tId = p2u.getTypeProdId();
            for (TypeDispo ptc : typeComboBox.getItems()) {
                if (ptc.getId() == tId) {
                    typeComboBox.setValue(ptc);
                    break;
                }
            }
        }
    }

    @FXML
    public void modifierProdR(javafx.event.ActionEvent actionEvent) {
        ProdR p2u = prodRService.getById(id);

        if (validerSaisie()) {
            if (p2u != null) {
                int idF = p2u.getId();
                String description = descriptionArea.getText();
                String nomP = nomPField.getText();
                String justificatif = justificatifField.getText();
                PtCollect ptcId = ptcComboBox.getValue();
                TypeDispo typeProdId = typeComboBox.getValue();

                if (ptcId != null && typeProdId != null) {
                    ProdR updatedProdR = new ProdR(idF, p2u.getUserId(), ptcId.getId(), typeProdId.getId(), false, description, nomP, justificatif);
                    prodRService.modifier(updatedProdR);
                    afficherAlerte(Alert.AlertType.INFORMATION, "Success", "Product modified successfully");
                menuc.navigateToFXML("/tn/esprit/pifx/ptCollect/ViewPtCollect.fxml" ,nomPField);
                } else {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une valeur dans les ComboBox.");
                }
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les données du ProdR.");
            }
        }
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
    private List<TypeDispo> retrieveTypeFromDatabase() {

        TypeDispoService ts = new TypeDispoService();
        ArrayList<TypeDispo> tl = (ArrayList<TypeDispo>) ts.recuperer();
        return tl;
    }
    private void fillPtcComboBox() {

        List<PtCollect> ptcs = retrievePtcFromDatabase();

        ptcComboBox.setConverter(new StringConverter<PtCollect>() {
            @Override
            public String toString(PtCollect p) {
                return p != null ? p.getNomPc() : null;
            }

            @Override
            public PtCollect fromString(String string) {
                return null; // Vous n'avez pas besoin de cette méthode pour un ComboBox de sélection
            }
        });
        ptcComboBox.getItems().addAll(ptcs);
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
}
