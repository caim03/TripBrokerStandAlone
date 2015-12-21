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
import javafx.scene.layout.Priority;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.PoliticheEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.CatalogView;
import view.DBTablePane;

import java.util.List;

public class ModifyPoliticsView extends DBTablePane {

    @Override
    protected TableView generateTable() {

        TableView<PoliticheEntity> list = new TableView<PoliticheEntity>();

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
        list.setOnMouseClicked(new ModifyPoliticsController(list));

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
    }

    @Override
    protected void fill() {

        new Thread(() -> {

            List<PoliticheEntity> entities = query();

            Platform.runLater(() -> {

                getChildren().remove(0);

                if (entities == null)
                    Notifications.create().title("Empty politics").text("No politics in database").show();

                else {

                    ObservableList<PoliticheEntity> names = FXCollections.observableArrayList();
                    for (PoliticheEntity e : entities) names.add(e);

                    TableView<PoliticheEntity> list = new TableView<>();
                    list.setItems(names);

                    getChildren().add(list);
                    setHgrow(list, Priority.ALWAYS);
                    setVgrow(list, Priority.ALWAYS);
                }
            });
        }).start();
    }

    @Override
    protected List<PoliticheEntity> query() {

        List<PoliticheEntity> entities;
        DAO dao = PoliticheDaoHibernate.instance();
        DBManager.initHibernate();
        entities = (List<PoliticheEntity>)dao.getAll();
        DBManager.shutdown();

        return entities;
    }
}