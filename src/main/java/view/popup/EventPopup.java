package view.popup;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entityDB.EventoEntity;

import java.text.SimpleDateFormat;


public class EventPopup extends PopupView{
    private EventoEntity eventoEntity;

    public EventPopup(EventoEntity prodottoEntity) {

        this.eventoEntity = prodottoEntity;
        this.title = "Evento";

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                cityLbl = new Label("Città:"),
                quanitityLbl = new Label("Quantità:"),
                stateLbl = new Label("Prenotazioni:"),
                dateLbl = new Label("Data:"),
                stayLbl = new Label("Numero Posto:"),
                startLbl = new Label("Ora inizio:"),
                endLbl = new Label("Ora Fine:"),
                placeLbl = new Label("Luogo:");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(priceLbl, 0, 1);
        pane.add(cityLbl, 0, 2);
        pane.add(quanitityLbl, 0, 3);
        pane.add(stateLbl, 0, 4);
        pane.add(dateLbl, 0, 5);
        pane.add(stayLbl, 0, 6);
        pane.add(startLbl, 0, 7);
        pane.add(endLbl, 0, 8);
        pane.add(placeLbl, 0, 9);

        pane.add(new Text(eventoEntity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(eventoEntity.getPrezzo())), 1, 1);
        pane.add(new Text(eventoEntity.getCittà()), 1, 2);
        pane.add(new Text(Integer.toString(eventoEntity.getQuantità())), 1, 3);
        pane.add(new Text(Integer.toString(eventoEntity.getPrenotazioni())), 1, 4);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(eventoEntity.getDataInizio())), 1, 5);
        pane.add(new Text(Integer.toString(eventoEntity.getNumeroPosto())), 1, 6);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(eventoEntity.getDataInizio())), 1, 7);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(eventoEntity.getDataFine())), 1, 8);
        pane.add(new Text(eventoEntity.getLuogo()), 1, 9);

        setRow(9);

        VBox dialogVbox = new VBox(40, pane);

        return dialogVbox;
    }
}
