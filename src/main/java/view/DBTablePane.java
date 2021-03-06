package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.entityDB.AbstractEntity;
import org.controlsfx.control.Notifications;
import view.material.LayerPane;
import view.material.ProgressCircle;

import java.util.List;

public abstract class DBTablePane extends LayerPane {

    protected ProgressCircle bar;

    protected DBTablePane() {

        super();

        bar = ProgressCircle.circleElevated();
        getChildren().add(bar);
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);

        fill();
    }

    protected void fill() {

        new Thread(() -> {

            List<? extends AbstractEntity> entities = query();

            Platform.runLater(() -> {

                pop();

                if (entities == null)
                    Notifications.create().title("Catalogo vuoto").text("Non esistono prodotti da mostrare nel catalogo").show();

                else {

                    ObservableList<AbstractEntity> names = FXCollections.observableArrayList();
                    for (AbstractEntity e : entities) names.add(e);

                    TableView list = generateTable();
                    list.setItems(names);

                    GridPane container = new GridPane();
                    container.getChildren().add(list);
                    GridPane.setHgrow(list, Priority.ALWAYS);
                    GridPane.setVgrow(list, Priority.ALWAYS);

                    attach(container);
                }
            });
        }).start();
    }

    protected abstract List<? extends AbstractEntity> query();
    protected abstract TableView generateTable();
}
