package view;


import controller.ApproveController;
import controller.DeleteController;
import controller.RejectController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.CreaPacchettoEntity;

public class ApprovalPopup extends PopupView {
    private PopupView popupView;
    private CreaPacchettoEntity pacchetto;

    public ApprovalPopup(PopupView popupView, CreaPacchettoEntity pacchetto){
        this.popupView = popupView;
        this.pacchetto = pacchetto;
        this.title = "Approva Pacchetto";
    }

    @Override
    protected Parent generatePopup() {

        return new VBox(popupView.generatePopup(), generateButtons());
    }

    private Parent generateButtons() {

        GridPane pane = new GridPane();
        Button approveButton = new Button("Approva");
        Button modifyButton = new Button("Respingi");
        Button deleteButton = new Button("Cancella");

        pane.add(approveButton, 0, 0);
        pane.add(modifyButton, 1, 0);
        pane.add(deleteButton, 2, 0);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);

        approveButton.setOnMouseClicked(new ApproveController(this.pacchetto));
        modifyButton.setOnMouseClicked(new RejectController(this.pacchetto));
        deleteButton.setOnMouseClicked(new DeleteController(this.pacchetto));

        return pane;
    }

}
