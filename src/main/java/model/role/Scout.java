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

    @Override
    protected void addCommands() throws ClassNotFoundException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.CatalogView"))), // visualize catalog command
                new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName("view.scout.OfferInsertionView"))), // offer insertion command
                new LogoutCommand(stage)); // logout command
    }

    @Override public String getRole() {
        return Constants.scout;
    }
}