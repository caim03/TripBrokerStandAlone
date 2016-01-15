package controller;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
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
import view.material.MaterialSpinner;
import view.material.NumericField;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

public class InsertOfferController {

    public static boolean handle(String name, String price, int quantity, String spinner, Node[] list) {

        if (name == null || "".equals(name)) {
            System.out.println("INVALID NAME: " + name);
            return false;
        }
        if (price == null || "".equals(price)) {
            System.out.println("INVALID PRICE");
            return false;
        }
        if (quantity == 0) {
            System.out.println("INVALID QUANTITY");
            return false; }

        if (Constants.event.equals(spinner)) {
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            int seatField = (int) ((NumericField) list[2]).getNumber();
            Date date = new Date(Date.from((((DatePicker) list[3]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            long day = date.getTime();

            long startHour = ((CalendarTimeTextField) list[4]).getCalendar().getTime().getHours() * 3600000;
            long endHour = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours() * 3600000;
            long startMinute = ((CalendarTimeTextField) list[4]).getCalendar().getTime().getMinutes() * 60000;
            long endMinute = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getMinutes() * 60000;

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))) {
                System.out.println("INVALID LOCATIONS");
                return false;
            }

            EventBuilder eventBuilder = new EventBuilder();
            eventBuilder.buildProduct(name, Double.parseDouble(price), Constants.event);
            eventBuilder.buildOffer(ctyField, Double.parseDouble(price), quantity, (byte) 0, new Timestamp(day + startHour + startMinute));
            eventBuilder.buildEntity(seatField, new Date(day + endHour + endMinute), locField);

            insertOfferEvent((EventoEntity) eventBuilder.getEntity());
        }

        else if (Constants.travel.equals(spinner)){
            String depField = ((TextField) list[0]).getText();
            String arrField = ((TextField) list[1]).getText();
            String vehSpinner = ((MaterialSpinner) list[2]).getValue();
            String clsSpinner = ((MaterialSpinner) list[3]).getValue();
            Date depDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date arrDate = new Date(Date.from((((DatePicker) list[6]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            long depDay = depDate.getTime();
            long arrDay = arrDate.getTime();

            long depHour = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours() * 3600000;
            long depMinute = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getMinutes() * 60000;
            long arrHour = ((CalendarTimeTextField) list[7]).getCalendar().getTime().getHours() * 3600000;
            long arrMinute = ((CalendarTimeTextField) list[7]).getCalendar().getTime().getMinutes() * 60000;

            if ((depField == null || "".equals(depField)) || (arrField == null || "".equals(arrField))) {
                System.out.println("INVALID STATIONS");
                return false;
            }

            Timestamp date1 = new Timestamp(depDay + depHour + depMinute);

            Timestamp date2 = new Timestamp(arrDay + arrHour + arrMinute);

            TravelBuilder travelBuilder = new TravelBuilder();
            travelBuilder.buildProduct(name, Double.parseDouble(price), Constants.travel);
            travelBuilder.buildOffer(depField, Double.parseDouble(price), quantity, (byte) 0, date1);
            travelBuilder.buildEntity(arrField, date2, vehSpinner, clsSpinner, depField, arrField);

            insertOfferTravel((ViaggioEntity) travelBuilder.getEntity());
        }

        else {
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            String starSpinner = ((MaterialSpinner) list[2]).getValue();
            String srvSpinner = ((MaterialSpinner) list[3]).getValue();
            Timestamp startDate = new Timestamp(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Timestamp endDate = new Timestamp(Date.from((((DatePicker) list[5]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))) {
                System.out.println("INVALID LOCATIONS");
                return false;
            }

            StayBuilder stayBuilder = new StayBuilder();
            stayBuilder.buildProduct(name, Double.parseDouble(price), Constants.stay);
            stayBuilder.buildOffer(ctyField, Double.parseDouble(price), quantity, (byte) 0, startDate);
            stayBuilder.buildEntity(endDate, srvSpinner, starSpinner, locField);

            insertOfferStay((PernottamentoEntity) stayBuilder.getEntity());
        }

        return true;
    }

    public static void insertOfferEvent(EventoEntity entity) {

        new Thread(() -> {
            DAO dao = EventoDaoHibernate.instance();
            DBManager.initHibernate();
            dao.store(entity);
            DBManager.shutdown();
        }).start();
    }

    public static void insertOfferStay(PernottamentoEntity entity) {

        new Thread(() -> {
            DAO dao = PernottamentoDaoHibernate.instance();
            DBManager.initHibernate();
            dao.store(entity);
            DBManager.shutdown();
        }).start();
    }

    public static void insertOfferTravel(ViaggioEntity entity) {

        new Thread(() -> {
            DAO dao = ViaggioDaoHibernate.instance();
            DBManager.initHibernate();
            dao.store(entity);
            DBManager.shutdown();
        }).start();
    }
}
