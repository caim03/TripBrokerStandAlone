package view.agent;

import controller.Constants;
import controller.SearchProductController;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import view.Collector;
import view.material.*;

import java.time.LocalDate;

public class SellProductView extends LayerPane implements Collector {

    private VBox vBox;
    private Node[] offerNode;
    private MaterialSpinner spinner;
    private MaterialButton button;
    private DBListView listView;
    private GridPane pane;

    public SellProductView(){

        spinner = new MaterialSpinner(this, FXCollections.<String>observableArrayList(Constants.travel,
                Constants.event, Constants.stay, Constants.packet));

        button = new ElevatedButton("Cerca");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(spinner, 0, 0, 5, 1);
        pane.add(button, 6, 0);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                harvest();
                try {
                listView.refresh();
                } catch (NullPointerException nullPointer) {
                    Notifications.create().title("Campi vuoti").text("Riempire tutti i campi obbligatori").show();
                    return;
                }
                vBox.getChildren().clear();
                vBox.getChildren().add(listView);
            }
        });

        spinner.textProperty().addListener((observable, oldValue, newValue) -> {

            try { vBox.getChildren().remove(1); } catch (IndexOutOfBoundsException ignore) {}

            vBox.getChildren().add(fromOffer(newValue));
        });

        vBox = new VBox(pane, fromOffer(spinner.getValue()));

        attach(vBox);
    }

    private Node fromOffer(String type) {
        /** @param String; string that represents the type of the offer:
         *               - Event
         *               - Travel
         *               - Stay
         *               - Packet
         *  @return Node; return a node to attach at the main pane **/

        Node attachment;

        if (Constants.travel.equals(type))
            attachment = travelAttachment();

        else if (Constants.event.equals(type))
            attachment = eventAttachment();

        else if (Constants.stay.equals(type))
            attachment = stayAttachment();

        else if (Constants.packet.equals(type))
            attachment = packetAttachment();

        else attachment = new Pane();

        return attachment;
    }

    private Node stayAttachment() {
        /** @return Node; return a node to attach at the main pane **/

        offerNode = new Node[3];

        Label city = new Label("City");
        TextField ctyField = new MaterialTextField();
        ctyField.setPromptText("Insert city");

        Label checkIn = new Label("Check-in");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Label checkOut = new Label("Check-out");
        DatePicker endPicker = new DatePicker(LocalDate.now());

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(checkIn, 0, 2);
        pane.add(checkOut, 0, 3);

        pane.add(ctyField, 1, 1);
        pane.add(datePicker, 1, 2);
        pane.add(endPicker, 1, 3);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = ctyField;
        offerNode[1] = datePicker;
        offerNode[2] = endPicker;

        return pane;
    }

    private Node eventAttachment() {
        /** @return Node; return a node to attach at the main pane **/

        offerNode = new Node[2];
        Label city = new Label("City");
        TextField ctyField = new MaterialTextField();
        ctyField.setPromptText("Insert city");

        Label date = new Label("Date");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);

        pane.add(city, 0, 1);
        pane.add(date, 0, 2);

        pane.add(ctyField, 1, 1);
        pane.add(datePicker, 1, 2);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = ctyField;
        offerNode[1] = datePicker;

        return pane;
    }

    private Node travelAttachment() {
        /** @return Node; return a node to attach at the main pane **/

        offerNode = new Node[6];
        Label departure = new Label("Departure");
        TextField depField = new MaterialTextField();
        depField.setPromptText("Insert departure station");

        DatePicker depTimePicker = new DatePicker(LocalDate.now());

        Label arrival = new Label("Arrival");
        TextField arrField = new MaterialTextField();
        arrField.setPromptText("Insert arrival station");

        DatePicker arrTimePicker = new DatePicker(LocalDate.now());

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
        pane.add(classLbl, 0, 4);

        pane.add(depField, 1, 1);
        pane.add(arrField, 1, 2);
        pane.add(vehSpinner, 1, 3);
        pane.add(clsSpinner, 1, 4);

        pane.add(depTimePicker, 3, 1);
        pane.add(arrTimePicker, 3, 2);

        pane.setPadding(new Insets(25, 25, 25, 25));

        offerNode[0] = depField;
        offerNode[1] = arrField;
        offerNode[2] = vehSpinner;
        offerNode[3] = clsSpinner;
        offerNode[4] = depTimePicker;
        offerNode[5] = arrTimePicker;

        return pane;
    }

    private Node packetAttachment() {
        /** @return Node; return a node to attach at the main pane **/

        return null;
    }

    @Override
    public void harvest() {

        listView = SearchProductController.handle(spinner.getValue(), offerNode, this);
    }
}
