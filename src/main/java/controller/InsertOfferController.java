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
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.time.ZoneId;

public class InsertOfferController {

    public static void handle(String name, String price, String quantity, String spinner, Node[] list) {
        if (name == null || "".equals(name)){
            Notifications.create().title("Offer refused").text("The offer is refused. The name field cannot be empty").show();
            return;
        }

        if (price == null || "".equals(price)){
            Notifications.create().title("Offer refused").text("The offer is refused. The price field cannot be empty").show();
            return;
        }

        if ("".equals(quantity) || Integer.parseInt(quantity) == 0) {
            Notifications.create().title("Offer refused").text("The offer is refused. The quantity field cannot be empty or zero").show();
            return;
        }

        if ("Evento".equals(spinner)){
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            if ("".equals(((TextField) list[2]).getText())){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            int seatField = Integer.parseInt(((TextField) list[2]).getText());

            Date date = new Date(Date.from((((DatePicker) list[3]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (((CalendarTimeTextField) list[4]).getCalendar() == null){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            int timePicker = ((CalendarTimeTextField) list[4]).getCalendar().getTime().getHours();

            if (((CalendarTimeTextField) list[5]).getCalendar() == null){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            int endPicker = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours();

            EventBuilder eventBuilder = new EventBuilder();
            eventBuilder.buildProduct(name, Double.parseDouble(price));
            eventBuilder.buildOffer(ctyField, Double.parseDouble(price), Integer.parseInt(quantity), (byte) 0, date);
            eventBuilder.buildEntity(seatField, timePicker, endPicker, locField);

            insertOfferEvent((EventoEntity) eventBuilder.getEntity());
            Notifications.create().title("Offer added").text("The offer is added successfully").show();
        }

        else if ("Viaggio".equals(spinner)){
            String depField = ((TextField) list[0]).getText();
            String arrField = ((TextField) list[1]).getText();

            if ((depField == null || "".equals(depField)) || (arrField == null || "".equals(arrField))){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            String vehSpinner = ((Spinner<String>) list[2]).getValue();
            String clsSpinner = ((Spinner<String>) list[3]).getValue();

            Date depDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (((CalendarTimeTextField) list[5]).getCalendar() == null){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            int depTime = ((CalendarTimeTextField) list[5]).getCalendar().getTime().getHours();

            Date arrDate = new Date(Date.from((((DatePicker) list[6]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (((CalendarTimeTextField) list[7]).getCalendar() == null){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            int arrTime = ((CalendarTimeTextField) list[7]).getCalendar().getTime().getHours();


            TravelBuilder travelBuilder = new TravelBuilder();
            travelBuilder.buildProduct(name, Double.parseDouble(price));
            travelBuilder.buildOffer(depField, Double.parseDouble(price), Integer.parseInt(quantity), (byte) 0, depDate);
            travelBuilder.buildEntity(arrField, depTime, arrTime, vehSpinner, clsSpinner, depField, arrField, arrDate);

            insertOfferTravel((ViaggioEntity) travelBuilder.getEntity());
            Notifications.create().title("Offer added").text("The offer is added successfully").show();
        }

        else {
            String ctyField = ((TextField) list[0]).getText();
            String locField = ((TextField) list[1]).getText();
            String starSpinner = ((Spinner<String>) list[2]).getValue();
            String srvSpinner = ((Spinner<String>) list[3]).getValue();
            Date startDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date endDate = new Date(Date.from((((DatePicker) list[5]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if ((ctyField == null || "".equals(ctyField)) || (locField == null || "".equals(locField))){
                Notifications.create().title("Offer refused").text("The offer is refused. Please check all fields").show();
                return;
            }

            StayBuilder stayBuilder = new StayBuilder();
            stayBuilder.buildProduct(name, Double.parseDouble(price));
            stayBuilder.buildOffer(ctyField, Double.parseDouble(price), Integer.parseInt(quantity), (byte) 0, startDate);
            stayBuilder.buildEntity(endDate, srvSpinner, starSpinner, locField);

            insertOfferStay((PernottamentoEntity) stayBuilder.getEntity());
            Notifications.create().title("Offer added").text("The offer is added successfully").show();
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
