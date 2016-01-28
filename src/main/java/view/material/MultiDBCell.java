package view.material;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Route;
import model.entityDB.*;
import view.material.cellcreator.*;

public class MultiDBCell<T extends Route> extends MaterialCell<T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        setFocusTraversable(true); //Enable ripple effect (dismissed later if required)

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        Label lbl = new Label(item.getValue());
        lbl.setPadding(new Insets(16));

        VBox node = new VBox(lbl);

        for (AbstractEntity entity : item)
            node.getChildren().add(getNode(entity)); //Region superclass grants access to width and height properties
        //node is then properly initialized

        setGraphic(node); //Region is ultimately attached to DBCell via overloaded setGraphic method

        node.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.doubleValue() > getListView().getPrefWidth()) {
                getListView().prefWidthProperty().setValue(newValue.doubleValue() + 25);
            }
        });
    }

    private Region getNode(AbstractEntity item) {

        Region region; //Region superclass grants access to width and height properties
        //node is then properly initialized

        if (item instanceof ViaggioGruppoEntity)
            region = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png");

        else region = AbstractCellCreator.getCell(item);

        if (region == null) region = new Label("NOT IMPLEMENTED"); //T not supported

        return region;
    }
}
