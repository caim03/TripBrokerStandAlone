package view.agent;

import controller.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.PacchettoDaoHibernate;
import model.entityDB.PacchettoEntity;
import model.entityDB.OffertaEntity;
import view.material.*;
import view.popup.SellPacketPopup;
import view.popup.SellPopup;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class SellProductView extends LayerPane {

    private OfferOptions options;
    private MaterialSpinner spinner;
    private MaterialButton button;
    private ListView listView;

    public SellProductView() {

        spinner = new MaterialSpinner(this, FXCollections.<String>observableArrayList(Constants.travel,
                Constants.event, Constants.stay, Constants.packet));

        button = new ElevatedButton("Cerca");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(spinner, 0, 0, 5, 1);
        pane.add(button, 6, 0);

        options = new EmptyOptions();

        final VBox vBox = new VBox(pane, options);

        button.setOnMouseClicked(event -> {

            vBox.getChildren().remove(listView);

            String query = options.getQuery();
            if (query == null) {
                listView = new ListView();
                return;
            }

            if (options instanceof PacketOptions) {
                listView = new ListView();
                listView.setCellFactory(callback -> new DBCell());
                listView.getItems().add(null);
                new Thread(() -> {
                    DBManager.initHibernate();
                    List<PacchettoEntity> list =
                            (List<PacchettoEntity>) PacchettoDaoHibernate.instance().getByCriteria(query);
                    DBManager.shutdown();
                    Platform.runLater(() -> {
                        listView.getItems().clear();
                        listView.getItems().addAll(list);
                    });
                }).start();
            }

            else {
                listView = new DBListView(query);
                listView.refresh();
            }

            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) return;
                listView.getSelectionModel().clearSelection();
                if (newValue instanceof PacchettoEntity)
                    new MaterialPopup(this, new SellPacketPopup((PacchettoEntity) newValue)).show();
                else
                    new MaterialPopup(this, new SellPopup((OffertaEntity) newValue)).show();
            });

            vBox.getChildren().add(listView);
        });

        spinner.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            vBox.getChildren().remove(options);
            vBox.getChildren().remove(listView);

            if (Constants.travel.equals(newValue)) options = new TravelOptions();
            else if (Constants.event.equals(newValue)) options = new EventOptions();
            else if (Constants.stay.equals(newValue)) options = new OvernightOptions();
            else if (Constants.packet.equals(newValue)) options = new PacketOptions();
            else options = new EmptyOptions();

            vBox.getChildren().add(options);
            options.generate();
        });

        attach(vBox);
    }

    abstract private class OfferOptions extends GridPane {

        protected TextField cityField;
        protected CheckBox dateCheck;
        protected DatePicker mainDatePicker;

        protected OfferOptions() {
            setStyle("-fx-background-color: white");
            setHgap(25);
            setVgap(8);
            setPadding(new Insets(25));
        }

        protected void fields() {

            cityField = new MaterialTextField();
            add(cityField, 1, 0);
        }

        protected void pickers() {

            mainDatePicker = new DatePicker(LocalDate.now());
            add(mainDatePicker, 3, 0);
        }

        protected void checkbox() {

            dateCheck = new CheckBox();
            add(dateCheck, 2, 0);

            dateCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.equals(oldValue))
                    mainDatePicker.setDisable(!newValue);
            });
            dateCheck.setSelected(false);
            mainDatePicker.setDisable(true);
        }

        protected void generate() {

            fields();
            pickers();
            checkbox();
        }
        abstract String getQuery();
    }

    private class TravelOptions extends OfferOptions {

        private TextField destination;
        private CheckBox arrivalCheck;
        private DatePicker arrivalDatePicker;

        @Override
        protected void fields() {

            super.fields();

            destination = new MaterialTextField();

            cityField.setPromptText("Inserisci città di partenza");
            destination.setPromptText("Inserisci destinazione");

            add(new Label("Partenza"), 0, 0);
            add(new Label("Arrivo"), 0, 1);
            add(destination, 1, 1);
        }

        @Override
        protected void pickers() {
            super.pickers();

            arrivalDatePicker = new DatePicker(LocalDate.now());
            add(arrivalDatePicker, 3, 1);
        }

        @Override
        protected void checkbox() {
            super.checkbox();

            arrivalCheck = new CheckBox();
            add(arrivalCheck, 2, 1);

            arrivalCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.equals(oldValue))
                    arrivalDatePicker.setDisable(!newValue);
            });
            arrivalCheck.setSelected(false);
            arrivalDatePicker.setDisable(true);
        }

        @Override
        String getQuery() {
            String query = "from ViaggioEntity", where = "",
                   dep = "", arr = "", depTime = "", arrTime = "";

            boolean clause = false;
            String buf;

            if ((buf = cityField.getText()) != null && !"".equals(buf)) {
                clause = true;
                where = " where ";
                dep += "città like '" + buf + "'";
            }
            if ((buf = destination.getText()) != null && !"".equals(buf)) {
                if (clause) arr += " and ";
                else {
                    where = " where ";
                    clause = true;
                }
                arr += "destinazione like '" + buf + "'";
            }
            if (dateCheck.isSelected() && mainDatePicker.getValue() != null) {

                if (clause) depTime += " and ";
                else {
                    where = " where ";
                    clause = true;
                }

                Instant instant = mainDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
                Timestamp from = Timestamp.from(instant),
                        to = Timestamp.from(instant.plus(Duration.ofHours(24)));
                depTime += "dataInizio between ";
                depTime += "str_to_date('";
                depTime += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(from);
                depTime += "', '%Y-%m-%d %k:%i') and ";
                depTime += "str_to_date('";
                depTime += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(to);
                depTime += "', '%Y-%m-%d %k:%i')";
            }
            if (arrivalCheck.isSelected() && arrivalDatePicker.getValue() != null) {

                if (clause) arrTime += " and ";
                else where = " where ";

                Instant instant = arrivalDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
                Timestamp from = Timestamp.from(instant),
                        to = Timestamp.from(instant.plus(Duration.ofHours(24)));
                arrTime += "dataInizio between ";
                arrTime += "str_to_date('";
                arrTime += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(from);
                arrTime += "', '%Y-%m-%d %k:%i') and ";
                arrTime += "str_to_date('";
                arrTime += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(to);
                arrTime += "', '%Y-%m-%d %k:%i')";
            }

            return query + where + dep + arr + depTime + arrTime;
        }
    }

    private class EventOptions extends OfferOptions {

        @Override
        protected void fields() {

            super.fields();
            cityField.setPromptText("Inserisci città");
            add(new Label("Città"), 0, 0);
        }

        @Override
        String getQuery() {
            String query = "from EventoEntity", where = "", city = "", date = "";

            boolean clause = false;
            String buf;

            if ((buf = cityField.getText()) != null && !"".equals(buf)) {
                clause = true;
                where = " where ";
                city += "città like '" + buf + "'";
            }

            if (dateCheck.isSelected() && mainDatePicker.getValue() != null) {

                if (clause) date += " and ";
                else where = " where ";

                Instant instant = mainDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
                Timestamp from = Timestamp.from(instant),
                        to = Timestamp.from(instant.plus(Duration.ofHours(24)));
                date += "dataInizio between ";
                date += "str_to_date('";
                date += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(from);
                date += "', '%Y-%m-%d %k:%i') and ";
                date += "str_to_date('";
                date += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(to);
                date += "', '%Y-%m-%d %k:%i')";
            }

            return query + where + city + date;
        }
    }

    private class OvernightOptions extends OfferOptions {

        @Override
        protected void fields() {

            super.fields();
            cityField.setPromptText("Inserisci città");
            add(new Label("Città"), 0, 0);
        }

        @Override
        String getQuery() {
            String query = "from PernottamentoEntity", where = "", city = "", date = "";

            boolean clause = false;
            String buf;

            if ((buf = cityField.getText()) != null && !"".equals(buf)) {
                clause = true;
                where = " where ";
                city += "città like '" + buf + "'";
            }

            if (dateCheck.isSelected() && mainDatePicker.getValue() != null) {

                if (clause) date += " and ";
                else where = " where ";

                Instant instant = mainDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
                Timestamp from = Timestamp.from(instant),
                        to = Timestamp.from(instant.plus(Duration.ofHours(24)));
                date += "dataInizio between ";
                date += "str_to_date('";
                date += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(from);
                date += "', '%Y-%m-%d %k:%i') and ";
                date += "str_to_date('";
                date += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(to);
                date += "', '%Y-%m-%d %k:%i')";
            }

            return query + where + city + date;
        }
    }

    private class PacketOptions extends OfferOptions {

        @Override
        protected void fields() {
            super.fields();
            cityField.setPromptText("Cerca per nome...");
            add(new Label("Pacchetto"), 0, 0);
        }

        @Override protected void pickers() { }
        @Override protected void checkbox() { }
        @Override String getQuery() {
            String query = "where stato = 1";

            String buffer = cityField.getText();
            if (buffer != null && !"".equals(buffer))
                query += " and nome like '%" + cityField.getText() + "%'";

            return query;
        }
    }

    private class EmptyOptions extends OfferOptions {

        @Override protected void generate() { }
        @Override String getQuery() { return null; }
    }
}