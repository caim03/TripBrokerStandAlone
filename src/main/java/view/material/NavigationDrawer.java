package view.material;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class NavigationDrawer extends VBox {

    static double ratio = 0.67;

    ListView options;

    public NavigationDrawer(ListView listView) {

        double width = Screen.getPrimary().getVisualBounds().getWidth() * 1 / 4;
        setPrefWidth(width);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: white; -fx-border-color: null");

        Canvas canvas = new Canvas(width, width * ratio);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.ORANGE);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        options = listView;
        options.setPadding(new Insets(16, 16, 16, 16));

        getChildren().addAll(canvas, options);
    }
}
