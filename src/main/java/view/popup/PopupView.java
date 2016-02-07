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

    public Region getGui() {
        if (gui == null) gui = (Region) generatePopup();
        return gui;
    }
}
