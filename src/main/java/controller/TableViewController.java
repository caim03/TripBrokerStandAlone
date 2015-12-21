package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.*;
import view.admin.PacketApproveView;
import view.popup.*;

public class TableViewController implements EventHandler<MouseEvent> {
    TableView<ProdottoEntity> tableView;

    public TableViewController(TableView<ProdottoEntity> list) {
        this.tableView = list;
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

            popupView = new PacketPopup((CreaPacchettoEntity) entity);
            if (tableView instanceof PacketApproveView)
                popupView = new ApprovalPopup(popupView, (CreaPacchettoEntity) entity, tableView);
        }

        popupView.show();
    }
}
