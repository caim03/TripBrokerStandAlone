package view.popup;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.ViaggioGruppoEntity;
import view.material.ProgressCircle;

public class GroupTripPopup extends PacketPopup {

    public GroupTripPopup(ViaggioGruppoEntity entity) { super(entity); }

    public GroupTripPopup(int id) { super(id); }

    @Override
    protected GridPane generatePane() {

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(new Label("Nome:"), 0, 0);
        pane.add(new Label("Prezzo:"), 0, 1);
        pane.add(new Label("Disponibilità:"), 0, 2);
        pane.add(new Label("Prenotazioni:"), 0, 3);
        pane.add(new Label("Creatore:"), 0, 4);


        pane.add(new Text(entity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);

        int availability = ((ViaggioGruppoEntity) entity).getMax()
                - ((ViaggioGruppoEntity) entity).getPrenotazioni()
                - ((ViaggioGruppoEntity) entity).getAcquisti();
        if (availability == 0) pane.add(new Text("non disponibile"), 1, 2);
        else pane.add(new Text("disponibile (" + availability + ")"), 1, 2);

        pane.add(new Text("" + ((ViaggioGruppoEntity) entity).getPrenotazioni()), 1, 3);

        ProgressCircle circle;
        pane.add(circle = ProgressCircle.miniCircle(), 1, 4);
        new Thread(() -> {
            String creator = getCreator(entity.getCreatore());
            Platform.runLater(() -> {
                pane.getChildren().remove(circle);
                pane.add(new Text(creator), 1, 4);
            });
        }).start();

        return pane;
    }
}
