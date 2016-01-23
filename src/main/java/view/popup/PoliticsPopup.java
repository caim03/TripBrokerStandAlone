package view.popup;

import controller.Constants;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.dao.DAO;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;
import view.material.FlatButton;
import view.material.MaterialPopup;
import view.material.NumericField;


public class PoliticsPopup extends PopupView {

    private PoliticheEntity entity;
    private NumericField field;
    private Button modButton;

    public PoliticsPopup(PoliticheEntity entity) {
        this.entity = entity;
        this.title = "Politiche";
    }

    @Override
    protected Parent generatePopup() {

        Label politic = new Label(entity.getNome());
        Label description = new Label(entity.getDescrizione());

        field = new NumericField(false);
        field.setText(entity.toString());
        field.setPromptText("Inserisci il nuovo valore");

        modButton = new FlatButton("Modifica");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(politic, 0, 0);
        pane.add(description, 0, 1);
        pane.add(field, 0, 2);
        if (entity.getId() != Constants.minGroup) pane.add(new Label("%"), 1, 2);
        pane.add(modButton, 1, 3);

        return new VBox(40, pane);
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        modButton.setOnMouseClicked(parent.getListener(new ModifyController(), true));
    }

    class ModifyController implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            DAO dao = PoliticheDaoHibernate.instance();
            double newValue = field.getNumber();
            newValue = polishValue(newValue);
            String msg;
            if ((msg = evaluate(newValue)) != null) {
                Notifications.create().text(msg).showWarning();
                return;
            }

            entity.setValore(newValue);

            DBManager.initHibernate();
            dao.update(entity);
            DBManager.shutdown();

            Notifications.create().title("Modificata").text("La politica è stata modificata con successo").show();
        }

        private String evaluate(double newValue) {

            DAO dao = PoliticheDaoHibernate.instance();

            switch (entity.getId()) {
                case Constants.minOverprice:
                    double maxValue = ((PoliticheEntity) dao.getById(Constants.maxOverprice)).getValore();
                    double discount = ((PoliticheEntity) dao.getById(Constants.discount)).getValore();
                    if (newValue >= maxValue) return "Il nuovo valore eccede quello del sovrapprezzo massimo";
                    else if (newValue * discount < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                    break;
                case Constants.maxOverprice:
                    double minValue = ((PoliticheEntity) dao.getById(Constants.minOverprice)).getValore();
                    if (newValue <= minValue) return "Il sovrapprezzo massimo è inferiore a quello minimo";
                    break;
                case Constants.discount:
                    minValue = ((PoliticheEntity) dao.getById(Constants.minOverprice)).getValore();
                    if (minValue * newValue < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                    break;
                case Constants.minGroup: default:
                    if (newValue < 2) return "Un viaggio di gruppo dev'essere composto da almeno 2 persone";
            }

            return null;
        }

        private double polishValue(double value) {

            switch (entity.getId()) {
                case Constants.discount:
                    return 1 - value / 100.0;
                case Constants.minGroup:
                    return value;
                default:
                    return 1 + value / 100.0;
            }
        }
    }
}
