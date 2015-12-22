package view.popup;


import controller.ApproveController;
import controller.DeleteController;
import controller.RejectController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import view.material.FlatButton;
import view.material.MaterialPopup;

public class ApprovalPopup extends PopupView {

    private PopupView popupView;
    private CreaPacchettoEntity pacchetto;
    private TableView<ProdottoEntity> list;
    private Button approveButton, modifyButton, deleteButton;

    public ApprovalPopup(PopupView popupView, CreaPacchettoEntity pacchetto, TableView<ProdottoEntity> list){
        this.popupView = popupView;
        this.pacchetto = pacchetto;
        this.title = "Approva Pacchetto";
        this.list = list;

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {

        return new VBox(popupView.generatePopup(), generateButtons());
    }

    private Parent generateButtons() {

        GridPane pane = new GridPane();
        setStyle("-fx-background-color: white");
        setPadding(new Insets(0, 0, 25, 0));
        approveButton = new FlatButton("Approva");
        modifyButton = new FlatButton("Respingi");
        deleteButton = new FlatButton("Cancella");

        pane.add(approveButton, 0, 0);
        pane.add(modifyButton, 1, 0);
        pane.add(deleteButton, 2, 0);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        approveButton.setOnMouseClicked(this.parent.getListener(new ApproveController(this.pacchetto, list)));
        modifyButton.setOnMouseClicked(this.parent.getListener(new RejectController(this.pacchetto, list)));
        deleteButton.setOnMouseClicked(this.parent.getListener(new DeleteController(this.pacchetto, list)));
    }
}
