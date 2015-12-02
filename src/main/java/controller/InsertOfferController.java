package controller;

import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import model.DBManager;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.dao.ViaggioDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.EventoEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;

import java.sql.Date;

public class InsertOfferController {

    public static void handle(String name, String price, String spinner, Node[] list) {
        if (name == null || "".equals(name)){
            return;
        }

        if (price == null || "".equals(price)){
            return;
        }

        if ("Evento".equals(spinner)){
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            String seatField = ((TextField) list[2]).getText();

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField)) ||
                    (seatField == null || "".equals(seatField))){
                return;
            }

            // BUILDER DI PROVA STATICO
            EventBuilder eventBuilder = new EventBuilder();
            eventBuilder.buildProduct(name, Double.parseDouble(price));
            eventBuilder.buildOffer(ctyField, Double.parseDouble(price), 2, (byte) 0, new Date(2015, 12, 2));
            eventBuilder.buildEntity(8, 12, 14, "Piazza di Spagna");

            insertOfferEvent((EventoEntity) eventBuilder.getEntity());
        }

        else if ("Viaggio".equals(spinner)){
            String depField = ((TextField) list[0]).getText();
            String arrField = ((TextField) list[1]).getText();
            String vehSpinner = ((Spinner<String>) list[2]).getValue();
            String clsSpinner = ((Spinner<String>) list[3]).getValue();

            if ((depField == null || "".equals(depField)) || (arrField == null || "".equals(arrField))){
                return;
            }

            TravelBuilder travelBuilder = new TravelBuilder();
            travelBuilder.buildProduct(name, Double.parseDouble(price));
            travelBuilder.buildOffer("Roma", Double.parseDouble(price), 5, (byte) 0, new Date(2015, 12, 2));
            travelBuilder.buildEntity("Milano", 12, 15, vehSpinner, clsSpinner, depField, arrField);

            insertOfferTravel((ViaggioEntity) travelBuilder.getEntity());
        }

        else {
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            String starSpinner = ((Spinner<String>) list[2]).getValue();

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))){
                return;
            }

            StayBuilder stayBuilder = new StayBuilder();
            stayBuilder.buildProduct(name, Double.parseDouble(price));
            stayBuilder.buildOffer(ctyField, Double.parseDouble(price), 7, (byte) 0, new Date(2015, 12, 2));
            stayBuilder.buildEntity(new Date(2015, 12, 9), "Mezza Pensione", starSpinner, locField);

            insertOfferStay((PernottamentoEntity) stayBuilder.getEntity());
        }
    }

    public static void insertOfferEvent(EventoEntity entity) {

        DAO dao = EventoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    public static void insertOfferStay(PernottamentoEntity entity) {

        DAO dao = PernottamentoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    public static void insertOfferTravel(ViaggioEntity entity) {

        DAO dao = ViaggioDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }
}
