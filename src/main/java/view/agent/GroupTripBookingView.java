package view.agent;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.entityDB.ViaggioGruppoEntity;
import view.material.MaterialPopup;
import view.material.PopupAttachable;
import view.popup.BookingPopup;
import view.popup.GroupTripPopup;
import view.material.DBListView;
import view.popup.PopupView;

public class GroupTripBookingView extends VBox implements PopupAttachable {

    DBListView list;

    public GroupTripBookingView() {

        list = new DBListView("from ViaggioGruppoEntity entity where entity.prenotazioni < entity.max");
        list.fill();
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null) return;
            PopupView popupView = new BookingPopup(
                    new GroupTripPopup((ViaggioGruppoEntity) newValue), (ViaggioGruppoEntity) newValue);
            new MaterialPopup(this, popupView).show();
        });

        getChildren().add(layer);
        attach(list);
    }

    @Override public void attach(Node e) { layer.getChildren().add(e); }
    @Override public void detach(Node e) { layer.getChildren().remove(e); }
    public void detach(int p) { layer.getChildren().remove(layer.getChildren().get(p)); }
}
