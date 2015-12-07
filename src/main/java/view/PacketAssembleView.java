package view;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.entityDB.ProdottoEntity;

public class PacketAssembleView extends JFXTabPane {

    private static String[] tabs = {"Viaggio", "Pernottamento", "Evento"};
    private ListView<ProdottoEntity> list;

    public PacketAssembleView() {

        for (int i = 0; i < 3; ++i) {

            Tab tab = new Tab(tabs[i]);
            ProdottoListView list = new ProdottoListView(tabs[i]);
            list.setCellFactory(param -> new PacketCell());

            tab.setContent(list);
            this.getTabs().add(tab);
        }

        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue.equals(newValue)) return;

            ProdottoListView view = (ProdottoListView) newValue.getContent();
            if (view.getItems().isEmpty()) view.fill();
        });
    }

}
