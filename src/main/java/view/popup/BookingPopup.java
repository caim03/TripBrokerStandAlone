package view.popup;

import controller.agent.BookingController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.ViaggioGruppoEntity;
import org.controlsfx.control.Notifications;
import view.material.*;

public class BookingPopup extends PopupDecorator {

    private ViaggioGruppoEntity entity;
    private Button bookBtn;
    private TextField nameField, surnameField, phoneField;
    private Spinner<Integer> bookingSpinner;
    private GridPane pane;

    public BookingPopup(ViaggioGruppoEntity entity) {
        super(new GroupTripPopup(entity));
        this.entity = entity;
    }

    @Override
    protected Node decorate() {

        nameField = new MaterialTextField();
        nameField.setPromptText("Inserisci nome");

        surnameField = new MaterialTextField();
        surnameField.setPromptText("Inserisci cognome");

        phoneField = new NumericField(false);
        phoneField.setPromptText("Inserisci numero di telefono");

        bookBtn = new FlatButton("Prenota");

        pane = new GridPane();

        pane.add(new Text("Nome"), 0, 0);
        pane.add(new Text("Cognome"), 0, 1);
        pane.add(new Text("Numero"), 0, 2);
        pane.add(new Text("Quantità"), 0, 3);

        pane.add(nameField, 1, 0);
        pane.add(surnameField, 1, 1);
        pane.add(phoneField, 1, 2);

        bookingSpinner = new Spinner<>(1, entity.getMax() - (entity.getPrenotazioni() + entity.getAcquisti()), 1);
        pane.add(bookingSpinner, 1, 3);

        pane.add(bookBtn, 0, 4);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);
        pane.setPadding(new Insets(25, 25,  25, 25));
        pane.setStyle("-fx-background-color: white");

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {

        super.setParent(parent);

        bookBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            String name = nameField.getText(),
                   surname = surnameField.getText();
            int phone = (int) ((NumericField) phoneField).getNumber();
            int qu = bookingSpinner.getValue();

            ProgressCircle circle = ProgressCircle.miniCircle();
            pane.getChildren().remove(bookBtn);
            pane.add(circle,0, 4);

            new Thread(() -> {
                boolean result = false;
                try { result = BookingController.handle(entity, name, surname, phone, qu); }
                catch (Exception e) {
                    Platform.runLater(() -> {
                        Notifications.create().text(e.getMessage()).showWarning();
                        pane.getChildren().remove(circle);
                        pane.add(bookBtn,0, 4);
                    });
                    return;
                }

                if (result)
                    Platform.runLater(() ->
                        Notifications.
                                create().
                                text("Prenotazione effettuata con successo").
                                show());
                else
                    Platform.runLater(() ->
                            Notifications.
                                    create().
                                    text("Prenotazione fallita").
                                    showError());

                Platform.runLater(parent::hide);
            }).start();
        });
    }
}
