package model.role;

import controller.Constants;
import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.material.ConsolePane;

/***
 * This implementation of Ruolo represents the set of responsibilities and jobs a
 * TripBroker Scout has.
 ***/

public class Scout extends Ruolo {

    /***
     * Implementation of generateView() abstract method;
     * @return Stage: the Stage object the application will show to the user
     ***/
    @Override
    public Stage generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane(getRole());
        container.setCenter(welcome);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        Stage stage = new Stage();
        stage.setScene(scene);

        // try to add the command operation to navigation drawer
        try {
            container.addCommands(
                    new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.CatalogView"))), // visualize catalog command
                    new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName("view.scout.OfferInsertionView"))), // offer insertion command
                    new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.SearchView"))), // visualize catalog command
                    new LogoutCommand(stage)); // logout command
        }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        return stage;
    }

    @Override
    public String getRole() {
        return Constants.scout;
    }
}