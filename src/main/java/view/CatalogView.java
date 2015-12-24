package view;

import controller.TableViewController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.ProdottoEntity;

import java.util.List;


public class CatalogView extends DBTablePane {

    @Override
    protected List<ProdottoEntity> query() {

        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        List<ProdottoEntity> entities = (List<ProdottoEntity>) dao.getAll();
        DBManager.shutdown();

        return entities;
    }

    @Override
    protected TableView generateTable() {

        TableView<ProdottoEntity> list = new TableView<>();

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(20);
        idColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Integer>("id"));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(350);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, String>("nome"));
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(300);
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Double>("prezzo"));
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(350);
        typeColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, String>("tipo"));

        list.getColumns().addAll(idColumn, nameColumn, priceColumn, typeColumn);
        list.setOnMouseClicked(new TableViewController(list, this));

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
    }
}
