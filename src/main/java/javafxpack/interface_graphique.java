package javafxpack;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class interface_graphique extends Application implements EventHandler<ActionEvent> {

    private ComboBox<String> sourceTimeZoneComboBox;
    private ComboBox<String> targetTimeZoneComboBox;
    private TextField timeTextField;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the UI elements
        primaryStage.setTitle("Time Converter");
        Label sourceTimeZoneLabel = new Label("Source Time Zone:");
        sourceTimeZoneComboBox = new ComboBox<>();
        sourceTimeZoneComboBox.getItems().addAll(ZoneId.getAvailableZoneIds());
        sourceTimeZoneComboBox.getSelectionModel().select("Asia/Tokyo");
        Label targetTimeZoneLabel = new Label("Target Time Zone:");
        targetTimeZoneComboBox = new ComboBox<>();
        targetTimeZoneComboBox.getItems().addAll(ZoneId.getAvailableZoneIds());
        targetTimeZoneComboBox.getSelectionModel().select("America/New_York");
        Label timeLabel = new Label("Time:");
        timeTextField = new TextField("yyyy-MM-dd HH:mm:ss");
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(this);
        resultLabel = new Label();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(sourceTimeZoneLabel, 0, 0);
        gridPane.add(sourceTimeZoneComboBox, 1, 0);
        gridPane.add(targetTimeZoneLabel, 0, 1);
        gridPane.add(targetTimeZoneComboBox, 1, 1);
        gridPane.add(timeLabel, 0, 2);
        gridPane.add(timeTextField, 1, 2);
        gridPane.add(convertButton, 1, 3);
        vbox.getChildren().addAll(gridPane, resultLabel);
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        // Get the source time zone and time
        String sourceTimeZoneId = sourceTimeZoneComboBox.getSelectionModel().getSelectedItem();
        ZoneId sourceTimeZone = ZoneId.of(sourceTimeZoneId);
        String timeString = timeTextField.getText();
        LocalDateTime localDateTime = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime sourceZonedDateTime = localDateTime.atZone(sourceTimeZone);

        // Convert to the target time zone
        String targetTimeZoneId = targetTimeZoneComboBox.getSelectionModel().getSelectedItem();
        ZoneId targetTimeZone = ZoneId.of(targetTimeZoneId);
        ZonedDateTime targetZonedDateTime = sourceZonedDateTime.withZoneSameInstant(targetTimeZone);

        // Format the result
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String resultString = targetZonedDateTime.format(formatter);

        // Set the result label
        resultLabel.setText(resultString);
    }
}