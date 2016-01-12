package view.popup;

import controller.SellController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.entityDB.OffertaEntity;
import org.controlsfx.control.Notifications;
import view.material.*;


public class SellPopup extends PopupView {

    private PopupView popupView;
    private OffertaEntity entity;
    private Button sellBtn;
    private MaterialSpinner quantity;

    public SellPopup(PopupView popupView, OffertaEntity entity) {
        /** @param: PopupView; the popup to which must be applied pattern decorator
         *  @param: OffertaEntity; the offer that must be purchased and displayed in popup **/

        this.popupView = popupView;
        this.entity = entity;

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {
        /** @result: Parent; the base class for all nodes that have children in the scene graph. **/

        popupView.generatePopup();
        return generateItems();
    }

    /** Method to do decorator pattern **/
    private Parent generateItems() {
        /** @result: Parent; pane to add in children popup **/

        Label quantityLabel = new Label("Quantità:");
        sellBtn = new FlatButton("Vendi");

        popupView.pane.add(quantityLabel, 0, popupView.getRow() + 1);
        popupView.pane.add(sellBtn, 2, popupView.getRow() + 1);

        popupView.pane.setAlignment(Pos.CENTER);
        popupView.pane.setHgap(4);

        return popupView.pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        /** @param: MaterialPopup; set a material popup as a parent of popup **/

        super.setParent(parent);

        this.parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && newValue instanceof LayerPane) {

                quantity = new MaterialSpinner((LayerPane) newValue, 1, entity.getQuantità());
                popupView.pane.add(quantity, 1, popupView.getRow() + 1);

                sellBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ProgressCircle progressCircle = new ProgressCircle();
                        popupView.pane.add(progressCircle, 1, popupView.getRow() + 2);
                        progressCircle.start();

                        new Thread(() -> {
                            if (!SellController.handle(entity, quantity.getValue())) {
                                Platform.runLater(()-> {
                                    Notifications.create().title("Acquisto fallito").text("L'acquisto dell'offerta è fallito, riprova successivamente").show();
                                    parent.hide();
                                });
                            }
                            else {
                                Platform.runLater(() -> {
                                    Notifications.create().title("Offerta acquistata").text("L'offerta è stata acquistata con successo").show();
                                    parent.hide();
                                });
                            }
                        }).start();
                    }
                });
            }
        });
    }
}
