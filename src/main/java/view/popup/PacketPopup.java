package view.popup;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PacchettoOffertaDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PacchettoOffertaEntity;
import view.material.DBCell;
import view.material.DBListView;

import java.util.ArrayList;
import java.util.List;


public class PacketPopup extends PopupView {

    private CreaPacchettoEntity entity;
    private ListView list;

    public PacketPopup(CreaPacchettoEntity prodottoEntity) {

        this.entity = prodottoEntity;
        this.title = "Pacchetto";
    }

    @Override
    protected Parent generatePopup() {

        String state;

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                stateLbl = new Label("Stato:"),
                motivLbl = new Label("Motivazione:"),
                creatLbl = new Label("Creatore:");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(nameLbl, 0, 0);
        pane.add(priceLbl, 0, 1);
        pane.add(stateLbl, 0, 2);
        pane.add(motivLbl, 0, 3);
        pane.add(creatLbl, 0, 4);

        pane.add(new Text(entity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);
        if (entity.getStato() == 0){
            state = "In attesa di approvazione";
        }

        else if (entity.getStato() == 1){
            state = "Approvato";
        }

        else{
            state = "Rifiutato";
        }
        pane.add(new Text(state), 1, 2);
        pane.add(new Text((entity.getMotivazione())), 1, 3);
        pane.add(new Text(getEmployee(entity.getCreatore())), 1, 4);

        setRow(4);

        generateList();

        VBox dialogVbox = new VBox(pane, list);

        list.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.doubleValue() > dialogVbox.getPrefWidth()) {
                dialogVbox.prefWidthProperty().setValue(newValue);
            }
        });

        list.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (oldValue != null)
                    dialogVbox.prefHeightProperty().subtract(oldValue.doubleValue());
                dialogVbox.prefHeightProperty().add(newValue.doubleValue());
            }
        });

        dialogVbox.setStyle("-fx-background-color: white");

        return dialogVbox;
    }

    private void generateList() {

        list = new ListView();
        list.setCellFactory(callback -> new DBCell());
        list.getItems().addAll(entity.retrieveOffers());
    }
}