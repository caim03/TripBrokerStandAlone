package view.desig;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.dao.DAO;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import view.DBTablePane;
import view.PacketFormView;
import view.material.MaterialPopup;
import view.popup.FormPopup;

import java.util.List;

public class PacketEditView extends DBTablePane {

    @Override
    protected List<ProdottoEntity> query() {

        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        List<ProdottoEntity> entities = (List<ProdottoEntity>) dao.getByCriteria("where tipo = 'Pacchetto' and stato = 2");
        DBManager.shutdown();

        return entities;
    }

    @Override
    protected TableView generateTable() {
        TableView<ProdottoEntity> list = new TableView<>();

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Integer>("id"));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(400);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, String>("nome"));
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(300);
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, Double>("prezzo"));
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(350);
        typeColumn.setCellValueFactory(new PropertyValueFactory<ProdottoEntity, String>("tipo"));

        list.getColumns().addAll(idColumn, nameColumn, priceColumn, typeColumn);

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            list.getSelectionModel().clearSelection();
            if (newValue != null) {
                MaterialPopup popup = new MaterialPopup(PacketEditView.this,
                        new FormPopup(
                                new PacketAssembleView(
                                        new PacketFormView(
                                                (CreaPacchettoEntity) newValue))), true);

                popup.show();
            }
        });

        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);

        return list;
    }
}
