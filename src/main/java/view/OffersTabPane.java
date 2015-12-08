package view;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

public class OffersTabPane extends JFXTabPane {

    private static String[] tabs = {"Viaggio", "Pernottamento", "Evento"};

    public OffersTabPane() {

        for (int i = 0; i < 3; ++i) {

            ListView list = new DBListView("from ProdottoEntity where tipo like '" + tabs[i] + "'");
            if (i == 0) list.refresh();
            Tab tab = new Tab(tabs[i]);
            tab.setContent(list);
            tab.setStyle("-fx-background-color: #303F9F");
            this.getTabs().add(tab);
        }

        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                tabMinWidthProperty().setValue(newValue.intValue() / 3.3);
            }
        });

        getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue.equals(newValue)) return;

            ListView list = (ListView) getTabs().get(newValue.intValue()).getContent();
            if (list.getItems().isEmpty()) {
                list.refresh();
            }
        });

        setStyle("-fx-background-color: #303F9F");
    }

}
