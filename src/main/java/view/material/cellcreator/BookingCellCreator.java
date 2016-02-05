package view.material.cellcreator;

import controller.agent.CancelBookingController;
import controller.agent.ConfirmBookingController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.entityDB.PrenotazioneEntity;
import org.controlsfx.control.Notifications;
import view.material.EmptyCell;
import view.material.FlatButton;

public class BookingCellCreator extends AbstractCellCreator<PrenotazioneEntity> {

    private static BookingCellCreator me;
    public static BookingCellCreator instance() {

        if (me == null) me = new BookingCellCreator();
        return me;
    }
    protected BookingCellCreator() { super(); }

    public EmptyCell createForListView(ListView listView, PrenotazioneEntity entity) {

        EmptyCell cell = createCell(entity);

        //Booking management Buttons
        Button confirm = new FlatButton("Conferma"),
                cancel = new FlatButton("Elimina");

        confirm.setOnMouseClicked(event -> {
            ConfirmBookingController.handle(entity);
            Notifications.create().text("Prenotazione confermata").show();
            listView.getItems().remove(entity);
        });
        cancel.setOnMouseClicked(event -> {
            CancelBookingController.handle(entity);
            Notifications.create().text("Prenotazione cancellata").show();
            listView.getItems().remove(entity);
        });

        cell.getChildren().addAll(confirm, cancel);

        cell.widthProperty().addListener((observable, oldValue, newValue) -> {
            //Bind ListView width to cell width

            double w = listView.getWidth();

            if (oldValue != null) {
                if (newValue != null && oldValue.doubleValue() < newValue.doubleValue() && newValue.doubleValue() > w) {
                    listView.minWidthProperty().setValue(newValue.doubleValue() + 24);
                    listView.prefWidthProperty().setValue(newValue.doubleValue() + 24);
                }
            }

            else if (newValue != null && w < newValue.doubleValue()) {
                listView.minWidthProperty().setValue(newValue.doubleValue() + 24);
                listView.prefWidthProperty().setValue(newValue.doubleValue() + 24);
            }
        });

        return cell;
    }

    @Override
    protected EmptyCell createCell(PrenotazioneEntity entity) {

        EmptyCell cell = new EmptyCell(entity.getNome() + " " + entity.getCognome());

        //Reservation quantity
        cell.addSubHeaders(
                "Numero di telefono: " + entity.getTelefono(),
                "Posti prenotati: " + entity.getQuantità());

        return cell;
    }
}
