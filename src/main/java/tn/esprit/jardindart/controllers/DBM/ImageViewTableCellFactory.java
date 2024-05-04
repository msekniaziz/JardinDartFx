package tn.esprit.jardindart.controllers.DBM;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import tn.esprit.jardindart.models.DonBienMateriel;

public class ImageViewTableCellFactory implements Callback<TableColumn<DonBienMateriel, String>, TableCell<DonBienMateriel, String>> {

    @Override
    public TableCell<DonBienMateriel, String> call(TableColumn<DonBienMateriel, String> param) {
        return new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image(imagePath);
                    imageView.setImage(image);
                    imageView.setFitWidth(50); // Set the width of the image
                    imageView.setFitHeight(50); // Set the height of the image
                    setGraphic(imageView);
                }
            }
        };
    }
}