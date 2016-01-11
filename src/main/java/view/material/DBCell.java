package view.material;

import controller.CancelBookingController;
import controller.ConfirmBookingController;
import controller.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.material.cellfactory.*;

public class DBCell<T extends AbstractEntity> extends MaterialCell<T> {

    /*
     * Custom ListCell, used in combination with CellFactory and Entities from Hibernate DB to
     * properly display record information. It extends MaterialCell in order to display ripple animations
     * on mouse click.
     * Can currentlly represent: OffertaEntities and subclasses, ViaggioGruppoEntities, PrenotazioneEntities.
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

        Region node; //Region superclass grants access to width and height properties
        //node is then properly initialized
        if (!item.isValid()) node = buildProgress(); //ProgressCircle
        else if (item instanceof OffertaEntity) {

            if (item instanceof ViaggioEntity)
                node = TravelCellFactory.instance().createCell((ViaggioEntity) item);
            else if (item instanceof EventoEntity)
                node = EventCellFactory.instance().createCell((EventoEntity) item);
            else
                node = StayCellFactory.instance().createCell((PernottamentoEntity) item);
        }

        else if (item instanceof ViaggioGruppoEntity) {
            node = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png");
        }

        else if (item instanceof PrenotazioneEntity) {
            setFocusTraversable(false); //disable cell ripple
            node = BookingCellFactory.instance().createForListView(getListView(), (PrenotazioneEntity) item);
        }

        else node = new Label("NOT IMPLEMENTED"); //T not supported

        setGraphic(node); //Region is ultimately attached to DBCell via overloaded setGraphic method
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
