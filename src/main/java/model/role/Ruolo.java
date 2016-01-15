package model.role;

import javafx.stage.Stage;

/*** The all classes that extend Ruolo class, must override two method:
 *      - generateView()
 *      - getRole()
 *   This class represents the dependent's role in general way,
 *   and with polymorphism the subclasses use generateView() for generating the right view,
 *   associated to current role;
 *   instead, with getRole() the subclasses obtain the right role that represent them ***/

public abstract class Ruolo {

    public abstract Stage generateView();
    /*** @result Stage; return the view of the right role ***/

    public abstract String getRole();
    /*** @result String; return the role as a string ***/
}
