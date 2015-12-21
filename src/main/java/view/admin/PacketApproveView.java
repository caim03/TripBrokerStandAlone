package view.admin;

import javafx.application.Platform;
import javafx.scene.control.TableView;
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
    protected List<ProdottoEntity> query() {

        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        List<ProdottoEntity> entities = (List<ProdottoEntity>) dao.getByCriteria("where tipo = 'Pacchetto' and stato != 1");
        DBManager.shutdown();

        return entities;
    }
}
