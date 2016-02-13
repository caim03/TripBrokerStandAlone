package view.admin;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.DBManager;
import model.dao.DipendenteDaoHibernate;
import model.dao.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendenteEntity;
import model.entityDB.PoliticaEntity;
import view.ButtonCell;
import view.DBTablePane;

import java.util.List;

public class ManageRolesView extends DBTablePane {

    @Override
    protected List<? extends AbstractEntity> query() {
        List<DipendenteEntity> entities;
        DAO dao = DipendenteDaoHibernate.instance();
        DBManager.initHibernate();
        entities = (List<DipendenteEntity>)dao.getAll();
        DBManager.shutdown();

        return entities;
    }

    @Override
    protected TableView generateTable() {
        TableView<DipendenteEntity> list = new TableView<>();

        TableColumn idColumn = new TableColumn("id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<PoliticaEntity, Integer>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<DipendenteEntity, String>("nome"));

        TableColumn surnameColumn = new TableColumn("Surname");
        surnameColumn.setMinWidth(150);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<DipendenteEntity, String>("cognome"));

        TableColumn roleColumn = new TableColumn("Role");
        roleColumn.setMinWidth(150);
        roleColumn.setCellValueFactory(new PropertyValueFactory<DipendenteEntity, String>("ruolo"));

        TableColumn mailColumn = new TableColumn("Mail");
        mailColumn.setMinWidth(200);
        mailColumn.setCellValueFactory(new PropertyValueFactory<DipendenteEntity, String>("mail"));

        TableColumn modBtnColumn = new TableColumn("");
        modBtnColumn.setMinWidth(200);
        modBtnColumn.setSortable(false);
        modBtnColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DipendenteEntity, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<DipendenteEntity, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        modBtnColumn.setCellFactory(param -> new ButtonCell("Modifica", this));

        TableColumn delBtnColumn = new TableColumn("");
        delBtnColumn.setMinWidth(200);
        delBtnColumn.setSortable(false);
        delBtnColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DipendenteEntity, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<DipendenteEntity, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });
        delBtnColumn.setCellFactory(param -> new ButtonCell("Elimina", this));

        list.getColumns().addAll(idColumn,
                nameColumn,
                surnameColumn,
                roleColumn,
                mailColumn,
                modBtnColumn,
                delBtnColumn);

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
    }
}
