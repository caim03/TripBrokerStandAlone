package view.material;

import controller.Constants;
import controller.command.Command;
import controller.command.DrawerHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import view.TripBrokerConsole;

public class NavigationDrawer extends VBox {

    public static final double WIDTH = 0.2;
    public static double ratio = 0.67;

    private double width;

    static ObservableList scout = FXCollections.<String>observableArrayList("Visualizza catalogo", "Inserisci offerta", "Logout"),
                          admin = FXCollections.<String>observableArrayList("Visualizza catalogo", "Approva pacchetto",  "Modifica politiche", "Gestione ruoli", "Aggiungi dipendente", "Andamento economico", "Logout"),
                          desig = FXCollections.<String>observableArrayList("Visualizza catalogo", "Componi pacchetto",  "Modifica pacchetto", "Logout"),
                          agent = FXCollections.<String>observableArrayList("Visualizza catalogo", "Organizza viaggio",  "Prenota viaggio", "Gestisci prenotazioni", "Vendi prodotto" ,"Logout");

    ListView<String> options;
    DrawerHandler handler;

    public NavigationDrawer() {

        width = Screen.getPrimary().getVisualBounds().getWidth() * NavigationDrawer.WIDTH;
        setPrefWidth(width);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: white; -fx-border-color: null");
    }

    public NavigationDrawer(String title) {

        this();

        double height = width * ratio;
        Canvas canvas = new Canvas(width, height);
        canvas.getGraphicsContext2D().setFill(Color.RED);
        canvas.getGraphicsContext2D().fillRect(0, 0, width, height);
        canvas.getGraphicsContext2D().setStroke(Color.WHITE);
        canvas.getGraphicsContext2D().strokeText(TripBrokerConsole.getGuestName(), 16, height - 16);

        getChildren().add(canvas);

        setOptions(title);
    }

    public void setOptions(String title) {

        if (Constants.scout.equals(title)) setOptions(NavigationDrawer.scout);
        else if (Constants.admin.equals(title)) setOptions(NavigationDrawer.admin);
        else if (Constants.desig.equals(title)) setOptions(NavigationDrawer.desig);
        else if (Constants.agent.equals(title)) setOptions(NavigationDrawer.agent);
    }

    public void setOptions(ObservableList<String> opts) {

        int size;
        if ((size = getChildren().size()) > 1) getChildren().remove(1, size);

        options = new ListView<>(opts);
        options.setCellFactory(param -> new DrawerCell());
        options.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            int index = newValue.intValue();
            if (index >= 0 && index < options.getItems().size()) {
                options.getSelectionModel().select(newValue.intValue());
                options.getSelectionModel().clearSelection();
            }
        });

        getChildren().add(options);
    }

    public void addCommands(Command... commands) {

        handler = new DrawerHandler(commands);
        options.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
    }
}
