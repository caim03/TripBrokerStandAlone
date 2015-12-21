package view.admin;

import controller.ModifyPoliticsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;

import java.util.List;

public class ModifyPoliticsView extends TableView<PoliticheEntity> {

    public ModifyPoliticsView() {

        ObservableList<PoliticheEntity> names = FXCollections.observableArrayList();

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(40);
        idColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Integer>("id"));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, String>("nome"));
        TableColumn minColumn = new TableColumn("Percent Min");
        minColumn.setMinWidth(100);
        minColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));
        TableColumn maxColumn = new TableColumn("Percent Max");
        maxColumn.setMinWidth(100);
        maxColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));

        getColumns().addAll(idColumn, nameColumn, minColumn, maxColumn);

        List<PoliticheEntity> politicheEntities;
        DAO dao = PoliticheDaoHibernate.instance();
        DBManager.initHibernate();
        politicheEntities = (List<PoliticheEntity>)dao.getAll();
        DBManager.shutdown();

        if (politicheEntities == null)
            Notifications.create().title("Empty politics").text("No politics in database").show();

        else {
            for (PoliticheEntity e : politicheEntities) names.add(e);
            setItems(names);
        }

        setOnMouseClicked(new ModifyPoliticsController(this));
    }
}