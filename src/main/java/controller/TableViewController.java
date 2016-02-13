package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.PacchettoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ProdottoEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.DBTablePane;
import view.admin.PacketApproveView;
import view.material.MaterialPopup;
import view.popup.*;

/**
 * ItemSelection EventHandler for generic catalog vision use case.
 */
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
            if (entity instanceof ViaggioGruppoEntity)
                popupView = new GroupTripPopup(entity.getId());
            else popupView = new PacketPopup(entity.getId());
            //if request comes from PacketApproveView, popup needs decoration
            if (pane instanceof PacketApproveView)
                popupView = new ApprovalPopup(popupView, (PacchettoEntity) entity, tableView);
        }

        new MaterialPopup(pane, popupView).show();
    }
}
