package view;

import controller.CatalogHandler;
import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;

import java.util.List;


public class CatalogView {

    public static Parent buildScene(){

        ListView<String> list = new ListView<String>();
        ObservableList<String> names = FXCollections.observableArrayList();
        list.setCellFactory(ComboBoxListCell.forListView(list.getItems()));

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
                names.add(p.getNome());
            }
            list.setItems(names);
        }

        return list;
    }
}
