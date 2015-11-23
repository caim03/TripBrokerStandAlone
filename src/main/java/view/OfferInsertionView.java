package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OfferInsertionView {

    private static Parent basicGUI;

    public static Parent getInstance() {
        if (basicGUI == null) basicGUI = buildGUI();
        return basicGUI;
    }

    private static Parent buildGUI() {

        Label name = new Label("Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Insert offer name");

        Label price = new Label("Price");
        TextField priceField = new NumericField();
        priceField.setPromptText("Insert offer price");

        Spinner<String> spinner = new Spinner<>(FXCollections.observableArrayList("Viaggio", "Evento", "Pernottamento"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(name, 0, 1);
        pane.add(price, 0, 2);

        pane.add(new MaterialField(nameField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(priceField, Color.GOLD), 1, 2, 2, 1);
        pane.add(spinner, 1, 3, 2, 1);

        VBox box = new VBox(pane);
        box.setStyle("-fx-background-color: white");

        spinner.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try { box.getChildren().remove(1); } catch (IndexOutOfBoundsException ignore) {}

                box.getChildren().add(OfferInsertionView.fromOffer(newValue));
            }
        });

        return box;
    }

    private static Node fromOffer(String type) {

        Node attachment = null;

        if ("Viaggio".equals(type))
            attachment = travelAttachment();

        else if ("Evento".equals(type))
            attachment = eventAttachment();

        else if ("Pernottamento".equals(type))
            attachment = stayAttachment();

        return attachment;
    }

    private static Node stayAttachment() {
        return new Label("STAY");
    }

    private static Node eventAttachment() {
        return new Label("EVENT");
    }

    private static Node travelAttachment() {

        Label departure = new Label("Departure");
        TextField depField = new TextField();
        depField.setPromptText("Insert departure station");

        Label arrival = new Label("Arrival");
        TextField arrField = new NumericField();
        arrField.setPromptText("Insert arrival station");

        Label vehicle = new Label("Vehicol");
        Spinner<String> vehSpinner = new Spinner<>(FXCollections.observableArrayList("Aeroplane", "Train", "Bus"));

        Label classLbl = new Label("Class");
        Spinner<String> clsSpinner = new Spinner<>(FXCollections.observableArrayList("First", "Second"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(departure, 0, 1);
        pane.add(arrival, 0, 2);
        pane.add(vehicle, 0, 3);
        pane.add(classLbl, 3, 3);

        pane.add(new MaterialField(depField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(arrField, Color.GOLD), 1, 2, 2, 1);
        pane.add(vehSpinner, 1, 3, 2, 1);
        pane.add(clsSpinner, 4, 3, 2, 1);

        return pane;
    }
}