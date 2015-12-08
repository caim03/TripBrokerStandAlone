package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.*;
import view.*;

public class TableViewController implements EventHandler<MouseEvent> {
    TableView<ProdottoEntity> tableView;

    public TableViewController(TableView<ProdottoEntity> list) {
        this.tableView = list;
    }

    @Override
    public void handle(MouseEvent event) {
        ProdottoEntity prodottoEntity = tableView.getSelectionModel().getSelectedItem();
        PopupView popupView;

        if (prodottoEntity == null){
            return;
        }

        if ("Evento".equals(prodottoEntity.getTipo())){
            popupView = new EventPopup((EventoEntity) prodottoEntity);
        }

        else if ("Viaggio".equals(prodottoEntity.getTipo())){
            popupView = new TravelPopup((ViaggioEntity) prodottoEntity);
        }

        else if ("Pernottamento".equals(prodottoEntity.getTipo())){
            popupView = new StayPopup((PernottamentoEntity) prodottoEntity);
        }

        else {
            popupView = new PacketPopup((CreaPacchettoEntity) prodottoEntity);
        }

        popupView.generatePopup();
    }
}
