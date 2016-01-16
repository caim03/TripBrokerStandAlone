package model.role;

import javafx.stage.Stage;

/***
 * Abstract class that represents an Employee job as part of TripBroker staff.
 * All extending classes must override two methods:
 *      - generateView()
 *      - getRole()
 * According to their implementation, these methods build different GUI interfaces,
 * aimed at offering a particular set of features mirroring the employee responsibilities.
 * This class both realize dynamic association between Employee and its role and Factory Method
 * pattern implementation for GUI construction.
 ***/

public abstract class Ruolo {

    public abstract Stage generateView();
    /*** @result Stage; return the view of the right role ***/

    public abstract String getRole();
    /*** @result String; return the role as a string ***/
}
