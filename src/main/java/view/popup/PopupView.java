package view.popup;

import javafx.scene.Parent;
import javafx.scene.layout.Region;
import model.DBManager;
import model.dao.DAO;
import model.dao.DipendentiDaoHibernate;
import model.entityDB.DipendentiEntity;
import view.material.MaterialPopup;

/** Factory Method for popup **/
public abstract class PopupView {

    protected Region gui;
    protected MaterialPopup parent;
    public void setParent(MaterialPopup parent) { this.parent = parent; }

    protected abstract Parent generatePopup();

    protected String getEmployee(int id){
        /** @param: int; Employee id
         *  @result: String; name of logged in Employee **/

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

    public Region getGui() {
        if (gui == null) gui = (Region) generatePopup();
        return gui;
    }
}
