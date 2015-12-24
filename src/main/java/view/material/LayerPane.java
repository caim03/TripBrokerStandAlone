package view.material;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LayerPane extends StackPane {

    public LayerPane() {

        reset();
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);
    }

    void reset() {

        int size = getChildren().size();
        if (size > 0) getChildren().remove(0, size);
    }

    public void pop() {

        int size = getChildren().size();
        if (size > 0) { detach(getChildren().get(size - 1)); }
    }

    public void attach(Node e) {

        if (e instanceof MaterialPopup) {
            FadeTransition ft = generateTransition(e, 0, 1);
            ft.play();
            ft.setOnFinished(event -> getChildren().add(e));
        }
        else getChildren().add(e);
    }

    private void detach(Node e) {

        if (e instanceof MaterialPopup) {
            FadeTransition ft = generateTransition(e, 1, 0);
            ft.play();
            ft.setOnFinished(event -> getChildren().remove(e));
        }
        else getChildren().remove(e);
    }

    private FadeTransition generateTransition(Node e, double from, double to) {

        FadeTransition ft = new FadeTransition(Duration.millis(100), e);
        ft.setFromValue(from);
        ft.setToValue(to);
        return ft;
    }
}
