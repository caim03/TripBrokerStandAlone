package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
        if ("Viaggio".equals(type)) {

            attachment = new Label("VIAGGIO");
        }
        else if ("Evento".equals(type)) {

            attachment = new Label("EVENTO");
        }
        else if ("Pernottamento".equals(type)) {

            attachment = new Label("PERNOTTAMENTO");
        }

        return attachment;
    }
}
