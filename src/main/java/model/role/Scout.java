package model.role;

import controller.Constants;
import controller.command.LogoutCommand;
import controller.command.RefreshMacroCommand;
import controller.command.ShowCommand;
import controller.command.ShowFormCommand;

import java.lang.reflect.Method;

/***
 * This implementation of Ruolo represents the set of responsibilities and jobs a
 * TripBroker Scout has.
 ***/
public class Scout extends Ruolo {

    private static String
            classes[] = new String[] {
            "controller.CatalogController",
            "controller.scout.InsertOfferController" },

            methods[] = new String[] {
            "getView",
            "getView" };

    @Override
    protected void addCommands() throws ClassNotFoundException ,NoSuchMethodException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[0]).getMethod(methods[0]))), // visualize catalog command
                new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName(classes[1]).getMethod(methods[1]))), // offer insertion command
                new LogoutCommand(stage)); // logout command

    }

    @Override public String getRole() {
        return Constants.scout;
    }
}