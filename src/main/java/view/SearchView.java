package view;

import controller.command.DeamonThread;
import controller.strategy.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entityDB.ViaggioEntity;
import view.material.DBListView;
import view.material.FloatingActionButton;
import view.material.MaterialTextField;
import view.material.ProgressCircle;

import java.util.List;

public class SearchView extends VBox {

    private final TextField from;
    private final TextField to;
    private final VBox pane;

    public SearchView() {

        from = new MaterialTextField();
        to = new MaterialTextField();
        Button stops = new FloatingActionButton(),
               faster = new FloatingActionButton(),
               cheaper = new FloatingActionButton();

        stops.addEventFilter(MouseEvent.MOUSE_CLICKED, getHandler(0));
        faster.addEventFilter(MouseEvent.MOUSE_CLICKED, getHandler(1));
        cheaper.addEventFilter(MouseEvent.MOUSE_CLICKED, getHandler(2));
        HBox buttons = new HBox(stops, faster, cheaper);

        pane = new VBox();

        getChildren().addAll(from, to, buttons, pane);
    }

    private EventHandler<MouseEvent> getHandler(int n) {

        return event -> {
            if (!"".equals(from.getText()) && !"".equals(to.getText())) {

                System.out.println("CALLING");
                pane.getChildren().clear();
                ProgressCircle progressCircle = new ProgressCircle();
                pane.getChildren().add(progressCircle);

                new DeamonThread(() -> {
                    List<SearchStrategy.Node> stations = getStrategy(n).search(new BFSearchStrategy.Arrival[] {
                            new BFSearchStrategy.Arrival(from.getText(), null),
                            new BFSearchStrategy.Arrival(to.getText(), null) } );
                    Platform.runLater(() -> {
                        pane.getChildren().remove(progressCircle);
                        if (stations != null) {
                            for (SearchStrategy.Node node : stations) {
                                pane.getChildren().addAll(new Text("VALORE: " + node.weightToString()),
                                        new DBListView(FXCollections.observableList(node.climbUp())));
                            }
                        }
                    });
                }).start();
            }
        };
    }

    private SearchStrategy getStrategy(int n) {

        switch (n) {
            case 0: return new FewerStopsSearchStrategy();
            case 1: return new FasterSearchStrategy();
            case 2: default: return new CheaperSearchStrategy();
        }
    }
}
