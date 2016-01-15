package view.popup;

import controller.BookingController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Material;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.material.*;

public class BookingPopup extends PopupView {


    private PopupView popupView;
    private ViaggioGruppoEntity entity;
    private Button bookBtn;
    private TextField nameField, surnameField;
    private MaterialSpinner bookingSpinner;
    private GridPane pane;

    public BookingPopup(PopupView popupView, ViaggioGruppoEntity entity) {

        this.popupView = popupView;
        this.entity = entity;
        this.title = "Prenota viaggio";

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {

        return new VBox(popupView.generatePopup(), generateFields());
    }

    private Parent generateFields() {

        nameField = new MaterialTextField();
        nameField.setPromptText("Inserisci nome");

        surnameField = new MaterialTextField();
        surnameField.setPromptText("Inserisci cognome");

        bookBtn = new FlatButton("Prenota");

        pane = new GridPane();

        pane.add(new Text("Nome"), 0, 0);
        pane.add(new Text("Cognome"), 0, 1);
        pane.add(new Text("Quantità"), 0, 2);

        pane.add(nameField, 1, 0);
        pane.add(surnameField, 1, 1);

        pane.add(bookBtn, 0, 3);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);
        pane.setPadding(new Insets(25, 25,  25, 25));
        pane.setStyle("-fx-background-color: white");

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {

        super.setParent(parent);

        parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && (newValue instanceof LayerPane)) {
                bookingSpinner = new MaterialSpinner((LayerPane) newValue, 1, entity.getMax() - entity.getPrenotazioni());
                pane.add(bookingSpinner, 1, 2);
            }
        });

        bookBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            String name = nameField.getText(),
                   surname = surnameField.getText(),
                   qu = bookingSpinner.getValue();

            if ("".equals(name) || "".equals(surname) || "".equals(qu))
                Notifications.create().text("Riempire tutti i campi obbligatori").showWarning();

            else {

                BookingController.handle(entity, nameField.getText(), surnameField.getText(),
                        Integer.parseInt(bookingSpinner.getValue()));
                this.parent.hide();
            }
        });
    }
}
