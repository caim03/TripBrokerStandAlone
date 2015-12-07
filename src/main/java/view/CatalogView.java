package view;

import controller.TableViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.EventoEntity;
import model.entityDB.ProdottoEntity;
import model.entityDB.ViaggioEntity;
import org.controlsfx.control.Notifications;

import java.util.List;


public class CatalogView {

    public static Parent buildScene(){

        ObservableList<ProdottoEntity> names = FXCollections.observableArrayList();
        TableView<ProdottoEntity> list = new TableView<ProdottoEntity>();

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

        List<ProdottoEntity> prodottoEntities;
        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        prodottoEntities = (List<ProdottoEntity>) dao.getAll();
        DBManager.shutdown();

        if (prodottoEntities == null) {
            Notifications.create().title("Empty catalog").text("No products in catalog").show();
        }
        else{
            for (ProdottoEntity p : prodottoEntities){
                names.add(p);
            }
            list.setItems(names);
        }

        list.setOnMouseClicked(new TableViewController(list));

        return list;
    }
}
