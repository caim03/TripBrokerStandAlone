package model.role;

import controller.Constants;
import controller.command.LogoutCommand;
import controller.command.RefreshMacroCommand;
import controller.command.ShowCommand;
import controller.command.ShowFormCommand;

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
