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
        tableView.getSelectionModel().clearSelection();
        PopupView popupView;

        if (entity == null) return;

        if (entity instanceof OffertaEntity) popupView = OfferPopup.getCatalogPopup((OffertaEntity) entity);
        else {
            popupView = new PacketPopup(entity.getId());
            if (pane instanceof PacketApproveView)
                popupView = new ApprovalPopup(popupView, (CreaPacchettoEntity) entity, tableView);
        }

        new MaterialPopup(pane, popupView).show();
    }
}
