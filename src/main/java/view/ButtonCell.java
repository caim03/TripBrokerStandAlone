package view;


import controller.DeleteButtonController;
import controller.ModifyButtonController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.MaterialPopup;
import view.popup.EmployeePopup;
import view.popup.PopupView;

public class ButtonCell extends TableCell<DipendentiEntity, Boolean> {
    final Button cellButton;
    private DBTablePane pane;

    public ButtonCell(String type, DBTablePane pane){
        cellButton = new Button(type);
        this.pane = pane;

        if (type.equals("modify"))
            cellButton.setOnMouseClicked(event -> {

                DipendentiEntity entity = (DipendentiEntity) getTableRow().getItem();
                PopupView popupView = new EmployeePopup(getTableView(), entity, getTableRow().getIndex());
                new MaterialPopup(pane, popupView).show();
            });

        else cellButton.setOnMouseClicked(event -> delete((DipendentiEntity) getTableRow().getItem()));
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);
        }
    }

    void delete(DipendentiEntity entity) {

        new Thread(() -> {

            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            dao.delete(entity);
            DBManager.shutdown();

            Platform.runLater(() -> {
                Notifications.create().title("Deleted").text("The employee has been deleted").show();
                getTableView().getItems().remove(entity);
                getTableView().refresh();
            });
        }).start();
    }
}