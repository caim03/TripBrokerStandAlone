package controller;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;

public class ModifyEmployeeController implements EventHandler<MouseEvent>{
    private String nameTxt, surnameTxt, roleTxt, passTxt, mailTxt;
    private TableView<DipendentiEntity> list;
    private DipendentiEntity entity;

    public ModifyEmployeeController(String nameTxt, String surnameTxt, String roleTxt, String passTxt, String mailTxt, TableView<DipendentiEntity> list, DipendentiEntity entity) {
        this.nameTxt = nameTxt;
        this.surnameTxt = surnameTxt;
        this.roleTxt = roleTxt;
        this.passTxt = passTxt;
        this.mailTxt = mailTxt;
        this.list = list;

        this.entity = entity;
    }

    @Override
    public void handle(MouseEvent event) {

        if (!"".equals(nameTxt)){
            entity.setNome(nameTxt);
        }
        if (!"".equals(surnameTxt)){
            entity.setCognome(surnameTxt);
        }
        if (!"".equals(roleTxt)){
            entity.setRuolo(roleTxt);
        }
        if (!"".equals(passTxt)){
            entity.setPasswordLogin(passTxt);
        }
        if (!"".equals(mailTxt)){
            entity.setMail(mailTxt);
        }

        new Thread(()-> {
            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            dao.update(entity);
            DBManager.shutdown();

            Platform.runLater(() -> {
                Notifications.create().title("Updated").text("The employee has been updated").show();
                list.refresh();
            });
        }).start();
    }
}
