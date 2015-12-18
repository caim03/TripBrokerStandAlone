package view.desig;

import com.jfoenix.controls.JFXButton;
import controller.ButtonInvoker;
import controller.command.Command;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import view.PacketFormView;
import view.material.NavigationDrawer;

public class PacketAssembleView extends AnchorPane {

    private PacketFormView form;

    public PacketAssembleView(PacketFormView form) {

        this.form = form;
        this.form.addListener();

        OffersTabPane lists = new OffersTabPane(form.getCommand());

        JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-radius: 32px; -fx-pref-width: 64px; -fx-pref-height: 64px");
        button.setOnMouseClicked(new ButtonInvoker(new Command() { @Override public void execute() { form.clear(); } }));

        setStyle("-fx-fill-width: true");

        double w = Screen.getPrimary().getVisualBounds().getWidth() * (1.0 - NavigationDrawer.WIDTH);
        setLeftAnchor(lists, 0.0);
        setRightAnchor(lists, w / 2);
        setLeftAnchor(form, w / 2);
        setRightAnchor(form, 0.0);
        setLeftAnchor(button, w / 2 - 32);
        setBottomAnchor(button, 25.0);

        HBox hbox = new HBox(lists, form);
        hbox.setFillHeight(true);
        hbox.setMaxWidth(Double.MAX_VALUE);

        lists.setPrefWidth(w / 2);
        form.setPrefWidth(w / 2);
        lists.setMaxWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(hbox, button);
    }

    public PacketAssembleView() {

        this(new PacketFormView());
    }

    public void harvest() {

        form.harvest();
    }
}
