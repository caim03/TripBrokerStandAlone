package view.material;

import controller.strategy.BFSearchStrategy;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Route;
import model.entityDB.*;
import view.material.cellcreator.BookingCellCreator;
import view.material.cellcreator.EventCellCreator;
import view.material.cellcreator.StayCellCreator;
import view.material.cellcreator.TravelCellCreator;

import java.util.List;

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
        if (item instanceof OffertaEntity) {

            if (item instanceof ViaggioEntity)
                region = TravelCellCreator.instance().createCell((ViaggioEntity) item);
            else if (item instanceof EventoEntity)
                region = EventCellCreator.instance().createCell((EventoEntity) item);
            else
                region = StayCellCreator.instance().createCell((PernottamentoEntity) item);
        }

        else if (item instanceof ViaggioGruppoEntity) {
            region = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png");
        }

        else if (item instanceof PrenotazioneEntity) {
            setFocusTraversable(false); //disable cell ripple
            region = BookingCellCreator.instance().createForListView(getListView(), (PrenotazioneEntity) item);
        }

        else region = new Label("NOT IMPLEMENTED"); //T not supported

        return region;
    }
}
