package view.popup;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.PacchettoDaoHibernate;
import model.dao.DAO;
import model.dao.DipendenteDaoHibernate;
import model.entityDB.DipendenteEntity;
import model.entityDB.PacchettoEntity;
import view.material.DBCell;
import view.material.ProgressCircle;

public class PacketPopup extends PopupView {

    private int id;
    private GridPane container = new GridPane();
    protected PacchettoEntity entity;
    private ListView list;

    public PacketPopup(PacchettoEntity prodottoEntity) {
        this.entity = prodottoEntity;
        container.setAlignment(Pos.CENTER);
    }

    public PacketPopup(int id) {
        this.id = id;
        container.setAlignment(Pos.CENTER);
    }

    public PacchettoEntity getEntity() { return entity; }

    @Override
    protected Parent generatePopup() {

        if (entity == null) {

            ProgressCircle circle = ProgressCircle.circleElevated();
            container.getChildren().add(circle);
            new Thread(() -> {
                PacchettoEntity entity =
                        (PacchettoEntity) PacchettoDaoHibernate.instance().getById(id);
                Platform.runLater(() -> {
                    container.getChildren().remove(circle);
                    this.entity = entity;
                    container.getChildren().add(generate());
                });
            }).start();

            return container;
        }
        else container.getChildren().add(generate());

        return container;
    }

    protected GridPane generatePane() {

        int i = entity.getStato() == 2 ? 1 : 0;

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(new Label("Nome:"), 0, 0);
        pane.add(new Label("Prezzo:"), 0, 1);
        pane.add(new Label("Stato:"), 0, 2);
        if (entity.getStato() == 2) pane.add(new Label("Motivazione:"), 0, 3);
        pane.add(new Label("Creatore:"), 0, 3 + i);

        pane.add(new Text(entity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);

        String state;
        if (entity.getStato() == 0) state = "In attesa di approvazione";
        else if (entity.getStato() == 1) state = "Approvato";
        else state = "Rifiutato";
        pane.add(new Text(state), 1, 2);
        if (entity.getStato() == 2) pane.add(new Text((entity.getMotivazione())), 1, 3);

        ProgressCircle circle;
        pane.add(circle = ProgressCircle.miniCircle(), 1, 3 + i);
        new Thread(() -> {
            String creator = getCreator(entity.getCreatore());
            Platform.runLater(() -> {
                pane.getChildren().remove(circle);
                pane.add(new Text(creator), 1, 3 + i);
            });
        }).start();

        return pane;
    }

    private Parent generate() {

        generateList();

        VBox dialogVbox = new VBox(generatePane(), list);

        list.maxWidth(300);
        list.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.doubleValue() > dialogVbox.getPrefWidth() && newValue.doubleValue() < list.getMaxWidth()) {
                dialogVbox.prefWidthProperty().setValue(newValue.doubleValue() + 32);
            }
        });

        dialogVbox.setStyle("-fx-background-color: white");

        return dialogVbox;
    }

    private void generateList() {

        list = new ListView();
        list.setMaxHeight(200);
        list.setCellFactory(callback -> new DBCell());
        list.getItems().addAll(entity.retrieveOffers());
    }

    protected String getCreator(int id) {
        /** @param: int; Employee id
         *  @result: String; name of logged in Employee **/

        DipendenteEntity entity;
        try {
            DBManager.initHibernate();
            DAO dao = DipendenteDaoHibernate.instance();
            entity = (DipendenteEntity) dao.getById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Errore";
        }
        finally { DBManager.shutdown(); }

        if (entity == null) return "Ignoto";

        return entity.getNome() + " " + entity.getCognome();
    }
}