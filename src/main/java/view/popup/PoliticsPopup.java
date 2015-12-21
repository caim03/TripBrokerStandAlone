package view.popup;

import controller.ModifyController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.PoliticheEntity;


public class PoliticsPopup extends PopupView {
    private PoliticheEntity politicheEntity;
    private TextField nameTxt, minTxt, maxTxt;
    private Button modButton;

    public PoliticsPopup(PoliticheEntity politicheEntity) {

        this.politicheEntity = politicheEntity;
        this.title = "Politiche";
    }

    @Override
    protected Parent generatePopup() {

        nameTxt = new TextField();
        minTxt = new TextField();
        maxTxt = new TextField();

        modButton = new Button("Modifica");

        Label nameLbl = new Label("Nome:"),
                minLbl = new Label("Percentuale minima:"),
                maxLbl = new Label("Percentuale massima:");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(minLbl, 0, 1);
        pane.add(maxLbl, 0, 2);
        pane.add(modButton, 0, 3);

        pane.add(nameTxt, 1, 0);
        pane.add(minTxt, 1, 1);
        pane.add(maxTxt, 1, 2);

        VBox dialogVbox = new VBox(40, pane);

        return dialogVbox;
    }

    @Override
    public void show(){
        super.show();
        modButton.setOnMouseClicked(new ModifyController(politicheEntity, nameTxt, minTxt, maxTxt));
    }
}
