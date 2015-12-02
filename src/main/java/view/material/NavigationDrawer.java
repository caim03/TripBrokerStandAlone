package view.material;

import controller.CatalogHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class NavigationDrawer extends VBox {

    static double ratio = 0.67;

    ListView<String> options;

    public NavigationDrawer() {

        double width = Screen.getPrimary().getVisualBounds().getWidth() * 1 / 4;
        setPrefWidth(width);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: white; -fx-border-color: null");

        Canvas canvas = new Canvas(width, width * ratio);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.ORANGE);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        getChildren().add(canvas);
    }

    public void setOptions(ObservableList<String> opts, ConsolePane container) {

        int size;
        if ((size = getChildren().size()) > 1) getChildren().remove(1, size);

        options = new ListView<>(opts);
        options.setCellFactory(param -> new DrawerCell());
        options.minHeight(Double.MAX_VALUE);
        options.setPadding(new Insets(16, 16, 16, 16));

        options.setOnMouseClicked(new CatalogHandler(options, container));

        getChildren().add(options);
    }
}
