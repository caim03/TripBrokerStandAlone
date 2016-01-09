package controller;


import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.DBManager;
import view.material.DBListView;
import view.material.MaterialSpinner;

import java.sql.Date;
import java.time.ZoneId;

public class SearchProductController {

    public static DBListView handle(String spinner, Node[] list) {

        DBListView listView;

        if (Constants.event.equals(spinner)) {
            String city = ((TextField) list[0]).getText();
            Date date = new Date(Date.from((((DatePicker) list[1]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (city == null || "".equals(city)) {
                return null;
            }

            listView = retrieveEvents(city, date);
            // TODO SET LISTENER : POPUP WITH DETAILS AND BUTTON SELL WITH QUANTITY
        }

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
            // TODO SET LISTENER : POPUP WITH DETAILS AND BUTTON SELL WITH QUANTITY
        }

        else if (Constants.stay.equals(spinner)) {
            String city = ((TextField) list[0]).getText();
            Date checkIn = new Date(Date.from((((DatePicker) list[1]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
            Date checkOut = new Date(Date.from((((DatePicker) list[2]).getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

            if (city == null || "".equals(city)) {
                return null;
            }

            listView = retrieveStay(city, checkIn, checkOut);
            // TODO SET LISTENER : POPUP WITH DETAILS AND BUTTON SELL WITH QUANTITY
        }

        else {
            return null;
        }

        return listView;
    }

    private static DBListView retrieveEvents(String city, Date date) {
        DBListView listView;
        listView = new DBListView("from EventoEntity where città like '" + city + "' and data_inizio >= '" +
                date + "' and quantità > 0");

        return listView;
    }

    private static DBListView retrieveTravels(String deparure, String arrival, String vehicle, String vehClass, Date depDate, Date arrDate) {
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
        DBListView listView;
        listView = new DBListView("from PernottamentoEntity where città like '" + city
                + "' and data_inizio >= '" + checkIn +
                "' and data_finale <= '" + checkOut +
                "' and quantità > 0");

        return listView;
    }
}
