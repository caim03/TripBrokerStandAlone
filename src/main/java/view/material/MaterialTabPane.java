package view.material;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class MaterialTabPane extends TabPane {

    public MaterialTabPane(String... tabs) {


        int index = tabs.length;
        double w = Screen.getPrimary().getVisualBounds().getWidth() * 0.75 / index;

        for (int i = 0; i < index; ++i) {

            Canvas box = new Canvas(w, 48);
            box.getGraphicsContext2D().setFill(Color.web("#303F9F"));
            box.getGraphicsContext2D().fill();

            Label lbl = new Label(tabs[i]);
            lbl.setTextFill(Color.WHITE);
            lbl.setAlignment(Pos.CENTER);

            Tab tab = new Tab();
            tab.setGraphic(new StackPane(box, lbl));
            tab.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                    if (oldValue ^ newValue) return;

                    else {

                        Canvas box = (Canvas) ((StackPane) tab.getGraphic()).getChildren().get(0);

                        if (newValue) box.getGraphicsContext2D().setFill(Color.WHITE);
                        else box.getGraphicsContext2D().setFill(Color.web("#303F9F"));

                        box.getGraphicsContext2D().fillRect(47, 0, w, 2);
                    }
                }
            });

            this.getTabs().add(tab);
        }
    }
}
