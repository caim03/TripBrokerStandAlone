package view.scout;

import controller.Constants;
import controller.InsertOfferController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.CalendarTimeTextField;
import org.controlsfx.control.Notifications;
import view.Collector;
import view.material.LayerPane;
import view.material.MaterialSpinner;
import view.material.MaterialTextField;
import view.material.NumericField;

import java.time.LocalDate;

public class OfferInsertionView extends LayerPane implements Collector {

    private TextField nameField, priceField, quField;
    private Node[] offerNode;
    private MaterialSpinner spinner;
    private VBox vBox;

    public String getOfferName() {
        /** @result String; return the name of the offer **/

        return nameField.getText();
    }
    public String getPriceoffer() {
        /** @result String; return the price of the offer as string **/

        return priceField.getText();
    }

    public int getOfferQuantity() {
        /** @result int; return the current quantity of the offer **/

        if ("".equals(quField.getText())) return 0;
        return Integer.parseInt(quField.getText());
    }

    public String getSpinner() {
        /** @result String; return the spinner value selected **/

        return spinner.getValue();
    }

    public Node[] getOfferNode() {
        /** @result Node[]; return the list of nodes attached to pane **/

        return offerNode;
    }

    public OfferInsertionView() {

        Label name = new Label("Name");
        nameField = new MaterialTextField();
        nameField.setPromptText("Insert offer name");

        Label price = new Label("Price");
        priceField = new NumericField();
        priceField.setPromptText("Insert offer price");

        Label qu = new Label("Quantity");
        quField = new NumericField(false);
        quField.setPromptText("Insert offer quantity");

        spinner = new MaterialSpinner(this, FXCollections.<String>observableArrayList(Constants.travel, Constants.event, Constants.stay));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(name, 0, 1);
        pane.add(price, 0, 2);
        pane.add(qu, 0, 3);

        pane.add(nameField, 1, 1, 2, 1);
        pane.add(priceField, 1, 2, 2, 1);
        pane.add(quField, 1, 3, 2, 1);
        pane.add(spinner, 1, 4, 2, 1);

        spinner.textProperty().addListener((observable, oldValue, newValue) -> {

            try { vBox.getChildren().remove(1); } catch (IndexOutOfBoundsException ignore) {}

            vBox.getChildren().add(fromOffer(newValue));
        });

        vBox = new VBox(pane, fromOffer(spinner.getValue()));

        attach(vBox);
    }

    private Node fromOffer(String type) {
        /** @param String; type of offer that must be inserted
         *  @result Node; return the node (pane) that contains new graphic elements **/

        Node attachment;

        if (Constants.travel.equals(type))
            attachment = travelAttachment();

        else if (Constants.event.equals(type))
            attachment = eventAttachment();

        else if (Constants.stay.equals(type))
            attachment = stayAttachment();

        else attachment = new Pane();

        return attachment;
    }

    private Node stayAttachment() {
        /** @return Node; return the pane with stay graphic elements **/

        offerNode = new Node[6];

        Label city = new Label("City");
        TextField ctyField = new MaterialTextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new MaterialTextField();
        locField.setPromptText("Insert location");

        Label from = new Label("From"), to = new Label("to");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePicker endPicker = new DatePicker(LocalDate.now());

        Label service = new Label("Service");
        MaterialSpinner srvSpinner = new MaterialSpinner(this, FXCollections.observableArrayList("Pensione completa", "Mezza pensione"));

        Label stars = new Label("Stars");
        MaterialSpinner strSpinner = new MaterialSpinner(this, FXCollections.<String>observableArrayList("1", "2", "3", "4", "5"));

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

        pane.add(ctyField, 1, 1);
        pane.add(locField, 1, 2);
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
        /** @result Node; return the pane with event graphic elements  **/

        offerNode = new Node[6];
        Label city = new Label("City");
        TextField ctyField = new MaterialTextField();
        ctyField.setPromptText("Insert city");

        Label location = new Label("Location");
        TextField locField = new MaterialTextField();
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

        pane.add(ctyField, 1, 1);
        pane.add(locField, 1, 2);
        pane.add(datePicker, 1, 3);
        pane.add(timePicker, 2, 3);
        pane.add(until, 3, 3);
        pane.add(endPicker, 4, 3);
        pane.add(seatField, 1, 4);

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
        /** @result Node; return the pane with stay graphic elements **/


        offerNode = new Node[8];
        Label departure = new Label("Departure");
        TextField depField = new MaterialTextField();
        depField.setPromptText("Insert departure station");

        DatePicker depDatePicker = new DatePicker(LocalDate.now());
        CalendarTimeTextField depTimePicker = new CalendarTimeTextField();

        Label arrival = new Label("Arrival");
        TextField arrField = new MaterialTextField();
        arrField.setPromptText("Insert arrival station");

        DatePicker arrDatePicker = new DatePicker(LocalDate.now());
        CalendarTimeTextField arrTimePicker = new CalendarTimeTextField();

        Label vehicle = new Label("Vehicol");
        MaterialSpinner vehSpinner = new MaterialSpinner(this, FXCollections.observableArrayList("Aereo", "Treno", "Bus"));

        Label classLbl = new Label("Class");
        MaterialSpinner clsSpinner = new MaterialSpinner(this, FXCollections.observableArrayList("First", "Second"));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(departure, 0, 1);
        pane.add(arrival, 0, 2);
        pane.add(vehicle, 0, 3);
        pane.add(classLbl, 3, 3);

        pane.add(depField, 1, 1);
        pane.add(arrField, 1, 2);
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

    public void harvest() {

        Notifications notifications = Notifications.create();

        if (!InsertOfferController.handle(getOfferName(), getPriceoffer(), getOfferQuantity(), getSpinner(), getOfferNode()))
            notifications.text("Non è stato possibile inserire l'offerta, per favore ricontrolla tutti i campi").title("Errore nell'inserimento").showWarning();

        else notifications.text("L'offerta è stata inserita con successo").title("Inserimento avvenuto con successo").showConfirm();
    }
}
