package view.popup;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import view.material.MaterialPopup;

// Factory Method for popup
public abstract class PopupView extends Pane {

    protected MaterialPopup parent;
    public void setParent(MaterialPopup parent) { this.parent = parent; }

    protected String title;
    protected abstract Parent generatePopup();

    protected String getEmployee(int id){
        DipendentiEntity entity;
        DBManager.initHibernate();
        DAO dao = DipendentiDaoHibernate.instance();
        entity = (DipendentiEntity) dao.getById(id);
        DBManager.shutdown();

        if (entity == null){
            return "Unknown";
        }

        return entity.getNome() + " " + entity.getCognome();
    }
}
