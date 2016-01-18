package view.desig;

import com.jfoenix.controls.JFXTabPane;
import controller.Constants;
import controller.command.Command;
import controller.command.DeamonThread;
import controller.command.TransferRecordCommand;
import controller.strategy.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.scene.control.CalendarTimeTextField;
import model.DBManager;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;
import view.material.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class OffersTabPane extends JFXTabPane {

    private static String[] tabs = {Constants.travel, Constants.stay, Constants.event};
    private TransferRecordCommand command;

    public OffersTabPane(TransferRecordCommand command) {

        this.command = command;

        for (int i = 0; i < 3; ++i) {

            if (i == 0) getTabs().add(new SearchTab(tabs[0]));

            else {
                getTabs().add(new OfferTab(i));
            }
        }

        tabMinWidthProperty().bind(widthProperty().divide(3));
        tabMaxWidthProperty().bind(widthProperty().divide(3));

        getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue.equals(newValue)) return;

            ListView list = ((CustomTab) getTabs().get(newValue.intValue())).getListView();
            if (list != null && list.getItems().isEmpty()) list.refresh();
        });

        setStyle("-fx-background-color: white");
    }

    private SearchStrategy getSearchStrategy(RadioButton selectedToggle) {

        int pos = Integer.parseInt(selectedToggle.getId());

        if (pos == 0) return new FewerStopsSearchStrategy();
        else if (pos == 1) return new FasterSearchStrategy();
        else return new CheaperSearchStrategy();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new JFXTabPaneSkin(this);
    }

    abstract class CustomTab extends Tab {
        CustomTab(String title) { super(title); }
        abstract ListView getListView();
    }

    class SearchTab extends CustomTab {

        SearchTab(String title) {
            super(title);
            setContent(searchTab());
        }

        private Node searchTab() {

            TextField from = new MaterialTextField(), to = new MaterialTextField();
            DatePicker datePicker = new DatePicker(LocalDate.now());
            CalendarTimeTextField timePicker = new CalendarTimeTextField();

            RadioButton stopsRadio = new RadioButton("Con meno scali"),
                    fastestRadio = new RadioButton("Più veloci"),
                    cheapestRadio = new RadioButton("Più economici");

            ToggleGroup toggleGroup = new ToggleGroup();
            stopsRadio.setToggleGroup(toggleGroup);
            fastestRadio.setToggleGroup(toggleGroup);
            cheapestRadio.setToggleGroup(toggleGroup);

            stopsRadio.setSelected(true);

            stopsRadio.setId("0");
            fastestRadio.setId("1");
            cheapestRadio.setId("2");

            Button searchBtn = new FlatButton("Cerca");

            GridPane pane = new GridPane();
            pane.add(from, 0, 0);
            pane.add(datePicker, 1, 0);
            pane.add(timePicker, 2, 0);
            pane.add(to, 0, 1);
            pane.add(stopsRadio, 0, 2);
            pane.add(fastestRadio, 0, 3);
            pane.add(cheapestRadio, 0, 4);
            pane.add(searchBtn, 0, 5);

            pane.setVgap(2);

            GridPane secondPane = new GridPane();
            VBox box = new VBox(pane, secondPane);
            box.setPadding(new Insets(8));

            searchBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (!"".equals(from.getText()) && !"".equals(to.getText())) {

                    Timestamp timestamp = null;
                    if (datePicker.getValue() != null) {

                        timestamp = timePicker.getCalendar() != null ?
                                new Timestamp(
                                        Timestamp.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()
                                                + timePicker.getCalendar().getTime().getHours() * 3600000
                                                + timePicker.getCalendar().getTime().getMinutes() * 60000) :
                                Timestamp.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }

                    String fromText = from.getText();
                    BFSearchStrategy.Arrival start = new BFSearchStrategy.Arrival(fromText, timestamp),
                            end = new BFSearchStrategy.Arrival(to.getText(), null);

                    if (secondPane.getChildren().size() > 0) secondPane.getChildren().remove(0);
                    ProgressCircle progressCircle = new ProgressCircle();
                    secondPane.getChildren().add(progressCircle);

                    new DeamonThread(() -> {
                        List<BFSearchStrategy.Station> stations = getSearchStrategy((RadioButton) toggleGroup.getSelectedToggle()).
                                search(new BFSearchStrategy.Arrival[]{start, end});
                        Platform.runLater(() -> {
                            ListView listView;
                            boolean empty = stations == null;
                            if (!empty) {
                                listView = new ListView();
                                listView.setCellFactory(param -> new MultiDBCell());
                                for (SearchStrategy.Node node : stations) {
                                    listView.getItems().add(node);
                                }
                                listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    listView.getSelectionModel().clearSelection();
                                    if (newValue != null) {
                                        for (ViaggioEntity entity : ((BFSearchStrategy.Station) newValue).climbUp())
                                            command.execute(entity);
                                    }
                                });
                                GridPane.setHgrow(listView, Priority.ALWAYS);
                                secondPane.getChildren().add(listView);
                            }
                            secondPane.getChildren().remove(progressCircle);
                        });
                    }).start();
                }
            });

            return box;
        }

        @Override ListView getListView() { return null; }
    }

    class OfferTab extends CustomTab {

        private DBListView list;

        OfferTab(int i) {
            super(tabs[i]);
            setContent(generate(i));
        }

        private Node generate(int i) {

            list = new DBListView("from ProdottoEntity where tipo like '" + tabs[i] + "'");
            list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                command.execute(newValue); });

            Label lbl = new Label("Città:");
            TextField field = new MaterialTextField();

            DatePicker datePicker = new DatePicker(LocalDate.now());

            Button button = new FlatButton("Cerca");
            button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (!"".equals(field.getText())) {
                    list.getItems().clear();
                    list.getItems().add(AbstractEntity.getInvalidEntity());

                    String query = "where città like '" + field.getText() + "'";
                    Timestamp timestamp = Timestamp.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    query += " and dataInizio between ";
                    query += "str_to_date('";
                    query += new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
                    query += "', '%Y-%m-%d') and ";
                    query += "str_to_date('";
                    query += new SimpleDateFormat("yyyy-MM-dd").
                            format(new Timestamp(timestamp.getTime() + 24 * 3600000));
                    query += "', '%Y-%m-%d')";
                    final String finalQuery = query;
                    new DeamonThread(() -> {
                        DBManager.initHibernate();
                        List<OffertaEntity> buffer;
                        if (i == 1) buffer =
                                (List<OffertaEntity>) PernottamentoDaoHibernate.instance().
                                        getByCriteria(finalQuery);
                        else buffer =
                                (List<OffertaEntity>) EventoDaoHibernate.instance().
                                        getByCriteria(finalQuery);
                        DBManager.shutdown();

                        Platform.runLater(() -> {
                            list.getItems().clear();
                            if (buffer != null) list.getItems().addAll(buffer);
                        });
                    }).start();
                }
                else list.refresh();
            });

            GridPane pane = new GridPane();
            pane.add(lbl, 0, 0);
            pane.add(field, 1, 0);
            pane.add(datePicker, 2, 0);
            pane.add(button, 0, 1);

            VBox box = new VBox(pane, list);
            box.setPadding(new Insets(8));

            return box;
        }

        @Override ListView getListView() { return list; }
    }
}
