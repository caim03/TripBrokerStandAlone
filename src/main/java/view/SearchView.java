package view;

import controller.strategy.BFSearchStrategy;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.entityDB.ViaggioEntity;
import view.material.DBListView;
import view.material.FloatingActionButton;
import view.material.MaterialTextField;
import view.material.ProgressCircle;

import java.util.List;

public class SearchView extends VBox {

    public SearchView() {

        TextField from = new MaterialTextField(), to = new MaterialTextField();
        Button button = new FloatingActionButton();
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            if (!"".equals(from.getText()) && !"".equals(to.getText())) {

                ProgressCircle progressCircle = new ProgressCircle();
                getChildren().add(progressCircle);

                new Thread(() -> {
                    List<ViaggioEntity> entities =
                            new BFSearchStrategy().search(new BFSearchStrategy.Arrival[] {
                            new BFSearchStrategy.Arrival(from.getText(), null),
                            new BFSearchStrategy.Arrival(to.getText(), null) } );
                    Platform.runLater(() -> {
                        getChildren().remove(progressCircle);
                        if (entities != null) {
                            getChildren().add(new DBListView(FXCollections.observableList(entities)));
                        }
                    });
                }).start();
            }
        });
        getChildren().addAll(from, to, button);
    }
}
