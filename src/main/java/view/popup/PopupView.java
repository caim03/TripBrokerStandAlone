package view.popup;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.material.MaterialPopup;

// Factory Method for popup
public abstract class PopupView extends Pane {

    protected MaterialPopup parent;
    public void setParent(MaterialPopup parent) { this.parent = parent; }

    protected String title;
    protected abstract Parent generatePopup();
}
