package view.material;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public interface PopupAttachable {

    StackPane layer = new StackPane();

    void attach(Node e);
    void detach(Node e);
}
