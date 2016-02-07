package model.role;

import controller.Constants;
import controller.command.LogoutCommand;
import controller.command.RefreshMacroCommand;
import controller.command.ShowCommand;

/***
 * This implementation of Ruolo represents the set of responsibilities and jobs a
 * TripBroker Agent has.
 ***/

public class Agente extends Ruolo {

    @Override
    protected void addCommands() throws ClassNotFoundException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.CatalogView"))), //Show Catalog command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.agent.GroupTripAssembleView"))), //Show group trip assemble interface
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.agent.GroupTripBookingView"))), //Show booking interface
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.agent.ManageBookingView"))), //Show booking management GUI
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName("view.agent.SellProductView"))), //Show product selling GUI
                new LogoutCommand(stage)); //Logout
    }

    @Override public String getRole() {
        return Constants.agent;
    }
}
