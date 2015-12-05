package controller;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;
import model.DBManager;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.dao.ViaggioDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.EventoEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import java.sql.Date;
import java.time.ZoneId;

public class InsertOfferController {

    public static boolean handle(String name, String price, int quantity, String spinner, Node[] list) {
        if (name == null || "".equals(name)){
            return false;
        }

        if (price == null || "".equals(price)){
            return false;
        }

        if (quantity == 0){
            return false;
        }

        if ("Evento".equals(spinner)){
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            int seatField = Integer.parseInt(((TextField) list[2]).getText());
            Date date = new Date(Date.from((((DatePicker) list[3]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            int timePicker = ((CalendarTimeTextField) list[4]).getCalendar().getTime().getHours();
            int endPicker = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours();

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))){
                return false;
            }

            EventBuilder eventBuilder = new EventBuilder();
            eventBuilder.buildProduct(name, Double.parseDouble(price), "Evento");
            eventBuilder.buildOffer(ctyField, Double.parseDouble(price), quantity, (byte) 0, date);
            eventBuilder.buildEntity(seatField, timePicker, endPicker, locField);

            insertOfferEvent((EventoEntity) eventBuilder.getEntity());
        }

        else if ("Viaggio".equals(spinner)){
            String depField = ((TextField) list[0]).getText();
            String arrField = ((TextField) list[1]).getText();
            String vehSpinner = ((Spinner<String>) list[2]).getValue();
            String clsSpinner = ((Spinner<String>) list[3]).getValue();
            Date depDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            int depTime = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours();
            Date arrDate = new Date(Date.from((((DatePicker) list[6]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            int arrTime = ((CalendarTimeTextField) list[7]).getCalendar().getTime().getHours();

            if ((depField == null || "".equals(depField)) || (arrField == null || "".equals(arrField))){
                return false;
            }

            TravelBuilder travelBuilder = new TravelBuilder();
            travelBuilder.buildProduct(name, Double.parseDouble(price), "Viaggio");
            travelBuilder.buildOffer(depField, Double.parseDouble(price), quantity, (byte) 0, depDate);
            travelBuilder.buildEntity(arrField, depTime, arrTime, vehSpinner, clsSpinner, depField, arrField, arrDate);

            insertOfferTravel((ViaggioEntity) travelBuilder.getEntity());
        }

        else {
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            String starSpinner = ((Spinner<String>) list[2]).getValue();
            String srvSpinner = ((Spinner<String>) list[3]).getValue();
            Date startDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date endDate = new Date(Date.from((((DatePicker) list[5]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))){
                return false;
            }

            StayBuilder stayBuilder = new StayBuilder();
            stayBuilder.buildProduct(name, Double.parseDouble(price), "Pernottamento");
            stayBuilder.buildOffer(ctyField, Double.parseDouble(price), quantity, (byte) 0, startDate);
            stayBuilder.buildEntity(endDate, srvSpinner, starSpinner, locField);

            insertOfferStay((PernottamentoEntity) stayBuilder.getEntity());
        }

        return true;
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
