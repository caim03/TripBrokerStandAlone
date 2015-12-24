package view.desig;

import com.jfoenix.controls.JFXButton;
import controller.ButtonInvoker;
import controller.command.Command;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import view.Collector;
import view.PacketFormView;
import view.material.MaterialButton;
import view.material.NavigationDrawer;

public class PacketAssembleView extends GridPane implements Collector {

    private PacketFormView form;

    public PacketAssembleView(PacketFormView form) {

        this.form = form;
        this.form.addListener();

        OffersTabPane lists = new OffersTabPane(form.getCommand());

        /*JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-radius: 32px; -fx-pref-width: 64px; -fx-pref-height: 64px");
        button.setOnMouseClicked(new ButtonInvoker(new Command() { @Override public void execute() { form.clear(); } }));*/

        Button button = new MaterialButton();

        setStyle("-fx-fill-width: true");

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
            AnchorPane.setLeftAnchor(button, newValue.doubleValue() / 2 - 32);
        });

        lists.setMaxWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(base);
    }

    public PacketAssembleView() {

        this(new PacketFormView());
    }

    public void harvest() {

        form.harvest();
    }
}
