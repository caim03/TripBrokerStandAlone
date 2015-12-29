package controller;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;

public class ModifyEmployeeController implements EventHandler<MouseEvent>{
    private TextField nameTxt;
    private TextField surnameTxt;
    private TextField roleTxt;
    private TextField passTxt;
    private TextField mailTxt;
    private TableView<DipendentiEntity> list;
    private DipendentiEntity entity;
    private int index;

    public ModifyEmployeeController(TextField nameTxt, TextField surnameTxt,
                                    TextField roleTxt, TextField passTxt, TextField mailTxt,
                                    TableView<DipendentiEntity> list, DipendentiEntity entity, int index) {
        this.nameTxt = nameTxt;
        this.surnameTxt = surnameTxt;
        this.roleTxt = roleTxt;
        this.passTxt = passTxt;
        this.mailTxt = mailTxt;
        this.list = list;
        this.entity = entity;
        this.index = index;
    }

    @Override
    public void handle(MouseEvent event) {

        if (!"".equals(nameTxt.getText())){
            entity.setNome(nameTxt.getText());
        }
        if (!"".equals(surnameTxt.getText())){
            entity.setCognome(surnameTxt.getText());
        }
        if (!"".equals(roleTxt.getText())){
            entity.setRuolo(roleTxt.getText());
        }
        if (!"".equals(passTxt.getText())){
            entity.setPasswordLogin(passTxt.getText());
        }
        if (!"".equals(mailTxt.getText())){
            entity.setMail(mailTxt.getText());
        }

        new Thread(()-> {
            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            dao.update(entity);
            DBManager.shutdown();

            Platform.runLater(() -> {
                Notifications.create().title("Updated").text("The employee has been updated").show();
                list.getItems().set(index, entity);
                list.refresh();
            });
        }).start();
    }
}
