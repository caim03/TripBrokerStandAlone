package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import model.entityDB.AbstractEntity;
import view.material.MaterialPopup;
import view.material.PopupAttachable;

import java.util.List;

public abstract class DBTablePane extends GridPane implements PopupAttachable {

    protected DBTablePane() {

        getChildren().add(layer);
        setHgrow(layer, Priority.ALWAYS);
        setVgrow(layer, Priority.ALWAYS);
        layer.setAlignment(Pos.CENTER);

        attach(new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS));
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);

        fill();
    }

    @Override public void attach(Node e) {

        if (e instanceof MaterialPopup) {
            attach((MaterialPopup) e);
            return;
        }
        layer.getChildren().add(e);
    }
    public void attach(MaterialPopup e) {

        FadeTransition ft = new FadeTransition(Duration.millis(200), e);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        ft.setOnFinished(event -> layer.getChildren().add(e));
    }

    @Override public void detach(Node e) {
        if (e instanceof MaterialPopup) {
            detach((MaterialPopup) e);
            return;
        }
        layer.getChildren().remove(e);
    }
    public void detach(MaterialPopup e) {

        FadeTransition ft = new FadeTransition(Duration.millis(100), e);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();

        ft.setOnFinished(event -> layer.getChildren().remove(e));
    }
    public void detach(int p) { detach(layer.getChildren().get(p)); }

    protected abstract void fill();
    protected abstract List<? extends AbstractEntity> query();
    protected abstract TableView generateTable();
}
