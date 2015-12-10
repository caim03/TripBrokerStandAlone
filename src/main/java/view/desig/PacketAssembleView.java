package view.desig;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import view.material.NavigationDrawer;

public class PacketAssembleView extends AnchorPane {

    public PacketAssembleView() {

        PacketFormView builder = new PacketFormView();

        OffersTabPane lists = new OffersTabPane(builder.getCommand());

        JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-radius: 32px; -fx-pref-width: 64px; -fx-pref-height: 64px");

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
