package view.admin;

import controller.ModifyPoliticsController;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.PoliticheEntity;
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
        minColumn.setMinWidth(260);
        minColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));
        TableColumn maxColumn = new TableColumn("Percent Max");
        maxColumn.setMinWidth(260);
        maxColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("percentuale_max"));

        list.getColumns().addAll(idColumn, nameColumn, minColumn, maxColumn);
        list.setOnMouseClicked(event -> ModifyPoliticsController.handle(list));

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
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