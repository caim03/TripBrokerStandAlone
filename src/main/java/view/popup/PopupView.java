package view.popup;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Factory Method for popup
public abstract class PopupView {

    protected String title;

    protected abstract Parent generatePopup();

    public void show(){
        Scene scene = new Scene(generatePopup());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(new Stage());

        stage.setTitle(title);
        stage.show();
    }
}
