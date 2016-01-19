package view.popup;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.dao.DAO;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;
import view.material.MaterialPopup;


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

        modButton.setOnMouseClicked(parent.getListener(new ModifyController(), true));

        return new VBox(40, pane);
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        modButton.setOnMouseClicked(parent.getListener(new ModifyController(), true));
    }

    class ModifyController implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            String namePolitic, minPerc, maxPerc;

            namePolitic = nameTxt.getText();
            minPerc = minTxt.getText();
            maxPerc = maxTxt.getText();

            if ("".equals(namePolitic)){
                politicheEntity.setNome(namePolitic);
            }

            if ("".equals(minPerc)){
                politicheEntity.setPercentualeMin(Double.parseDouble(minPerc));
            }

            if ("".equals(maxPerc)){
                politicheEntity.setPercentualeMax(Double.parseDouble(maxPerc));
            }

            DAO dao = PoliticheDaoHibernate.instance();
            DBManager.initHibernate();
            dao.update(politicheEntity);
            DBManager.shutdown();

            Notifications.create().title("Modificata").text("La politica è stata modificata con successo").show();
        }
    }
}
