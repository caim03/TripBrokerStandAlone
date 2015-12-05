package controller;

import controller.command.Command;
import controller.command.CreateOfferCommand;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.*;
import view.material.ConsolePane;
import view.material.FlatButton;

public class CatalogHandler implements EventHandler<MouseEvent> {

    ListView<String> list;
    ConsolePane pane;
    Label defaultMsg;

    static Stage stage;

    public CatalogHandler() {
        this(null, null);
    }

    public CatalogHandler(ListView<String> list) {
        this(list, null);
    }

    public CatalogHandler(ListView<String> list, ConsolePane pane) {

        setList(list);
        setPane(pane);

        generateLabel();
    }

    public void setList(ListView list) {

        this.list = list;
    }

    public void setPane(ConsolePane pane) {
        this.pane = pane;
    }

    public static void setStage(Stage stage) {

        CatalogHandler.stage = stage;
    }

    public void generateLabel() {

        defaultMsg = new Label("Choose an option");
        defaultMsg.setPadding(new Insets(25,25,25,25));
        defaultMsg.setTextFill(Color.CRIMSON);
        defaultMsg.setStyle("-fx-font-size: 128px");
        defaultMsg.setAlignment(Pos.CENTER);
    }

    @Override
    public void handle(MouseEvent event) {

        if (stage== null || list == null || pane == null) return;

        pane.hideToolbarButtons();
        String selected = list.getSelectionModel().getSelectedItem();

        if ("Visualizza Catalogo".equals(selected)) {

        }

        else if ("Inserisci offerta".equals(selected)) {

            OfferInsertionView view = OfferInsertionView.getInstance();
            Button done = new FlatButton();
            Command command = new CreateOfferCommand(view);
            done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));

            pane.addToolbarButton(done);
            pane.setCenter(OfferInsertionView.getInstance());
        }

        else if ("Logout".equals(selected)) {

            stage.close();

            try {
                new TripBrokerLogin().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {

            pane.setCenter(defaultMsg);
        }
    }
}
