package view.popup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import view.material.MaterialPopup;

/** Factory Method for popup **/
public abstract class PopupView extends Pane {

    protected GridPane pane;
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
}
