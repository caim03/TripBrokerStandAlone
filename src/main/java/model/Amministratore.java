package model;

import javafx.stage.Stage;

/**
 * Created by stg on 18/11/15.
 */
public class Amministratore extends Ruolo {
    @Override
    public Stage generateView() {
        return null;
    }

    @Override
    public String getRole() {
        return "Amministratore";
    }
}
