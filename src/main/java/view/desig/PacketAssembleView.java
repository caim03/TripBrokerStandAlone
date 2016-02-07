package view.desig;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import view.Collector;
import view.PacketFormView;
import view.material.FloatingActionButton;

public class PacketAssembleView extends GridPane implements Collector {

    private PacketFormView form;

    public PacketAssembleView(PacketFormView form) {

        this.form = form;
        this.form.addListener();

        setStyle("-fx-fill-width: true");

        OffersTabPane lists = new OffersTabPane(form.getCommand());

        Button button = new FloatingActionButton(new Image("create.png"));
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> harvest());

        AnchorPane base = new AnchorPane(lists, form, button);
        setHgrow(base, Priority.ALWAYS);
        setVgrow(base, Priority.ALWAYS);
        base.setStyle("-fx-fill-width: true");

        AnchorPane.setLeftAnchor(lists, 0.0);
        AnchorPane.setRightAnchor(form, 0.0);
        AnchorPane.setTopAnchor(lists, 0.0);
        AnchorPane.setTopAnchor(form, 0.0);
        AnchorPane.setBottomAnchor(lists, 0.0);
        AnchorPane.setBottomAnchor(form, 0.0);
        AnchorPane.setBottomAnchor(button, 25.0);

        lists.prefWidthProperty().bind(base.widthProperty().divide(2));
        form.prefWidthProperty().bind(base.widthProperty().divide(2));

        base.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                double value = newValue.doubleValue() / 2;
                AnchorPane.setLeftAnchor(button, value - 32);
            }
        });

        lists.setMaxWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(base);
    }

    public PacketAssembleView() { this(new PacketFormView()); }

    public void harvest() { form.harvest(); }
}
