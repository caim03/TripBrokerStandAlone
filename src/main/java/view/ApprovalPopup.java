package view;


import controller.ApproveController;
import controller.DeleteController;
import controller.RejectController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;

public class ApprovalPopup extends PopupView {

    private PopupView popupView;
    private CreaPacchettoEntity pacchetto;
    private TableView<ProdottoEntity> list;

    public ApprovalPopup(PopupView popupView, CreaPacchettoEntity pacchetto, TableView<ProdottoEntity> list){
        this.popupView = popupView;
        this.pacchetto = pacchetto;
        this.title = "Approva Pacchetto";
        this.list = list;
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

        approveButton.setOnMouseClicked(new ApproveController(this.pacchetto, list));
        modifyButton.setOnMouseClicked(new RejectController(this.pacchetto, list));
        deleteButton.setOnMouseClicked(new DeleteController(this.pacchetto, list));

        return pane;
    }
}
