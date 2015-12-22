package view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;
import view.material.MaterialField;
import view.material.MaterialPopup;

public class BookingPopup extends PopupView {


    private PopupView popupView;
    private ViaggioGruppoEntity entity;
    private Button bookBtn;
    private TextField nameField, surnameField;
    private Spinner<Integer> bookingSpinner;

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

        nameField = new TextField();
        nameField.setPromptText("Inserisci nome");

        surnameField = new TextField();
        surnameField.setPromptText("Inserisci cognome");

        bookingSpinner = new Spinner<>(1, entity.getMax() - entity.getPrenotazioni(), 1);

        bookBtn = new Button("Prenota");

        GridPane pane = new GridPane();

        pane.add(new Text("Nome"), 0, 0);
        pane.add(new Text("Cognome"), 0, 1);
        pane.add(new Text("Quantità"), 0, 2);

        pane.add(new MaterialField(nameField, Color.GOLD), 1, 0);
        pane.add(new MaterialField(surnameField, Color.GOLD), 1, 1);
        pane.add(bookingSpinner, 1, 2);

        pane.add(bookBtn, 0, 3);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);
        pane.setPadding(new Insets(25, 25,  25, 25));

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        bookBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, this.parent.getListener(event -> {
            DBManager.initHibernate();
            PrenotazioneEntity booking = new PrenotazioneEntity();
            booking.setViaggioId(entity.getId());
            booking.setNome(nameField.getText());
            booking.setCognome(surnameField.getText());
            booking.setQuantità(bookingSpinner.getValue());
            PrenotazioneDaoHibernate.instance().store(booking);

            int bookings = bookingSpinner.getValue();
            entity.addPrenotazione(bookings);
            ViaggioGruppoDaoHibernate.instance().update(entity);

            for (AbstractEntity gruppoOfferta : GruppoOffertaDaoHibernate.instance().getByCriteria("where id_gruppo = " + entity.getId())) {

                OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getByCriteria("where id = " + ((GruppoOffertaEntity) gruppoOfferta).getIdOfferta()).get(0);
                offer.addPrenotazioni(bookings);
                OffertaDaoHibernate.instance().update(offer);
            }

            DBManager.shutdown();
        }));
    }
}
