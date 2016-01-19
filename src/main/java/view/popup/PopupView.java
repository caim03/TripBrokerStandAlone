package view.popup;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;
import view.material.MaterialPopup;

/** Factory Method for popup **/
public abstract class PopupView {

    protected GridPane pane;
    private Parent gui;
    protected MaterialPopup parent;
    protected int row;
    public void setParent(MaterialPopup parent) { this.parent = parent; }

    protected String title;
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

    protected Pane getPane() {
        /** @result: Pane; cached popup pane, used for gui building **/

        return this.pane;
    }

    protected int getRow() {
        /** @result: int; number of row in popup pane **/

        return this.row;
    }

    protected void setRow(int row) {
        /** @param: int; last row position **/

        this.row = row;
    }

    public Parent getGui() { return gui == null? gui = generatePopup() : gui; }
}
