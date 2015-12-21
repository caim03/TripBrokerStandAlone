package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.PoliticheEntity;
import view.popup.PoliticsPopup;
import view.popup.PopupView;


public class ModifyPoliticsController implements EventHandler<MouseEvent> {
    private TableView<PoliticheEntity> list;

    public ModifyPoliticsController(TableView<PoliticheEntity> list) {
        this.list = list;
    }

    @Override
    public void handle(MouseEvent event) {
        PoliticheEntity politicheEntity = list.getSelectionModel().getSelectedItem();
        PopupView popupView;

        if (politicheEntity == null){
            return;
        }

        popupView = new PoliticsPopup(politicheEntity);
        popupView.show();
    }
}
