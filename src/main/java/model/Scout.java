package model;

import javafx.stage.Stage;

public class Scout extends Ruolo {
    @Override
    public Stage generateView() {
        return null;
    }

    @Override
    public String getRole() {
        return "Scout";
    }
}
