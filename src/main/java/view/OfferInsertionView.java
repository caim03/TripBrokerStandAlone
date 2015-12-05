package view;

import controller.InsertOfferController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.scene.control.CalendarTimeTextField;
import org.controlsfx.control.Notifications;
import view.material.MaterialField;
import view.material.NumericField;

import java.time.LocalDate;

public class OfferInsertionView extends VBox implements Cloneable {

    private TextField nameField, priceField, quField;
    private Node[] offerNode;
    private Spinner<String> spinner;

    private static OfferInsertionView basicGUI;

    @Override
    public OfferInsertionView clone() throws CloneNotSupportedException {

        return (OfferInsertionView) super.clone();
    }

    public static OfferInsertionView getInstance() {
        if (basicGUI == null) {

            basicGUI = new OfferInsertionView();
            basicGUI.buildGUI();
        }

        return basicGUI;
    }

    public String getOfferName() {
        return nameField.getText();
    }

    public String getPriceoffer() {
        return priceField.getText();
    }

    public int getOfferQuantity() {
        return Integer.parseInt(quField.getText());
    }

    public String getSpinner() {
        return spinner.getValue();
    }

    public Node[] getOfferNode() {
        return offerNode;
    }

    public void harvest() {

        if (InsertOfferController.handle(getOfferName(), getPriceoffer(), getOfferQuantity(), getSpinner(), getOfferNode()));

        else {

            Notifications error = Notifications.create();
            error.text("Could not insert offer, please check fields and retry");
            error.title("Insertion error");
            error.showWarning();
        }
    }

    private void buildGUI() {

        Label name = new Label("Name");
        nameField = new TextField();
        nameField.setPromptText("Insert offer name");

        Label price = new Label("Price");
        priceField = new NumericField();
        priceField.setPromptText("Insert offer price");

        Label qu = new Label("Quantity");
        quField = new NumericField(false);
        quField.setPromptText("Insert offer quantity");

        spinner = new Spinner<>(FXCollections.observableArrayList("Viaggio", "Evento", "Pernottamento"));
        spinner.getEditor().setPromptText(null);

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(name, 0, 1);
        pane.add(price, 0, 2);
        pane.add(qu, 0, 3);

        pane.add(new MaterialField(nameField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(priceField, Color.GOLD), 1, 2, 2, 1);
        pane.add(new MaterialField(quField, Color.GOLD), 1, 3, 2, 1);
        pane.add(spinner, 1, 4, 2, 1);

        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {

            try { basicGUI.getChildren().remove(1); } catch (IndexOutOfBoundsException ignore) {}

            basicGUI.getChildren().add(fromOffer(newValue));
        });

        basicGUI.getChildren().addAll(pane, fromOffer(spinner.getValue()));
    }

    private Node fromOffer(String type) {

        Node attachment = null;

        if ("Viaggio".equals(type))
            attachment = travelAttachment();

        else if ("Evento".equals(type))
            attachment = eventAttachment();

        else if ("Pernottamento".equals(type))
            attachment = stayAttachment();

        return attachment;
    }

    private Node stayAttachment() {

        offerNode = new Node[6];

        Label city = new Label("City");
        TextField ctyField = new TextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new TextField();
        locField.setPromptText("Insert location");

        Label from = new Label("From"), to = new Label("to");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePicker endPicker = new DatePicker(LocalDate.now());

        Label service = new Label("Service");
        Spinner<String> srvSpinner = new Spinner<>(FXCollections.observableArrayList("Pensione completa", "Mezza pensione"));

        Label stars = new Label("Stars");
        Spinner<String> strSpinner = new Spinner<>(FXCollections.observableArrayList("1", "2", "3", "4", "5"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(location, 0, 2);
        pane.add(from, 0, 3);
        pane.add(to, 2, 3);
        pane.add(stars, 0, 4);
        pane.add(service, 0, 5);

        pane.add(new MaterialField(ctyField, Color.GOLD), 1, 1);
        pane.add(new MaterialField(locField, Color.GOLD), 1, 2);
        pane.add(datePicker, 1, 3);
        pane.add(endPicker, 3, 3);
        pane.add(strSpinner, 1, 4);
        pane.add(srvSpinner, 1, 5);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = ctyField;
        offerNode[1] = locField;
        offerNode[2] = strSpinner;
        offerNode[3] = srvSpinner;
        offerNode[4] = datePicker;
        offerNode[5] = endPicker;

        return pane;
    }

    private Node eventAttachment() {

        offerNode = new Node[6];
        Label city = new Label("City");
        TextField ctyField = new TextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new TextField();
        locField.setPromptText("Insert location");

        Label date = new Label("Date"), until = new Label("until");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        CalendarTimeTextField timePicker = new CalendarTimeTextField();
        CalendarTimeTextField endPicker = new CalendarTimeTextField();

        Label seat = new Label("Seat");
        TextField seatField = new NumericField(false);
        seatField.setPromptText("Insert seat number (leave empty for parterre)");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(location, 0, 2);
        pane.add(date, 0, 3);
        pane.add(seat, 0, 4);

        pane.add(new MaterialField(ctyField, Color.GOLD), 1, 1);
        pane.add(new MaterialField(locField, Color.GOLD), 1, 2);
        pane.add(datePicker, 1, 3);
        pane.add(timePicker, 2, 3);
        pane.add(until, 3, 3);
        pane.add(endPicker, 4, 3);
        pane.add(new MaterialField(seatField, Color.GOLD), 1, 4);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = ctyField;
        offerNode[1] = locField;
        offerNode[2] = seatField;
        offerNode[3] = datePicker;
        offerNode[4] = timePicker;
        offerNode[5] = endPicker;

        return pane;
    }

    private Node travelAttachment() {

        offerNode = new Node[8];
        Label departure = new Label("Departure");
        TextField depField = new TextField();
        depField.setPromptText("Insert departure station");

        DatePicker depDatePicker = new DatePicker(LocalDate.now());
        CalendarTimeTextField depTimePicker = new CalendarTimeTextField();

        Label arrival = new Label("Arrival");
        TextField arrField = new TextField();
        arrField.setPromptText("Insert arrival station");

        DatePicker arrDatePicker = new DatePicker(LocalDate.now());
        CalendarTimeTextField arrTimePicker = new CalendarTimeTextField();

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

        pane.add(new MaterialField(depField, Color.GOLD), 1, 1);
        pane.add(new MaterialField(arrField, Color.GOLD), 1, 2);
        pane.add(vehSpinner, 1, 3);
        pane.add(clsSpinner, 4, 3);

        pane.add(depDatePicker, 3, 1);
        pane.add(arrDatePicker, 3, 2);
        pane.add(depTimePicker, 4, 1);
        pane.add(arrTimePicker, 4, 2);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = depField;
        offerNode[1] = arrField;
        offerNode[2] = vehSpinner;
        offerNode[3] = clsSpinner;
        offerNode[4] = depDatePicker;
        offerNode[5] = depTimePicker;
        offerNode[6] = arrDatePicker;
        offerNode[7] = arrTimePicker;

        return pane;
    }
}
