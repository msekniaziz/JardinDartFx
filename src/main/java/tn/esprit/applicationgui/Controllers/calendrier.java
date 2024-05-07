package tn.esprit.applicationgui.Controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.applicationgui.entites.ReponseBlog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.applicationgui.Controllers.ReponseBlogservice;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.*;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class calendrier  implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    private javafx.scene.control.Button showCalendarButton;

    @FXML
    private DatePicker searchDatePicker;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;


    private ReponseBlogservice reponseBlogservice; // Service to handle reservation data access

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        updateYearMonthLabels();

        today = ZonedDateTime.now();
        reponseBlogservice = new ReponseBlogservice();
        drawCalendar();
    }
@FXML
    public void backOneMonth() {
        dateFocus = dateFocus.minusMonths(1);
        updateYearMonthLabels();
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        updateYearMonthLabels();
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void searchDate(ActionEvent event) {
        LocalDate searchedDate = searchDatePicker.getValue();
        if (searchedDate != null) {

            clearHighlights();


        //    highlightDate(searchedDate);
        }
    }

    private void clearHighlights() {

        for (Node node : calendar.getChildren()) {
            if (node instanceof StackPane) {
                ((StackPane) node).setStyle("");
            }
        }
    }

    private void highlightDate(LocalDate searchedDate) {

        for (Node node : calendar.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                String style = "-fx-background-color: yellow;";
                LocalDate currentDate = LocalDate.parse(((Text) stackPane.getChildren().get(1)).getText());
                if (currentDate.equals(searchedDate)) {
                    stackPane.setStyle(style);
                }
            }
        }
    }
    private void updateYearMonthLabels() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(dateFocus.getMonth().toString());
    }
//    private void drawCalendar() {
//        year.setText(String.valueOf(dateFocus.getYear()));
//        month.setText(String.valueOf(dateFocus.getMonth()));
//
//        YearMonth yearMonth = YearMonth.of(dateFocus.getYear(), dateFocus.getMonthValue());
//        int daysInMonth = yearMonth.lengthOfMonth(); // Get the number of days in the month
//
//        // Calculate the first day of the week and the last day of the week
//        LocalDate date = yearMonth.atDay(1);
//
//        double calendarWidth = calendar.getPrefWidth();
//        double calendarHeight = calendar.getPrefHeight();
//        double strokeWidth = 1;
//        double spacingH = calendar.getHgap();
//        double spacingV = calendar.getVgap();
//
//        // List of reservations for a given month
//      List<ReponseBlog> reponseBlogs;
//        reponseBlogs = ReponseBlogservice.readAllReponses( date);
//
//        Map<LocalDate, List<ReponseBlog>> reservationMap = new HashMap<>();
//        for (ReponseBlog reponseBlog : reponseBlogs) { // Utiliser reponseBlogs1
//            LocalDate startDate = reponseBlog.getDate(); // Utilisez reponseBlog au singulier
//            LocalDate date = startDate;
////            while (!date.isAfter(lastDayOfMonth)) {
////                reservationMap.computeIfAbsent(date, k -> new ArrayList<>()).add(reponseBlog);
////                date = date.plusDays(1);
////            }
//        }
//
//        int numRows = (int) Math.ceil((double) (daysInMonth + firstDayOfMonth.getDayOfWeek().getValue() - 1) / 7);
//
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < 7; j++) {
//                StackPane stackPane = new StackPane();
//
//                Rectangle rectangle = new Rectangle();
//                rectangle.setFill(Color.TRANSPARENT);
//                rectangle.setStroke(Color.BLACK);
//                rectangle.setStrokeWidth(strokeWidth);
//                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
//                rectangle.setWidth(rectangleWidth);
//                double rectangleHeight = (calendarHeight / numRows) - strokeWidth - spacingV;
//                rectangle.setHeight(rectangleHeight);
//                stackPane.getChildren().add(rectangle);
//
//                int dayOfMonth = j + i * 7 + 1 - firstDayOfMonth.getDayOfWeek().getValue() + 1; // Calculate the current day of the month
//                if (dayOfMonth > 0 && dayOfMonth <= daysInMonth) { // Check if the day is valid for this month
//                    Text dayText = new Text(String.valueOf(dayOfMonth));
//                    stackPane.getChildren().add(dayText);
//
//                    LocalDate currentDate = yearMonth.atDay(dayOfMonth);
//                    List<ReponseBlog> reponseBlogs1;
//                    reponseBlogs1 = reservationMap.get(currentDate);
//                    if (reponseBlogs1 != null && !reponseBlogs1.isEmpty()) {
//                        // If responses exist for this day, display the data of the first response
//                        ReponseBlog firstResponse = reponseBlogs1.get(0);
//                        Text responseText = new Text("Contenu: " + firstResponse.getContenu());
//                        Rectangle responseBox = new Rectangle(rectangleWidth, rectangleHeight / 3);
//                        responseBox.setFill(Color.LIGHTGRAY);
//                        stackPane.getChildren().addAll(responseBox, responseText);
//                    }
//
//                }
//
//                calendar.getChildren().add(stackPane);
//            }
//        }
//    }
private void drawCalendar() {
    YearMonth selectedMonth = YearMonth.of(dateFocus.getYear(), dateFocus.getMonthValue());
    List<LocalDate> dates = new ArrayList<>();
    for (int i = 1; i <= selectedMonth.lengthOfMonth(); i++) {
        dates.add(selectedMonth.atDay(i));
    }

    for (LocalDate date : dates) {
        // Créer un conteneur pour chaque date
        VBox dateContainer = new VBox();
        dateContainer.setPrefWidth(100);
        dateContainer.setPrefHeight(100);
        dateContainer.setAlignment(javafx.geometry.Pos.CENTER);

        // Ajouter le texte de la date
        Label dateLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dateLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        dateContainer.getChildren().add(dateLabel);

        // Vérifier s'il y a des réponses de blog pour cette date
        List<ReponseBlog> reponsesForDate = reponseBlogservice.readAllReponses(date);
        if (!reponsesForDate.isEmpty()) {
            // Ajouter le nombre de réponses de blog pour cette date
            Label reponsesCountLabel = new Label("(" + reponsesForDate.size() + ")");
            reponsesCountLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-fill: #3f51b5;");
            dateContainer.getChildren().add(reponsesCountLabel);
        }

        // Ajouter le conteneur de date au calendrier
        calendar.getChildren().add(dateContainer);
    }
}

    @FXML
    void showCalendar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) showCalendarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSearchDatePicker(DatePicker searchDatePicker) {
        this.searchDatePicker = searchDatePicker;
    }
}
