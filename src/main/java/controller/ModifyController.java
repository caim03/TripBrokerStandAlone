package controller;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;

public class ModifyController implements EventHandler<MouseEvent>{
    private PoliticheEntity politicheEntity;
    private TextField name, min, max;


    public ModifyController(PoliticheEntity politicheEntity, TextField name, TextField min, TextField max) {
        this.politicheEntity = politicheEntity;
        this.name = name;
        this.min = min;
        this.max = max;
    }


    @Override
    public void handle(MouseEvent event) {
        String namePolitic, minPerc, maxPerc;

        namePolitic = name.getText();
        minPerc = min.getText();
        maxPerc = max.getText();

        if ("".equals(namePolitic)){
            politicheEntity.setNome(namePolitic);
        }

        if ("".equals(minPerc)){
            politicheEntity.setPercentualeMin(Double.parseDouble(minPerc));
        }

        if ("".equals(maxPerc)){
            politicheEntity.setPercentualeMax(Double.parseDouble(maxPerc));
        }

        ((Node)event.getSource()).getScene().getWindow().hide();

        DAO dao = PoliticheDaoHibernate.instance();
        DBManager.initHibernate();
        dao.update(politicheEntity);
        DBManager.shutdown();

        Notifications.create().title("Modified").text("The politic has been modified").show();
    }
}
