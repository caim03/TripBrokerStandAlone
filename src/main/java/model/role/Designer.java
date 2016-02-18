package model.role;

import controller.Constants;
import controller.command.LogoutCommand;
import controller.command.RefreshMacroCommand;
import controller.command.ShowCommand;

/***
 * This implementation of Ruolo represents the set of responsibilities and jobs a
 * TripBroker Designer has.
 ***/
public class Designer extends Ruolo {

    private static String
            classes[] = new String[] {
                "controller.CatalogController",
                "controller.desig.PacketAssembleController",
                "controller.desig.PacketEditController" },

            methods[] = new String[] {
                "getView",
                "getView",
                "getView" };

    @Override
    protected void addCommands() throws ClassNotFoundException, NoSuchMethodException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[0]).getMethod(methods[0]))), // visualize catalog command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[1]).getMethod(methods[1]))), // packet assemble command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[2]).getMethod(methods[2]))), // packet edit command
                new LogoutCommand(stage)); // logout command
    }

    @Override public String getRole() { return Constants.desig; }
}
