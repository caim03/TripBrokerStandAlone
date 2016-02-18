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

    private static String
            classes[] = new String[] {
            "controller.CatalogController",
            "controller.agent.GroupTripAssembleController",
            "controller.agent.BookingController",
            "controller.agent.ManageBookingController",
            "controller.agent.SellController" },

            methods[] = new String[] {
            "getView",
            "getView",
            "getView",
            "getView",
            "getView" };

    @Override
    protected void addCommands() throws ClassNotFoundException, NoSuchMethodException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[0]).getMethod(methods[0]))), //Show Catalog command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[1]).getMethod(methods[1]))), //Show group trip assemble interface
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[2]).getMethod(methods[2]))), //Show booking interface
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[3]).getMethod(methods[3]))), //Show booking management GUI
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[4]).getMethod(methods[4]))), //Show product selling GUI
                new LogoutCommand(stage)); //Logout
    }

    @Override public String getRole() {
        return Constants.agent;
    }
}
