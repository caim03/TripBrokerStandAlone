package view.admin;

import controller.ModifyPoliticsController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.dao.DAO;
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
        TableColumn nameColumn = new TableColumn("Nome");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, String>("nome"));
        TableColumn valColumn = new TableColumn("Valore");
        valColumn.setMinWidth(260);
        valColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Double>("valore"));

        list.getColumns().addAll(idColumn, nameColumn, valColumn);
        list.setOnMouseClicked(event -> ModifyPoliticsController.handle(list, this));

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