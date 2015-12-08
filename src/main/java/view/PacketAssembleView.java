package view;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import org.controlsfx.control.Notifications;
import view.material.NavigationDrawer;

public class PacketAssembleView extends AnchorPane {

    public PacketAssembleView() {

        OffersTabPane lists = new OffersTabPane();
        Pane builder = new Pane();
        builder.setStyle("-fx-background-color: #3F51B5");

        JFXButton button = new JFXButton("+");
        button.setButtonType(JFXButton.ButtonType.FLAT);
        button.setAlignment(Pos.BOTTOM_CENTER);

        double w = Screen.getPrimary().getVisualBounds().getWidth() * (1.0 - NavigationDrawer.WIDTH);
        setLeftAnchor(lists, 0.0);
        setRightAnchor(lists, w / 2);
        setLeftAnchor(builder, w / 2);
        setRightAnchor(builder, w);
        setRightAnchor(button, w / 2);

        HBox hbox = new HBox(lists, builder);

        lists.setPrefWidth(w / 2);
        builder.setPrefWidth(w / 2);



        getChildren().addAll(hbox, button);
    }
}
