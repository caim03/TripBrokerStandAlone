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
import view.material.MaterialField;
import view.material.NumericField;

public class OfferInsertionView {
    private static TextField nameField;
    private static TextField priceField;
    private static Node[] offerNode;
    private static Spinner<String> spinner;


    private static Parent basicGUI;

    public static Parent getInstance() {
        if (basicGUI == null) basicGUI = buildGUI();
        return basicGUI;
    }

    public static String getOfferName() {
        return nameField.getText();
    }

    public static String getPriceoffer() {
        return priceField.getText();
    }

    public static String getSpinner() {
        return spinner.getValue();
    }

    public static Node[] getOfferNode() {
        return offerNode;
    }

    private static Parent buildGUI() {

        Label name = new Label("Name");
        nameField = new TextField();
        nameField.setPromptText("Insert offer name");

        Label price = new Label("Price");
        priceField = new NumericField();
        priceField.setPromptText("Insert offer price");

        spinner = new Spinner<>(FXCollections.observableArrayList("Viaggio", "Evento", "Pernottamento"));
        spinner.getEditor().setPromptText(null);

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

        VBox box = new VBox(pane, fromOffer(spinner.getValue()));
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

        offerNode = new Node[3];

        Label city = new Label("City");
        TextField ctyField = new TextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new TextField();
        locField.setPromptText("Insert location");

        Label stars = new Label("Stars");
        Spinner<String> strSpinner = new Spinner<>(FXCollections.observableArrayList("1", "2", "3", "4", "5"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(location, 0, 2);
        pane.add(stars, 0, 3);

        pane.add(new MaterialField(ctyField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(locField, Color.GOLD), 1, 2, 2, 1);
        pane.add(strSpinner, 1, 3);

        offerNode[0] = ctyField;
        offerNode[1] = locField;
        offerNode[2] = strSpinner;

        return pane;
    }

    private static Node eventAttachment() {

        offerNode = new Node[3];
        Label city = new Label("City");
        TextField ctyField = new TextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new TextField();
        locField.setPromptText("Insert location");

        Label seat = new Label("Seat");
        TextField seatField = new NumericField(false);
        seatField.setPromptText("Insert seat number (leave empty for parterre)");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(location, 0, 2);
        pane.add(seat, 0, 3);

        pane.add(new MaterialField(ctyField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(locField, Color.GOLD), 1, 2, 2, 1);
        pane.add(new MaterialField(seatField, Color.GOLD), 1, 3);

        offerNode[0] = ctyField;
        offerNode[1] = locField;
        offerNode[2] = seatField;

        return pane;
    }

    private static Node travelAttachment() {

        offerNode = new Node[4];
        Label departure = new Label("Departure");
        TextField depField = new TextField();
        depField.setPromptText("Insert departure station");

        Label arrival = new Label("Arrival");
        TextField arrField = new TextField();
        arrField.setPromptText("Insert arrival station");

        Label vehicle = new Label("Vehicol");
        Spinner<String> vehSpinner = new Spinner<>(FXCollections.observableArrayList("Aeroplane", "Train", "Bus"));

        Label classLbl = new Label("Class");
        Spinner<String> clsSpinner = new Spinner<>(FXCollections.observableArrayList("First", "Second"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(departure, 0, 1);
        pane.add(arrival, 0, 2);
        pane.add(vehicle, 0, 3);
        pane.add(classLbl, 3, 3);

        pane.add(new MaterialField(depField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(arrField, Color.GOLD), 1, 2, 2, 1);
        pane.add(vehSpinner, 1, 3, 2, 1);
        pane.add(clsSpinner, 4, 3, 2, 1);

        offerNode[0] = depField;
        offerNode[1] = arrField;
        offerNode[2] = vehSpinner;
        offerNode[3] = clsSpinner;

        return pane;
    }
}
