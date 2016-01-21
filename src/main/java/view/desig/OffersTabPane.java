package view.desig;

import com.jfoenix.controls.JFXTabPane;
import controller.Constants;
import controller.command.DeamonThread;
import controller.command.TransferRecordCommand;
import controller.strategy.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.CalendarTimeTextField;
import model.DBManager;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;
import view.material.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class OffersTabPane extends JFXTabPane {

    private static String[] tabs = {Constants.travel, Constants.stay, Constants.event};
    private TransferRecordCommand command;

    public OffersTabPane(TransferRecordCommand command) {

        this.command = command;

        for (int i = 0; i < 3; ++i) {
            if (i == 0) getTabs().add(new SearchTab(tabs[0]));
            else {
                System.out.println("NUMBER " + i);
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

    @Override protected Skin<?> createDefaultSkin() {
        return new JFXTabPaneSkin(this);
    }

    abstract class CustomTab extends Tab {

        protected VBox root = new VBox();
        protected GridPane fieldsGrid = new GridPane();
        protected ListView listView;
        protected TextField field;
        protected CheckBox checkBox;
        protected DatePicker datePicker;

        CustomTab(String title) {
            super(title);
            generate();
            setContent(root);
        }

        abstract ListView getListView() ;
        protected abstract void generate() ;
        protected void root() {
            grid();
            root.setPadding(new Insets(0, 8, 8, 8));
            root.prefHeightProperty().bind(OffersTabPane.this.prefHeightProperty().subtract(prefHeightProperty()));
        }
        protected void grid() {
            fieldsGrid.setVgap(4);
            fieldsGrid.setPadding(new Insets(8, 0, 8, 0));
        }
    }

    class SearchTab extends CustomTab {

        private TextField to;
        private GridPane listGrid;
        private CalendarTimeTextField timePicker;
        private ToggleGroup toggleGroup;

        SearchTab(String title) { super(title); }

        @Override
        protected void generate() {
            fields();
            pickers();
            checkBox();
            radios();
            button();
            listView();
            root();
        }

        private void fields() {
            field = new MaterialTextField();
            to = new MaterialTextField();

            fieldsGrid.add(field, 0, 0);
            fieldsGrid.add(to, 0, 1);
        }

        private void pickers() {
            datePicker = new DatePicker(LocalDate.now());
            timePicker = new CalendarTimeTextField();

            datePicker.setDisable(true);
            timePicker.setDisable(true);

            fieldsGrid.add(datePicker, 2, 0);
            fieldsGrid.add(timePicker, 3, 0);
        }

        private void checkBox() {
            checkBox = new CheckBox();

            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override public void changed(ObservableValue<? extends Boolean> observable,
                                              Boolean oldValue, Boolean newValue) { set(!newValue); }
                private void set(boolean disable) {
                    datePicker.setDisable(disable);
                    timePicker.setDisable(disable); } });

            fieldsGrid.add(checkBox, 1, 0);
        }

        private void radios() {
            RadioButton stopsRadio = new RadioButton("Con meno scali"),
                        fastestRadio = new RadioButton("Più veloci"),
                        cheapestRadio = new RadioButton("Più economici");

            toggleGroup = new ToggleGroup();
            stopsRadio.setToggleGroup(toggleGroup);
            fastestRadio.setToggleGroup(toggleGroup);
            cheapestRadio.setToggleGroup(toggleGroup);

            stopsRadio.setSelected(true);

            stopsRadio.setId("0");
            fastestRadio.setId("1");
            cheapestRadio.setId("2");

            fieldsGrid.add(stopsRadio, 0, 2);
            fieldsGrid.add(fastestRadio, 0, 3);
            fieldsGrid.add(cheapestRadio, 0, 4);
        }

        private Timestamp getTimestamp() {
            if (checkBox.isSelected() && datePicker.getValue() != null) {
                Timestamp timestamp = Timestamp.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (timePicker.getCalendar() != null)
                    timestamp = new Timestamp(
                              timestamp.getTime()
                            + timePicker.getCalendar().getTime().getHours() * 3600000
                            + timePicker.getCalendar().getTime().getMinutes() * 60000);

                return timestamp;
            }
            return null;
        }

        private boolean goAhead() { return !"".equals(field.getText()) && !"".equals(to.getText()); }

        private Node circle() {
            ProgressCircle progressCircle = new ProgressCircle();
            listGrid.getChildren().add(progressCircle);
            return progressCircle;
        }

        private void showResults(List<BFSearchStrategy.Station> stations) {

            listView.getItems().clear();
            boolean empty = stations == null;
            if (!empty) {
                for (SearchStrategy.Node node : stations) listView.getItems().add(node);

                listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    listView.getSelectionModel().clearSelection();
                    if (newValue != null) {
                        for (ViaggioEntity entity : ((BFSearchStrategy.Station) newValue).climbUp())
                            command.execute(entity);
                    }
                });
                listGrid.getChildren().add(listView);
            }
        }

        private void button() {
            Button searchBtn = new ElevatedButton("Cerca");
            fieldsGrid.add(searchBtn, 0, 5);

            searchBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (goAhead()) {
                    String fromText = field.getText();
                    BFSearchStrategy.Arrival start = new BFSearchStrategy.Arrival(fromText, getTimestamp()),
                            end = new BFSearchStrategy.Arrival(to.getText(), null);

                    listGrid.getChildren().clear();
                    Node circle = circle();

                    new DeamonThread(() -> {
                        List<BFSearchStrategy.Station> stations = getSearchStrategy().
                                search(new BFSearchStrategy.Arrival[]{start, end});
                        Platform.runLater(() -> {
                            showResults(stations);
                            listGrid.getChildren().remove(circle);
                        });
                    }).start();
                }
            });

        }

        private void listView() {
            listView = new ListView();
            listView.setCellFactory(param -> new MultiDBCell());
            GridPane.setHgrow(listView, Priority.ALWAYS);
            GridPane.setVgrow(listView, Priority.ALWAYS);

            listGrid = new GridPane();
            listGrid.getChildren().add(listView);
        }

        @Override
        protected void root() {
            super.root();
            root.getChildren().addAll(fieldsGrid, listGrid);
        }

        private SearchStrategy getSearchStrategy() {

            int pos = Integer.parseInt(((RadioButton) toggleGroup.getSelectedToggle()).getId());

            if (pos == 0) return new FewerStopsSearchStrategy();
            else if (pos == 1) return new FasterSearchStrategy();
            else return new CheaperSearchStrategy();
        }

        @Override ListView getListView() { return null; }
    }

    class OfferTab extends CustomTab {

        private int position;

        OfferTab(int i) {
            super(tabs[i]);
            position = i;
            listView();
            root();
        }

        @Override
        protected void generate() {
            fields();
            checkBox();
            picker();
            button();
        }

        @Override
        protected void root() {
            super.root();
            root.getChildren().addAll(fieldsGrid, listView);
        }

        private void listView() {

            System.out.println("LISTVIEW " + tabs[position] + " " + position);
            listView = new DBListView("from ProdottoEntity where tipo like '" + tabs[position] + "'");
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                command.execute((AbstractEntity) newValue));
        }

        private void fields() {
            Label lbl = new Label("Città:");
            field = new MaterialTextField();

            fieldsGrid.add(lbl, 0, 0);
            fieldsGrid.add(field, 1, 0);
        }

        private void picker() {
            datePicker = new DatePicker(LocalDate.now());
            datePicker.setDisable(true);
            fieldsGrid.add(datePicker, 3, 0);
        }

        private void checkBox() {
            checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override public void changed(ObservableValue<? extends Boolean> observable,
                                              Boolean oldValue, Boolean newValue) { set(!newValue); }
                private void set(boolean disable) { datePicker.setDisable(disable); } });

            fieldsGrid.add(checkBox, 2, 0);
        }

        private String query() {

            String query = "where città like '" + field.getText() + "'";

            if (checkBox.isSelected() && datePicker.getValue() != null) {
                Timestamp timestamp =
                        Timestamp.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                query += " and dataInizio between ";
                query += "str_to_date('";
                query += new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
                query += "', '%Y-%m-%d') and ";
                query += "str_to_date('";
                query += new SimpleDateFormat("yyyy-MM-dd").
                        format(new Timestamp(timestamp.getTime() + 24 * 3600000));
                query += "', '%Y-%m-%d')";
            }

            System.out.println(query);
            return query;
        }

        private void button() {
            Button button = new ElevatedButton("Cerca");
            button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (!"".equals(field.getText())) {
                    listView.getItems().clear();
                    listView.getItems().add(AbstractEntity.getInvalidEntity());

                    final String finalQuery = query();
                    new DeamonThread(() -> {
                        DBManager.initHibernate();
                        List<OffertaEntity> buffer;
                        if (position == 1) buffer =
                                (List<OffertaEntity>) PernottamentoDaoHibernate.instance().
                                        getByCriteria(finalQuery);
                        else buffer =
                                (List<OffertaEntity>) EventoDaoHibernate.instance().
                                        getByCriteria(finalQuery);
                        DBManager.shutdown();

                        Platform.runLater(() -> {
                            listView.getItems().clear();
                            if (buffer != null) listView.getItems().addAll(buffer);
                        });
                    }).start();
                }
                else listView.refresh();
            });

            fieldsGrid.add(button, 0, 1);
        }

        @Override ListView getListView() { return listView; }
    }
}
