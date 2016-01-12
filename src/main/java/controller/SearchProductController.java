package controller;


import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.DBManager;
import model.entityDB.EventoEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.agent.SellProductView;
import view.material.DBListView;
import view.material.MaterialPopup;
import view.material.MaterialSpinner;
import view.popup.*;

import java.sql.Date;
import java.time.ZoneId;

public class SearchProductController {

    public static DBListView handle(String spinner, Node[] list, SellProductView sellProductView) {
        /** @param String sp; product selected in spinner as string
         *  @param Node[]; list of node that draw right graphic
         *  @param SellProductView; reference to view
         *  @result DBListView; the list of offers **/

        DBListView listView; // list of offers

        // if offer is an event
        if (Constants.event.equals(spinner)) {
            String city = ((TextField) list[0]).getText();
            Date date = new Date(Date.from((((DatePicker) list[1]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (city == null || "".equals(city)) {
                return null;
            }

            listView = retrieveEvents(city, date);
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.equals(oldValue)) return;
                new MaterialPopup(
                        sellProductView,
                        new SellPopup(new EventPopup((EventoEntity)newValue), (EventoEntity)newValue))
                        .show();
            });
        }

        // if offer is a travel
        else if (Constants.travel.equals(spinner)) {
            String departure = ((TextField) list[0]).getText();
            String arrival = ((TextField) list[1]).getText();
            String vehicle = (String) ((MaterialSpinner) list[2]).getValue();
            String vehClass = (String) ((MaterialSpinner) list[3]).getValue();
            Date depDate = new Date(Date.from((((DatePicker) list[4]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date arrDate = new Date(Date.from((((DatePicker) list[5]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (departure == null || "".equals(departure) || arrival == null || "".equals(arrival)
                    || vehicle == null || "".equals(vehicle) || vehClass == null || "".equals(vehClass)) {
                return null;
            }

            listView = retrieveTravels(departure, arrival, vehicle, vehClass, depDate, arrDate);
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.equals(oldValue)) return;
                new MaterialPopup(
                        sellProductView,
                        new SellPopup(new TravelPopup((ViaggioEntity)newValue), (ViaggioEntity)newValue))
                        .show();
            });
        }

        // if offer is a stay
        else if (Constants.stay.equals(spinner)) {
            String city = ((TextField) list[0]).getText();
            Date checkIn = new Date(Date.from((((DatePicker) list[1]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date checkOut = new Date(Date.from((((DatePicker) list[2]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (city == null || "".equals(city)) {
                return null;
            }

            listView = retrieveStay(city, checkIn, checkOut);
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.equals(oldValue)) return;
                new MaterialPopup(
                        sellProductView,
                        new SellPopup(new StayPopup((PernottamentoEntity)newValue), (PernottamentoEntity)newValue))
                        .show();
            });
        }

        // if offer is a packet
        else {
            return null;
        }

        return listView;
    }

    private static DBListView retrieveEvents(String city, Date date) {
        /** @param String; the city of offer to search
         *  @param Date; the date of offer to search
         *  @result DBListView; list of offer retrieved by the query **/

        DBListView listView;
        listView = new DBListView("from EventoEntity where città like '" + city + "' and data_inizio >= '" +
                date + "' and quantità > 0");

        return listView;
    }

    private static DBListView retrieveTravels(String deparure, String arrival, String vehicle, String vehClass, Date depDate, Date arrDate) {
        /** @param String; departure place of the offer
         *  @param String; arrival place of the offer
         *  @param String; vehicle used for the travel
         *  @param String; vehicle class
         *  @param Date; departure date of the offer
         *  @param Date; arrival date of the offer
         *  @return DBListView; list of the offer retrieved by the query **/

        DBListView listView;
        listView = new DBListView("from ViaggioEntity where stazione_partenza like '" + deparure
                + "' and stazione_arrivo like '" + arrival +
                "' and mezzo like '" + vehicle +
                "' and classe like '" + vehClass +
                "' and data_inizio >= '" + depDate +
                "' and data_arrivo <= '" + arrDate +
                "' and quantità > 0");

        return listView;
    }

    private static DBListView retrieveStay(String city, Date checkIn, Date checkOut) {
        /** @param String; the city of offer to search
         *  @param Date; check-in date (start of stay)
         *  @param Date; check-out date (end of stay)
         *  @result DBListView; list of the offer retrieved by the query **/

        DBListView listView;
        listView = new DBListView("from PernottamentoEntity where città like '" + city
                + "' and data_inizio >= '" + checkIn +
                "' and data_finale <= '" + checkOut +
                "' and quantità > 0");

        return listView;
    }
}
