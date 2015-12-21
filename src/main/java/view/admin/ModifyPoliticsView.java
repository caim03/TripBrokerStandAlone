package view.admin;

import controller.ModifyPoliticsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;

import java.util.List;

public class ModifyPoliticsView extends GridPane {

    public ModifyPoliticsView() {

        ObservableList<PoliticheEntity> names = FXCollections.observableArrayList();
        TableView<PoliticheEntity> list = new TableView<PoliticheEntity>();
        ProgressBar progressBar = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        this.getChildren().add(progressBar);
        this.setAlignment(Pos.CENTER);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Integer>("id"));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, String>("nome"));
        TableColumn minColumn = new TableColumn("Percent Min");
        minColumn.setMinWidth(250);
        minColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));
        TableColumn maxColumn = new TableColumn("Percent Max");
        maxColumn.setMinWidth(250);
        maxColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));

        list.getColumns().addAll(idColumn, nameColumn, minColumn, maxColumn);

        new Thread(() -> {

            List<PoliticheEntity> politicheEntities;
            DAO dao = PoliticheDaoHibernate.instance();
            DBManager.initHibernate();
            politicheEntities = (List<PoliticheEntity>)dao.getAll();
            DBManager.shutdown();

            Platform.runLater(() -> {
                if (politicheEntities == null)
                    Notifications.create().title("Empty politics").text("No politics in database").show();

                else {
                    for (PoliticheEntity e : politicheEntities) {
                        names.add(e);
                    }
                    list.setItems(names);
                }

                this.getChildren().remove(0);
                this.getChildren().add(list);
            });
        }).start();

        list.setOnMouseClicked(new ModifyPoliticsController(list));
    }
}