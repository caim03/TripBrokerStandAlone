package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Node;
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

public class BookingPopup extends PopupView {


    private PopupView popupView;
    private ViaggioGruppoEntity entity;

    public BookingPopup(PopupView popupView, ViaggioGruppoEntity entity) {

        this.popupView = popupView;
        this.entity = entity;
        this.title = "Prenota viaggio";
    }

    @Override
    protected Parent generatePopup() {

        return new VBox(popupView.generatePopup(), generateFields());
    }

    private Parent generateFields() {

        TextField nameField = new TextField();
        nameField.setPromptText("Inserisci nome");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Inserisci cognome");

        Spinner<Integer> bookingSpinner = new Spinner<>(1, entity.getMax() - entity.getPrenotazioni(), 1);

        Button bookBtn = new Button("Prenota");
        bookBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

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

                OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getByCriteria("where id = " + ((GruppoOffertaEntity)gruppoOfferta).getIdOfferta()).get(0);
                offer.addPrenotazioni(bookings);
                OffertaDaoHibernate.instance().update(offer);
            }

            DBManager.shutdown();

            ((Node)event.getSource()).getScene().getWindow().hide();
        });

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

        return pane;
    }

}
