package model.role;

import controller.Constants;
import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.TripBrokerConsole;
import view.material.ConsolePane;

/***
 * This implementation of Ruolo represents the set of responsibilities and jobs a
 * TripBroker Administrator has.
 ***/
public class Amministratore extends Ruolo {

    @Override
    protected void addCommands() throws ClassNotFoundException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.CatalogView"))), // visualize catalog command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.admin.PacketApproveView"))), // approve packets command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.admin.ModifyPoliticsView"))), // modify politics command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.admin.ManageRolesView"))), // menage roles command
                new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName("view.admin.AddNewEmployeeView"))), // add employee command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.admin.CompanyStatusView"))), // add employee command
                new LogoutCommand(stage)); // logout command
    }

    @Override
    public String getRole() { return Constants.admin; }


}
