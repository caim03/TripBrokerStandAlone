package view.popup;


import controller.admin.ModifyEmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.DipendentiEntity;
import view.material.*;

public class EmployeePopup extends PopupView{

    private static final ObservableList<String> ROLES = FXCollections.observableArrayList("Agente", "Amministratore", "Designer", "Scout");

    private TableView list;
    private DipendentiEntity entity;
    private int index;
    private Button modButton;
    private TextField nameTxt, surnameTxt, passTxt, mailTxt;
    private MaterialSpinner roleSpinner;
    private GridPane pane;

    public EmployeePopup(TableView list, DipendentiEntity entity, int index) {
        this.entity = entity;
        this.index = index;
        this.list = list;
    }

    @Override
    protected Parent generatePopup() {

        Label nameLbl = new Label("Nome:"),
                surnameLbl = new Label("Cognome:"),
                roleLbl = new Label("Ruolo:"),
                passLbl = new Label("Password:"),
                mailLbl = new Label("Mail");

        nameTxt = new MaterialTextField();
        surnameTxt = new MaterialTextField();
        passTxt = new MaterialTextField();
        mailTxt = new MaterialTextField();

        nameTxt.setText(entity.getNome());
        surnameTxt.setText(entity.getCognome());
        passTxt.setText(entity.getPasswordLogin());
        mailTxt.setText(entity.getMail());

        modButton = new FlatButton("modify");

        nameTxt.setPromptText("Nuovo Nome");
        surnameTxt.setPromptText("Nuovo Cognome");
        passTxt.setPromptText("Nuova Password");
        mailTxt.setPromptText("Nuova Mail");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(surnameLbl, 0, 1);
        pane.add(roleLbl, 0, 2);
        pane.add(passLbl, 0, 3);
        pane.add(mailLbl, 0, 4);

        pane.add(nameTxt, 1, 0);
        pane.add(surnameTxt, 1, 1);
        pane.add(passTxt, 1, 3);
        pane.add(mailTxt, 1, 4);

        pane.add(modButton, 0, 5);

        return new VBox(40, pane);
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        this.parent.parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                roleSpinner = new MaterialSpinner((LayerPane) newValue, ROLES);
                roleSpinner.setValue(entity.getRuolo());
                pane.add(roleSpinner, 1, 2);

                modButton.setOnMouseClicked(this.parent.getListener(
                        new ModifyEmployeeController(nameTxt, surnameTxt, roleSpinner, passTxt,
                                mailTxt, list, entity, index),
                        true));
            }
        });
    }
}
