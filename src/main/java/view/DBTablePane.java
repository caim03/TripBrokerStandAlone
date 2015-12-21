package view;

import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.entityDB.AbstractEntity;

import java.util.List;

public abstract class DBTablePane extends GridPane {

    protected abstract void fill();
    protected abstract List<? extends AbstractEntity> query();
    protected abstract TableView generateTable();
}
