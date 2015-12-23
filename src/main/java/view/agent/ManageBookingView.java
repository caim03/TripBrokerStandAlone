package view.agent;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;
import view.material.MaterialPopup;
import view.material.PopupAttachable;
import view.popup.BookingListPopup;

public class ManageBookingView extends GridPane implements PopupAttachable {

    public ManageBookingView() {

        int size = layer.getChildren().size();
        if (size > 0) layer.getChildren().remove(0, size);

        getChildren().add(layer);
        setHgrow(layer, Priority.ALWAYS);
        setVgrow(layer, Priority.ALWAYS);
        layer.setAlignment(Pos.CENTER);

        DBListView list = new DBListView("from ViaggioGruppoEntity where prenotazioni > 0");
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null || newValue.equals(oldValue)) return;
            System.out.println("SELECTED");
            new MaterialPopup(
                    ManageBookingView.this,
                    new BookingListPopup((ViaggioGruppoEntity)newValue))
                    .show();
        });

        attach(list);

        list.fill();
    }


    @Override
    public void attach(Node e) {

        System.out.println("ATTACH");
        if (e instanceof MaterialPopup) {
            attach((MaterialPopup) e);
            return;
        }

        layer.getChildren().add(e);
    }

    @Override public void detach(Node e) {
        if (e instanceof MaterialPopup) {
            detach((MaterialPopup) e);
            return;
        }
        layer.getChildren().remove(e);
    }
    public void detach(MaterialPopup e) {

        FadeTransition ft = new FadeTransition(Duration.millis(100), e);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();

        ft.setOnFinished(event -> layer.getChildren().remove(e));
    }

    public void attach(MaterialPopup e) {

        FadeTransition ft = new FadeTransition(Duration.millis(200), e);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        ft.setOnFinished(event -> layer.getChildren().add(e));
    }
}
