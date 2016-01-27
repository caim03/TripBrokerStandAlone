package controller.admin;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.MaterialSpinner;

public class ModifyEmployeeController implements EventHandler<MouseEvent>{
    private TextField nameTxt;
    private TextField surnameTxt;
    private MaterialSpinner roleTxt;
    private TextField passTxt;
    private TextField mailTxt;
    private TableView<DipendentiEntity> list;
    private DipendentiEntity entity;
    private int index;

    /** @param nameTxt; textfield of the name
     *  @param surnameTxt; textfield of the surname
     *  @param roleTxt; spinner of the role
     *  @param passTxt; textfield of the password string
     *  @param mailTxt; textfield of the mail
     *  @param list; list of employees
     *  @param entity; employee
     *  @param index; **/
    public ModifyEmployeeController(TextField nameTxt, TextField surnameTxt,
                                    MaterialSpinner roleTxt, TextField passTxt, TextField mailTxt,
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
        if (!"".equals(roleTxt.getValue())){
            entity.setRuolo(roleTxt.getValue());
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
                Notifications.create().title("Modificato").text("Il dipendente è stato modificato con successo").show();
                list.getItems().set(index, entity);
                list.refresh();
            });
        }).start();
    }
}
