package view.admin;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.DAO;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.PoliticheEntity;
import view.DBTablePane;
import view.material.MaterialPopup;
import view.popup.PoliticsPopup;

import java.util.List;

public class ModifyPoliticsView extends DBTablePane {

    @Override
    protected TableView generateTable() {

        TableView<PoliticheEntity> list = new TableView<>();

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
        list.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    list.getSelectionModel().clearSelection();
                    if (newValue == null) return;
                    new MaterialPopup(
                            ModifyPoliticsView.this,
                            new PoliticsPopup(newValue, list)).show();
                });

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