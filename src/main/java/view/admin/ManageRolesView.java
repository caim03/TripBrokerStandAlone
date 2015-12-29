package view.admin;


import controller.ManageRolesController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendentiEntity;
import model.entityDB.PoliticheEntity;
import view.ButtonCell;
import view.DBTablePane;

import java.util.List;

public class ManageRolesView extends DBTablePane{
    @Override
    protected List<? extends AbstractEntity> query() {
        List<DipendentiEntity> entities;
        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        entities = (List<DipendentiEntity>)dao.getAll();
        DBManager.shutdown();

        return entities;
    }

    @Override
    protected TableView generateTable() {
        TableView<DipendentiEntity> list = new TableView<DipendentiEntity>();

        TableColumn idColumn = new TableColumn("id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<PoliticheEntity, Integer>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<DipendentiEntity, String>("nome"));

        TableColumn surnameColumn = new TableColumn("Surname");
        surnameColumn.setMinWidth(150);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<DipendentiEntity, String>("cognome"));

        TableColumn roleColumn = new TableColumn("Role");
        roleColumn.setMinWidth(150);
        roleColumn.setCellValueFactory(new PropertyValueFactory<DipendentiEntity, String>("ruolo"));

        TableColumn mailColumn = new TableColumn("Mail");
        mailColumn.setMinWidth(200);
        mailColumn.setCellValueFactory(new PropertyValueFactory<DipendentiEntity, String>("mail"));

        TableColumn modBtnColumn = new TableColumn("Modify");
        modBtnColumn.setMinWidth(200);
        modBtnColumn.setSortable(false);
        modBtnColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DipendentiEntity, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<DipendentiEntity, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        modBtnColumn.setCellFactory(param -> new ButtonCell("modify", list, this));

        TableColumn delBtnColumn = new TableColumn("Delete");
        delBtnColumn.setMinWidth(200);
        delBtnColumn.setSortable(false);
        delBtnColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DipendentiEntity, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<DipendentiEntity, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        delBtnColumn.setCellFactory(param -> new ButtonCell("delete", list, this));

        list.getColumns().addAll(idColumn, nameColumn, surnameColumn, roleColumn, mailColumn, modBtnColumn, delBtnColumn);
        list.setOnMouseClicked(new ManageRolesController(list, this));

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
    }
}