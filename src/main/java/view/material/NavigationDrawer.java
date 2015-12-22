package view.material;

import controller.Constants;
import controller.DrawerHandler;
import controller.command.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class NavigationDrawer extends VBox {

    public static final double WIDTH = 0.2;
    static double ratio = 0.67;

    static ObservableList scout = FXCollections.<String>observableArrayList("Inserisci offerta", "OPERATION 2",  "OPERATION 3", "Logout"),
                          admin = FXCollections.<String>observableArrayList("Visualizza catalogo", "Approva pacchetto",  "Modifica Politiche", "Logout"),
                          desig = FXCollections.<String>observableArrayList("Visualizza catalogo", "Componi pacchetto",  "OPERATION 3", "Logout"),
                          agent = FXCollections.<String>observableArrayList("Visualizza catalogo", "Organizza viaggio",  "Prenota viaggio", "Gestisci prenotazioni", "Logout");

    ListView<String> options;
    DrawerHandler handler;

    public NavigationDrawer() {

        double width = Screen.getPrimary().getVisualBounds().getWidth() * NavigationDrawer.WIDTH;
        setPrefWidth(width);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: white; -fx-border-color: null");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(width * ratio);
        imageView.setImage(new Image("gandalf.png"));
        getChildren().add(imageView);
    }

    public NavigationDrawer(String title) {

        this();
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
        options.minHeight(Double.MAX_VALUE);
        options.setPadding(new Insets(16, 16, 16, 16));

        getChildren().add(options);
    }

    public void addCommands(Command... commands) {

        handler = new DrawerHandler(commands);
        options.setOnMouseClicked(handler);
    }
}
