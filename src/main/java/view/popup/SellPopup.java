package view.popup;

import controller.agent.SellController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.entityDB.OffertaEntity;
import org.controlsfx.control.Notifications;
import view.material.*;

public class SellPopup extends PopupDecorator {

    private OffertaEntity entity;
    private Button sellBtn;
    private MaterialSpinner quantity;
    private GridPane pane;

    public SellPopup(OfferPopup popupView) {
        super(popupView);
        this.entity = popupView.getEntity();
    }

    public SellPopup(OffertaEntity entity) { this(OfferPopup.getSellingPopup(entity)); }

    @Override
    protected Node decorate() {
        return generateItems();
    }

    /** Method to do decorator pattern **/
    private Parent generateItems() {
        /** @result: Parent; pane to add in children popup **/

        pane = new GridPane();

        Label quantityLabel = new Label("Quantità:");
        sellBtn = new FlatButton("Vendi");

        pane.add(quantityLabel, 0, 0);
        pane.add(sellBtn, 2, 0);

        pane.setHgap(4);

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        /** @param MaterialPopup; set a material popup as a parent of popup **/

        super.setParent(parent);

        this.parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && newValue instanceof LayerPane) {

                quantity = new MaterialSpinner((LayerPane) newValue, 1, entity.getQuantità());
                pane.add(quantity, 1, 0);

                sellBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    ProgressCircle progressCircle = new ProgressCircle();
                    pane.add(progressCircle, 1, 1);
                    progressCircle.start();

                    new Thread(() -> {
                        int qu;
                        try { qu = Integer.parseInt(quantity.getValue()); }
                        catch (NumberFormatException e) {
                            e.printStackTrace();
                            Platform.runLater(() -> Notifications.create().
                                    text("Selezionare una quantità").showWarning());
                            return;
                        }

                        if (!SellController.handle(entity, qu)) {
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
                });
            }
        });
    }
}
