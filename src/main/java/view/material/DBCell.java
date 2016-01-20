package view.material;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.entityDB.*;
import view.material.cellcreator.BookingCellCreator;
import view.material.cellcreator.EventCellCreator;
import view.material.cellcreator.StayCellCreator;
import view.material.cellcreator.TravelCellCreator;

public class DBCell<T extends AbstractEntity> extends MaterialCell<T> {

    /*
     * Custom ListCell, used in combination with CellCreators and Entities from Hibernate DB to
     * properly display record information. It extends MaterialCell in order to display ripple animations
     * on mouse click.
     * Can currently represent: OffertaEntities and subclasses, ViaggioGruppoEntities, PrenotazioneEntities.
     * An InvalidEntity is used to display an IndeterminateProgressCircle during data loading processes.
     */

    @Override
    protected void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);

        setFocusTraversable(true); //Enable ripple effect (dismissed later if required)

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        Region node = getNode(item); //Region superclass grants access to width and height properties
        //node is then properly initialized

        setGraphic(node); //Region is ultimately attached to DBCell via overloaded setGraphic method

        node.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.doubleValue() > getListView().getPrefWidth()) {
                getListView().prefWidthProperty().setValue(newValue.doubleValue() + 25);
            }
        });
    }

    private Region getNode(T item) {

        Region region; //Region superclass grants access to width and height properties
        //node is then properly initialized
        if (item == null || !item.isValid()) region = buildProgress(); //ProgressCircle
        else if (item instanceof OffertaEntity) {

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

    private Region buildProgress() {
        //Progress placeholder for loading purposes

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(ProgressCircle.circleElevated());
        pane.prefWidthProperty().bind(getListView().widthProperty());
        pane.prefHeightProperty().bind(getListView().heightProperty());

        return pane;
    }
}
