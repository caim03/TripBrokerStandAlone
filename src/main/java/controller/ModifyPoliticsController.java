package controller;

import javafx.scene.control.TableView;
import model.entityDB.PoliticheEntity;
import view.admin.ModifyPoliticsView;
import view.material.LayerPane;
import view.material.MaterialPopup;
import view.popup.PoliticsPopup;
import view.popup.PopupView;


public class ModifyPoliticsController {

    public static void handle(TableView<PoliticheEntity> list, LayerPane layerPane) {

        PoliticheEntity entity = list.getSelectionModel().getSelectedItem();
        PopupView popupView;

        if (entity == null) return;

        popupView = new PoliticsPopup(entity);
        new MaterialPopup(layerPane, popupView).show();
    }
}
