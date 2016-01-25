package view.scout;

import controller.Constants;
import controller.builder.EntityBuilder;
import controller.InsertOfferController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.CalendarTimeTextField;
import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import org.controlsfx.control.Notifications;
import view.Collector;
import view.material.LayerPane;
import view.material.MaterialSpinner;
import view.material.MaterialTextField;
import view.material.NumericField;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class OfferInsertionView extends LayerPane implements Collector {

    private TextField nameField;
    private NumericField priceField, quField;
    private OfferForm form;
    private MaterialSpinner spinner;

    /** @return String; return the name of the offer **/
    public String getName() { return nameField.getText(); }

    /** @return String; return the price of the offer as string **/
    public Double getPrice() { return priceField.getNumber(); }

    /** @return int; return the current quantity of the offer **/
    public int getQuantity() { return (int) quField.getNumber(); }

    /** @return String; return the spinner value selected **/
    public String getType() { return spinner.getValue(); }

    public String getCity() { return form.getCity(); }

    public Timestamp getDate() { return form.getMainDate(); }

    public void harvest() {

        String name = getName(),
               type = getType(),
               city = getCity();
        double price = getPrice();
        int qu = getQuantity();
        Timestamp date = getDate();
        EntityBuilder.Arguments arguments = form.harvest();

        new Thread(() -> {
            boolean result = InsertOfferController.handle(name, price, qu, type, city, date, arguments);
            Platform.runLater(() -> {
                Notifications notifications = Notifications.create();
                if (!result) notifications.
                        text("Non è stato possibile inserire l'offerta, per favore ricontrolla tutti i campi").
                        title("Errore nell'inserimento").showWarning();
                else notifications.
                        text("L'offerta è stata inserita con successo").
                        title("Inserimento avvenuto con successo").showConfirm();
            });
        }).start();
    }

    public OfferInsertionView() {

        Label name = new Label("Name");
        nameField = new MaterialTextField();
        nameField.setPromptText("Insert offer name");

        Label price = new Label("Price");
        priceField = new NumericField();
        priceField.setPromptText("Insert offer price");

        Label qu = new Label("Quantity");
        quField = new NumericField(false);
        quField.setPromptText("Insert offer quantity");

        spinner = new MaterialSpinner(this, FXCollections.<String>observableArrayList(Constants.travel, Constants.event, Constants.stay));

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(name, 0, 1);
        pane.add(price, 0, 2);
        pane.add(qu, 0, 3);

        pane.add(nameField, 1, 1, 2, 1);
        pane.add(priceField, 1, 2, 2, 1);
        pane.add(quField, 1, 3, 2, 1);
        pane.add(spinner, 1, 4, 2, 1);

        form = new EmptyForm();
        VBox vBox = new VBox(pane, form);

        spinner.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println(newValue);
            form = fromOffer(newValue);
            vBox.getChildren().remove(1);
            vBox.getChildren().add(form);
            form.generate();
        });

        attach(vBox);
    }

    private OfferForm fromOffer(String type) {
        if (Constants.travel.equals(type)) return new TravelForm();
        else if (Constants.event.equals(type)) return new EventForm();
        else if (Constants.stay.equals(type)) return new OvernightForm();
        else return new EmptyForm();
    }

    private class EmptyForm extends OfferForm {

        @Override void generate() { }
        @Override EntityBuilder.Arguments harvest() { return null; }
        @Override protected String getCity() { return null; }
        @Override protected Timestamp getMainDate() { return null; }
    }

    public abstract class OfferForm<T extends OffertaEntity> extends GridPane {

        protected TextField cityField;
        protected DatePicker mainDatePicker;

        OfferForm() {
            setStyle("-fx-background-color: white");
            setHgap(25);
            setVgap(8);
            setPadding(new Insets(25));
        }

        abstract void generate();
        abstract EntityBuilder.Arguments harvest();

        protected String getCity() { return cityField.getText(); }
        protected Timestamp getMainDate() {

            LocalDate localDate = mainDatePicker.getValue();
            if (localDate == null) return null;
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

            return Timestamp.from(instant);
        }
    }

    private class TravelForm extends OfferForm<ViaggioEntity> {

        private TextField arrField;
        private DatePicker arrDatePicker;
        private CalendarTimeTextField depTimePicker, arrTimePicker;
        private MaterialSpinner vehSpinner, clsSpinner;

        private void fields() {
            Label departure = new Label("Partenza");
            cityField = new MaterialTextField();
            cityField.setPromptText("Inserisci città di partenza");

            Label arrival = new Label("Arrivo");
            arrField = new MaterialTextField();
            arrField.setPromptText("Inserisci destinazione");

            add(departure, 0, 1);
            add(arrival, 0, 2);
            add(cityField, 1, 1);
            add(arrField, 1, 2);
        }

        private void pickers() {

            mainDatePicker = new DatePicker(LocalDate.now());
            depTimePicker = new CalendarTimeTextField();

            arrDatePicker = new DatePicker(LocalDate.now());
            arrTimePicker = new CalendarTimeTextField();

            add(mainDatePicker, 3, 1);
            add(arrDatePicker, 3, 2);
            add(depTimePicker, 4, 1);
            add(arrTimePicker, 4, 2);
        }

        private void spinners() {

            vehSpinner = new MaterialSpinner(OfferInsertionView.this, FXCollections.observableArrayList("Aereo", "Treno", "Bus"));
            clsSpinner = new MaterialSpinner(OfferInsertionView.this, FXCollections.observableArrayList("Prima", "Seconda"));

            add(new Label("Mezzo"), 0, 3);
            add(new Label("Classe"), 3, 3);
            add(vehSpinner, 1, 3);
            add(clsSpinner, 4, 3);
        }
        @Override
        void generate() {
            fields();
            pickers();
            spinners();
        }

        @Override
        EntityBuilder.Arguments harvest() {

            String arrival = arrField.getText();
            if (arrival == null || "".equals(arrival)) return null;

            LocalDate arrLcl = arrDatePicker.getValue();
            Calendar arrCalendar = arrTimePicker.getCalendar();
            if (arrLcl == null || arrCalendar == null)
                return null;

            String vehicle = vehSpinner.getValue(), _class = clsSpinner.getValue();
            if (vehicle == null || "".equals(vehicle) ||
                    _class == null || "".equals(_class)) return null;

            Instant arrInstant = arrLcl.atStartOfDay(ZoneId.systemDefault()).toInstant();

            long arrHour = arrCalendar.getTime().getHours() * 3600000;
            long arrMinute = arrCalendar.getTime().getMinutes() * 60000;

            arrInstant.plusMillis(arrHour + arrMinute);

            Timestamp arrivalTime = Timestamp.from(arrInstant);

            return EntityBuilder.Arguments.from(arrival, arrivalTime, vehicle, _class);
        }

        @Override
        protected Timestamp getMainDate() {
            Timestamp base = super.getMainDate();
            Calendar calendar = depTimePicker.getCalendar();
            if (calendar == null) return base;

            long hour = calendar.getTime().getHours() * 3600000;
            long minute = calendar.getTime().getMinutes() * 60000;

            return new Timestamp(base.getTime() + hour + minute);
        }
    }

    private class EventForm extends OfferForm<EventoEntity> {

        private TextField locationField;
        private CalendarTimeTextField timePicker, endPicker;

        private void fields() {
            Label cityLbl = new Label("Città");
            cityField = new MaterialTextField();
            cityField.setPromptText("Inserisci città di partenza");

            Label locationLbl = new Label("Presso: ");
            locationField = new MaterialTextField();
            locationField.setPromptText("Inserisci luogo");

            add(cityLbl, 0, 1);
            add(locationLbl, 0, 2);
            add(cityField, 1, 1);
            add(locationField, 1, 2);
        }

        private void pickers() {

            mainDatePicker = new DatePicker(LocalDate.now());
            timePicker = new CalendarTimeTextField();
            endPicker = new CalendarTimeTextField();

            add(new Label("Inizio: "), 0, 3);
            add(mainDatePicker, 1, 3);
            add(timePicker, 2, 3);
            add(new Label("Fino a: "), 3, 3);
            add(endPicker, 4, 3);
        }

        @Override
        void generate() {
            fields();
            pickers();
        }

        @Override
        EntityBuilder.Arguments harvest() {

            String location = locationField.getText();
            if (location == null || "".equals(location)) return null;

            LocalDate localDate = mainDatePicker.getValue();
            Calendar endCalendar = endPicker.getCalendar();
            if (localDate == null || endCalendar == null)
                return null;

            Instant endInstant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

            long endHour = endCalendar.getTime().getHours() * 3600000;
            long endMinute = endCalendar.getTime().getMinutes() * 60000;

            endInstant.plusMillis(endHour + endMinute);
            Timestamp endTime = Timestamp.from(endInstant);

            return EntityBuilder.Arguments.from(location, endTime);
        }

        @Override
        protected Timestamp getMainDate() {
            Timestamp base = super.getMainDate();
            Calendar calendar = timePicker.getCalendar();
            if (calendar == null) return base;

            long hour = calendar.getTime().getHours() * 3600000;
            long minute = calendar.getTime().getMinutes() * 60000;

            return new Timestamp(base.getTime() + hour + minute);
        }
    }

    private class OvernightForm extends OfferForm<PernottamentoEntity> {

        private TextField locationField;
        private DatePicker endPicker;
        private MaterialSpinner srvSpinner, strSpinner;

        private void fields() {

            cityField = new MaterialTextField();
            cityField.setPromptText("Inserisci città");

            locationField = new MaterialTextField();
            locationField.setPromptText("Inserisci luogo");

            add(new Label("Città"), 0, 1);
            add(new Label("Location"), 0, 2);
            add(cityField, 1, 1);
            add(locationField, 1, 2);
        }

        private void pickers() {

            mainDatePicker = new DatePicker(LocalDate.now());
            endPicker = new DatePicker(LocalDate.now());

            add(new Label("Inizio: "), 0, 3);
            add(mainDatePicker, 1, 3);
            add(new Label("Fino a: "), 2, 3);
            add(endPicker, 3, 3);
        }

        private void spinners() {

            srvSpinner = new MaterialSpinner(OfferInsertionView.this,
                    FXCollections.observableArrayList("Pensione completa", "Mezza pensione"));
            strSpinner = new MaterialSpinner(OfferInsertionView.this, 1, 5);

            add(new Label("Stars"), 0, 4);
            add(new Label("Service"), 0, 5);
            add(strSpinner, 1, 4);
            add(srvSpinner, 1, 5);
        }

        @Override
        void generate() {
            fields();
            pickers();
            spinners();
        }

        @Override
        EntityBuilder.Arguments harvest() {

            String location = locationField.getText();
            if (location == null || "".equals(location)) return null;

            String service = srvSpinner.getValue(),
                     stars = strSpinner.getValue();
            if (service == null || "".equals(service) ||
                    stars == null || "".equals(stars)) return null;

            LocalDate localDate = endPicker.getValue();

            if (localDate == null)
                return null;

            Instant checkOutInstant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Timestamp checkOut = Timestamp.from(checkOutInstant);

            return EntityBuilder.Arguments.from(location, service, stars, checkOut);
        }
    }
}
