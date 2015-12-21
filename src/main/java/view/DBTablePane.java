package view;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import model.entityDB.AbstractEntity;

import java.util.List;

public abstract class DBTablePane extends GridPane {

    protected DBTablePane() {

        getChildren().add(new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS));
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);

        fill();
    }

    protected abstract void fill();
    protected abstract List<? extends AbstractEntity> query();
    protected abstract TableView generateTable();
}
