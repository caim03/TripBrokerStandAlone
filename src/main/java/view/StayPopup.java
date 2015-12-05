package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entityDB.PernottamentoEntity;

public class StayPopup extends PopupView{
    PernottamentoEntity pernottamentoEntity;

    public StayPopup(PernottamentoEntity prodottoEntity) {
        this.pernottamentoEntity = prodottoEntity;
    }

    @Override
    public void generatePopup() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(new Stage());

        dialog.setTitle("Evento");

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                cityLbl = new Label("Città:"),
                quanitityLbl = new Label("Quantità:"),
                stateLbl = new Label("Stato:"),
                dateInLbl = new Label("Data Iniziale:"),
                dateFinLbl = new Label("Data Finale:"),
                qualityLbl = new Label("Qualità:"),
                serviceLbl = new Label("Servizio:"),
                placeLbl = new Label("Luogo:");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(priceLbl, 0, 1);
        pane.add(cityLbl, 0, 2);
        pane.add(quanitityLbl, 0, 3);
        pane.add(stateLbl, 0, 4);
        pane.add(dateInLbl, 0, 5);
        pane.add(dateFinLbl, 0, 6);
        pane.add(qualityLbl, 0, 7);
        pane.add(serviceLbl, 0, 8);
        pane.add(placeLbl, 0, 9);

        pane.add(new Text(pernottamentoEntity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(pernottamentoEntity.getPrezzo())), 1, 1);
        pane.add(new Text(pernottamentoEntity.getCittà()), 1, 2);
        pane.add(new Text(Integer.toString(pernottamentoEntity.getQuantità())), 1, 3);
        pane.add(new Text(Integer.toString(pernottamentoEntity.getStato())), 1, 4);
        pane.add(new Text(pernottamentoEntity.getDataInizio().toString()), 1, 5);
        pane.add(new Text(pernottamentoEntity.getDataFinale().toString()), 1, 6);
        pane.add(new Text(pernottamentoEntity.getQualità()),1, 7);
        pane.add(new Text(pernottamentoEntity.getServizio()),1, 8);
        pane.add(new Text(pernottamentoEntity.getLuogo()), 1, 9);

        VBox dialogVbox = new VBox(40, pane);
        Scene dialogScene = new Scene(dialogVbox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
