package view.material;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sun.misc.ASCIICaseInsensitiveComparator;

public class MaterialSpinner extends FlatButton {

    private LayerPane parent;
    private ListView<String> listView;
    private boolean shown = false;
    private AnchorPane mask;
    private Rectangle shape = new Rectangle(0.1, 0.1);
    private AnchorPane pane;
    private static Color details = Color.GRAY;

    public MaterialSpinner(LayerPane parent, int from, int to) {
        this(parent, generateNumberList(from, to));
    }

    private static ObservableList<String> generateNumberList(int from, int to) {
        ObservableList<String> obs = FXCollections.observableArrayList();
        int i;
        for (i = from; i <= to; ++i) obs.add(Integer.toString(i));

        return obs;
    }

    @Override
    protected Skin<?> createDefaultSkin() {

        Skin skin = super.createDefaultSkin();

        mask = new AnchorPane();
        mask.prefWidthProperty().bind(widthProperty());
        mask.prefHeightProperty().bind(heightProperty());

        Rectangle line = new Rectangle(0.1, 2, details);
        line.widthProperty().bind(mask.prefWidthProperty());

        Canvas triangle = new Canvas(5, 3);
        triangle.getGraphicsContext2D().setFill(details);
        triangle.getGraphicsContext2D().fillRect(0, 0, 5, 1);
        triangle.getGraphicsContext2D().fillRect(1, 1, 3, 1);
        triangle.getGraphicsContext2D().fillRect(2, 2, 1, 1);

        mask.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                AnchorPane.setLeftAnchor(triangle, newValue.doubleValue() - 6);
            }
        });
        mask.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                AnchorPane.setTopAnchor(triangle, newValue.doubleValue() / 2 - 1);
                AnchorPane.setTopAnchor(line, newValue.doubleValue() - 2);
            }
        });

        mask.getChildren().addAll(line, triangle);
        getChildren().add(0, mask);

        shape.setFill(Color.WHITE);
        shape.setEffect(Shadow.getStaticInstance());

        return skin;
    }

    public MaterialSpinner(LayerPane parent, ObservableList<String> obs) {

        this.parent = parent;

        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            event.consume();
            showList();
        });

        setStyle("-fx-text-fill: black");
        double max = 0;
        for (String str : obs) max = str.length() > max ? str.length() : max;
        max = max < 4 ? 4 : max;
        setMaxWidth(getFont().getSize() * max);
        setPrefWidth(getFont().getSize() * max * 0.75);

        listView = new ListView<>(obs);
        listView.maxWidthProperty().bind(widthProperty());
        obs.addListener((ListChangeListener<String>) c -> {
            c.next();
            double n = c.getList().size() > 5 ? 5 : c.getList().size();
            listView.maxHeightProperty().setValue(26 * n);
        });

        listView.setCellFactory((callback) -> new SpinnerCell());

        listView.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && shown && !newValue) { playExitAnimation(); }});

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && shown) {
                playExitAnimation();
                setText(newValue);
                getChildren().remove(mask);
                getChildren().add(0, mask);
            }
        });

        listView.setEffect(Shadow.getStaticInstance());
    }

    private void showList() {

        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: null");

        pane.prefWidthProperty().bind(parent.widthProperty());
        pane.prefHeightProperty().bind(parent.heightProperty());

        AnchorPane.setTopAnchor(listView, recursiveTrackingY(this));
        AnchorPane.setLeftAnchor(listView, recursiveTrackingX(this));

        String first = getText();
        ObservableList<String> items = listView.getItems();
        if ("".equals(first)) {

            items.remove("");
            items.sort(new ASCIICaseInsensitiveComparator());
            items.add(0, first);
        }
        else if (items.contains(first)) {

            items.remove("");
            int index = items.indexOf(first);
            items.remove(index);
            items.sort(new ASCIICaseInsensitiveComparator());
            items.add(0, first);
        }

        listView.getSelectionModel().clearSelection();

        AnchorPane.setTopAnchor(shape, recursiveTrackingY(this));
        AnchorPane.setLeftAnchor(shape, recursiveTrackingX(this));
        pane.getChildren().add(shape);

        parent.attach(listView);
        parent.pop();

        double w = listView.getWidth(), h = listView.getHeight();
        if (w <= 0) w = getWidth();
        if (h <= 0) h = getHeight() * listView.getItems().size();

        FadeTransition ft = new FadeTransition(Duration.millis(50), shape);
        ft.setByValue(0.0);
        ft.setToValue(1);

        Timeline timeline = new Timeline();
        KeyValue walue = new KeyValue(shape.widthProperty(), w);
        KeyValue halue = new KeyValue(shape.heightProperty(), h);
        KeyFrame wrame = new KeyFrame(Duration.millis(50), walue);
        KeyFrame hrame = new KeyFrame(Duration.millis(50), halue);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(wrame, hrame);

        timeline.setOnFinished(event -> {

            pane.getChildren().remove(shape);
            pane.getChildren().add(listView);
            parent.pop();
            parent.attach(pane);
            shape.setWidth(getWidth());
            shape.setHeight(getHeight());
        });

        parent.attach(pane);
        shown = true;
        new ParallelTransition(timeline, ft).playFromStart();
    }

    double recursiveTrackingX(Region e) {

        if (e.getParent() != null && !(e.getParent() instanceof LayerPane))
            return recursiveTrackingX((Region) e.getParent()) + e.getLayoutX();
        else return e.getLayoutX();
    }

    double recursiveTrackingY(Region e) {

        if (e.getParent() != null && !(e.getParent() instanceof LayerPane))
            return recursiveTrackingY((Region) e.getParent()) + e.getLayoutY();
        else return e.getLayoutY();
    }

    void playExitAnimation() {

        shown = false;
        
        Rectangle rectangle = new Rectangle(listView.getWidth(), listView.getHeight(), Color.WHITE);
        AnchorPane.setTopAnchor(rectangle, recursiveTrackingY(this));
        AnchorPane.setLeftAnchor(rectangle, recursiveTrackingX(this));
        rectangle.setEffect(Shadow.getStaticInstance());

        FadeTransition ft = new FadeTransition(Duration.millis(50), rectangle);
        ft.setByValue(0.0);
        ft.setToValue(1);

        Timeline timeline = new Timeline();
        KeyValue walue = new KeyValue(rectangle.widthProperty(), getWidth());
        KeyValue halue = new KeyValue(rectangle.heightProperty(), getHeight());
        KeyFrame wrame = new KeyFrame(Duration.millis(50), walue);
        KeyFrame hrame = new KeyFrame(Duration.millis(50), halue);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(wrame, hrame);

        timeline.setOnFinished(event -> {

            pane.getChildren().remove(rectangle);
            parent.pop();
        });

        pane.getChildren().remove(listView);
        pane.getChildren().add(rectangle);

        new ParallelTransition(timeline, ft).playFromStart();
    }

    public String getValue() {
        return getText();
    }

    public void setValue(String item) {
        setText(item);
        getChildren().remove(mask);
        getChildren().add(0, mask);
    }

    class SpinnerCell extends ListCell<String> {

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
                return;
            }

            Label txt = new Label(item);
            txt.setTextFill(Color.BLACK);

            GridPane cell = new GridPane();
            cell.setAlignment(Pos.CENTER);
            cell.widthProperty().addListener((observable, oldValue, newValue) -> {

                if (newValue != null && !newValue.equals(oldValue)) {

                    double w = getListView().widthProperty().getValue();

                    if (w < newValue.doubleValue()) {

                        listView.maxWidthProperty().setValue(newValue.doubleValue() + 32);
                        listView.resize(newValue.doubleValue() + 32, getListView().getHeight());
                        txt.resize(newValue.doubleValue(), txt.getHeight());
                    }
                }
            });

            cell.getChildren().add(txt);

            if ("".equals(item)) setFocusTraversable(false);

            setGraphic(cell);
        }
    }
}
