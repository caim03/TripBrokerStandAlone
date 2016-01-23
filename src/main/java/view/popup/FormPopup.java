package view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import view.desig.PacketAssembleView;
import view.material.FlatButton;
import view.material.MaterialPopup;

public class FormPopup extends PopupView {

    private PacketAssembleView view;
    private Button exit;

    public FormPopup(PacketAssembleView view) {
        this.view = view;
    }

    @Override
    protected Parent generatePopup() {

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_RIGHT);
        exit = new FlatButton("x");
        exit.setPadding(new Insets(8));
        stackPane.getChildren().addAll(view, exit);
        return stackPane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);
        exit.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> this.parent.hide());
    }
}
