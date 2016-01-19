package view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class PopupDecorator extends PopupView {
    private PopupView base;
    private GridPane decoration;

    public PopupDecorator(PopupView base) { this.base = base; }

    @Override public Parent generatePopup() {

        decoration = new GridPane();
        decoration.setAlignment(Pos.CENTER);
        decoration.setPadding(new Insets(4));
        decoration.getChildren().add(decorate());

        VBox box = new VBox(base.generatePopup(), decoration);
        box.setStyle("-fx-background-color: white");
        return box;
    }
    protected abstract Node decorate();

    protected void changeDecoration(Node newDecoration) {
        decoration.getChildren().clear();
        decoration.getChildren().add(newDecoration);
    }
}
