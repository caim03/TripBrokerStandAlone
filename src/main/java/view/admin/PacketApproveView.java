package view.admin;

import javafx.application.Platform;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.CatalogView;

import java.util.List;

public class PacketApproveView extends CatalogView {

    public PacketApproveView() {

        super();
    }

    @Override
    protected void fill() {

        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        List<ProdottoEntity> entities = (List<ProdottoEntity>) dao.getByCriteria("where tipo = 'Pacchetto' and stato != 1");
        DBManager.shutdown();

        Platform.runLater(() -> {

            if (entities == null) {
                Notifications.create().title("Empty catalog").text("No products in catalog").show();
            }
            else{
                for (ProdottoEntity p : entities){
                    this.names.add(p);
                }
                this.list.setItems(this.names);
            }

            this.pane.getChildren().remove(0);
            this.pane.getChildren().add(this.list);
        });
    }
}
