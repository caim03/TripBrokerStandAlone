package view.popup;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entityDB.ViaggioEntity;

import java.text.SimpleDateFormat;

public class TravelPopup extends PopupView{
    private ViaggioEntity viaggioEntity;

    public TravelPopup(ViaggioEntity prodottoEntity) {

        this.viaggioEntity = prodottoEntity;
        this.title = "Viaggio";

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                cityLbl = new Label("Città:"),
                quanitityLbl = new Label("Quantità:"),
                stateLbl = new Label("Stato:"),
                dateParLbl = new Label("Data Partenza:"),
                dateArrLbl = new Label("Data Arrivo:"),
                destLbl = new Label("Destinazione:"),
                parLbl = new Label("Ora Partenza:"),
                arrLbl = new Label("Ora Arrivo:"),
                mezzoLbl = new Label("Mezzo:"),
                classeLbl = new Label("Classe:"),
                stzParLbl = new Label("Stazione Partenza:"),
                stzArrLbl = new Label("Stazione Arrivo:");


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
        pane.add(dateParLbl, 0, 5);
        pane.add(dateArrLbl, 0, 6);
        pane.add(destLbl, 0, 7);
        pane.add(parLbl, 0, 8);
        pane.add(arrLbl, 0, 9);
        pane.add(mezzoLbl, 0, 10);
        pane.add(classeLbl, 0, 11);
        pane.add(stzParLbl, 0, 12);
        pane.add(stzArrLbl, 0, 13);

        pane.add(new Text(viaggioEntity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(viaggioEntity.getPrezzo())), 1, 1);
        pane.add(new Text(viaggioEntity.getCittà()), 1, 2);
        pane.add(new Text(Integer.toString(viaggioEntity.getQuantità())), 1, 3);
        pane.add(new Text(Integer.toString(viaggioEntity.getStato())), 1, 4);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(viaggioEntity.getDataInizio())), 1, 5);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(viaggioEntity.getDataArrivo())), 1, 6);
        pane.add(new Text(viaggioEntity.getDestinazione()), 1, 7);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(viaggioEntity.getDataInizio())), 1, 8);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(viaggioEntity.getDataArrivo())), 1, 9);
        pane.add(new Text(viaggioEntity.getMezzo()), 1, 10);
        pane.add(new Text(viaggioEntity.getClasse()), 1, 11);
        pane.add(new Text(viaggioEntity.getStazionePartenza()), 1, 12);
        pane.add(new Text(viaggioEntity.getStazioneArrivo()), 1, 13);

        setRow(13);

        VBox dialogVbox = new VBox(40, pane);

        return dialogVbox;
    }
}
