package view;

import com.jfoenix.controls.JFXButton;
import controller.command.Command;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import view.material.NavigationDrawer;

public class PacketAssembleView extends AnchorPane {

    public PacketAssembleView() {

        ListView builder = new ListView();
        builder.setCellFactory(param -> new DBCell());

        OffersTabPane lists = new OffersTabPane(builder);

        JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-radius: 32px; -fx-pref-width: 64px; -fx-pref-height: 64px");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                builder.getItems().remove(0, builder.getItems().size());
            }
        });

        setStyle("-fx-fill-width: true");

        double w = Screen.getPrimary().getVisualBounds().getWidth() * (1.0 - NavigationDrawer.WIDTH);
        setLeftAnchor(lists, 0.0);
        setRightAnchor(lists, w / 2);
        setLeftAnchor(builder, w / 2);
        setRightAnchor(builder, 0.0);
        setLeftAnchor(button, w / 2 - 32);
        setBottomAnchor(button, 25.0);

        HBox hbox = new HBox(lists, builder);
        hbox.setFillHeight(true);
        hbox.setMaxWidth(Double.MAX_VALUE);

        lists.setPrefWidth(w / 2);
        builder.setPrefWidth(w / 2);
        lists.setMaxWidth(Double.MAX_VALUE);
        builder.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(hbox, button);
    }
}
