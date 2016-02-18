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

    private static String
            classes[] = new String[] {
            "controller.CatalogController",
            "controller.admin.ApproveController",
            "controller.admin.ModifyPoliticsController",
            "controller.admin.ModifyEmployeeController",
            "controller.admin.AddNewEmployeeController",
            "controller.admin.CompanyStatusController"
    },

    methods[] = new String[] {
            "getView",
            "getView",
            "getView",
            "getView",
            "getView",
            "getView" };

    @Override
    protected void addCommands() throws ClassNotFoundException, NoSuchMethodException {
        container.addCommands(
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[0]).getMethod(methods[0]))), // visualize catalog command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[1]).getMethod(methods[1]))), // approve packets command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[2]).getMethod(methods[2]))), // modify politics command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[3]).getMethod(methods[3]))), // menage roles command
                new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName(classes[4]).getMethod(methods[4]))), // add employee command
                new RefreshMacroCommand(container, new ShowCommand(container, Class.forName(classes[5]).getMethod(methods[5]))), // add employee command
                new LogoutCommand(stage)); // logout command
    }

    @Override
    public String getRole() { return Constants.admin; }


}
