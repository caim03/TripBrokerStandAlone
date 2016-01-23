package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.entityDB.*;
import view.DBTablePane;
import view.admin.PacketApproveView;
import view.material.MaterialPopup;
import view.popup.*;

public class TableViewController implements EventHandler<MouseEvent> {
    TableView<ProdottoEntity> tableView;
    DBTablePane pane;

    public TableViewController(TableView<ProdottoEntity> list, DBTablePane pane) {
        this.tableView = list;
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event) {

        ProdottoEntity entity = tableView.getSelectionModel().getSelectedItem();
        PopupView popupView;

        if (entity == null)
            return;

        String type = entity.getTipo();

        if (Constants.event.equals(type))
            popupView = new EventPopup((EventoEntity) entity);

        else if (Constants.travel.equals(type))
            popupView = new TravelPopup((ViaggioEntity) entity);

        else if (Constants.stay.equals(type))
            popupView = new StayPopup((PernottamentoEntity) entity);

        else if (Constants.group.equals(type))
            popupView = new GroupTripPopup((ViaggioGruppoEntity) entity);

        else {
            popupView = new PacketPopup(entity.getId());
            if (pane instanceof PacketApproveView)
                popupView = new ApprovalPopup(popupView, (CreaPacchettoEntity) entity, tableView);
        }

        new MaterialPopup(pane, popupView).show();
    }
}
