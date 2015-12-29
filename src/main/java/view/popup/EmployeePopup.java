package view.popup;


import controller.ModifyEmployeeController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.DipendentiEntity;

public class EmployeePopup extends PopupView{

    private TableView<DipendentiEntity> list;

    public EmployeePopup(TableView<DipendentiEntity> list){
        this.list = list;
        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {

        Label nameLbl = new Label("Nome:"),
                surnameLbl = new Label("Cognome:"),
                roleLbl = new Label("Ruolo:"),
                passLbl = new Label("Password:"),
                mailLbl = new Label("Mail");

        TextField nameTxt = new TextField(),
                surnameTxt = new TextField(),
                roleTxt = new TextField(),
                passTxt = new PasswordField(),
                mailTxt = new TextField();

        Button modButton = new Button("modify");
        modButton.setOnMouseClicked(new ModifyEmployeeController(nameTxt.getText(), surnameTxt.getText(),
                roleTxt.getText(), passTxt.getText(), mailTxt.getText(), list));

        nameTxt.setPromptText("Nuovo Nome");
        surnameTxt.setPromptText("Nuovo Cognome");
        roleTxt.setPromptText("Nuovo Ruolo");
        passTxt.setPromptText("Nuova Password");
        mailTxt.setPromptText("Nuova Mail");



        GridPane pane = new GridPane();
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
        pane.add(roleTxt, 1, 2);
        pane.add(passTxt, 1, 3);
        pane.add(mailTxt, 1, 4);

        pane.add(modButton, 0, 5);

        VBox vBox = new VBox(40, pane);
        return vBox;
    }
}
