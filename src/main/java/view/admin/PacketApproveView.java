package view.admin;

import com.jfoenix.controls.JFXTabPane;
import controller.TableViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;

import java.util.List;


public class PacketApproveView extends JFXTabPane {

    public static Parent buildScene(){

        ObservableList<ProdottoEntity> names = FXCollections.observableArrayList();
        TableView<ProdottoEntity> list = new TableView<ProdottoEntity>();

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(40);
        idColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Integer>("id"));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(500);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, String>("nome"));
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(500);
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Double>("prezzo"));

        list.getColumns().addAll(idColumn, nameColumn, priceColumn);

        List<ProdottoEntity> prodottoEntities;
        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        prodottoEntities = (List<ProdottoEntity>) dao.getByCriteria("where tipo='Pacchetto' and stato!=1");
        DBManager.shutdown();

        if (prodottoEntities == null) {
            Notifications.create().title("Empty catalog").text("No packets in catalog").show();
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
