package view.desig;

import com.jfoenix.controls.JFXTabPane;
import controller.Constants;
import controller.command.Command;
import controller.command.TransferRecordCommand;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import model.entityDB.AbstractEntity;
import view.material.DBListView;
import view.material.JFXTabPaneSkin;

public class OffersTabPane extends JFXTabPane {

    private static String[] tabs = {Constants.travel, Constants.stay, Constants.event};

    public OffersTabPane(Command command) {

        for (int i = 0; i < 3; ++i) {

            ListView list = new DBListView("from ProdottoEntity where tipo like '" + tabs[i] + "'");

            list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                TransferRecordCommand.loadEntity((AbstractEntity) newValue);
                command.execute(); });

            if (i == 0) list.refresh();
            Tab tab = new Tab(tabs[i]);
            tab.setContent(list);
            tab.setStyle("-fx-background-color: #303F9F");
            this.getTabs().add(tab);
        }

        tabMinWidthProperty().bind(widthProperty().divide(3));
        tabMaxWidthProperty().bind(widthProperty().divide(3));

        getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue.equals(newValue)) return;

            ListView list = (ListView) getTabs().get(newValue.intValue()).getContent();
            if (list.getItems().isEmpty()) {
                list.refresh();
            }
        });

        setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, #303F9F");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new JFXTabPaneSkin(this);
    }
}
