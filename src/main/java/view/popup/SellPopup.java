package view.popup;

import controller.agent.SellController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.entityDB.OffertaEntity;
import org.controlsfx.control.Notifications;
import view.material.*;

public class SellPopup extends PopupDecorator {

    private OffertaEntity entity;
    private Button sellBtn;
    private Spinner<Integer> quantity;
    private GridPane pane;
    private boolean available;

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

        if (available = entity.getQuantità() - entity.getPrenotazioni() > 0) {
            Label quantityLabel = new Label("Quantità:");
            quantity = new Spinner<>(1, entity.getQuantità() - entity.getPrenotazioni(), 1);
            sellBtn = new FlatButton("Vendi");

            pane.add(quantityLabel, 0, 0);
            pane.add(quantity, 1, 0);
            pane.add(sellBtn, 2, 0);
        }

        pane.setHgap(4);

        return pane;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        /** @param MaterialPopup; set a material popup as a parent of popup **/

        super.setParent(parent);

        if (!available) return;

        this.parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && newValue instanceof LayerPane) {

                sellBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    ProgressCircle progressCircle = new ProgressCircle();
                    pane.add(progressCircle, 1, 1);
                    progressCircle.start();

                    new Thread(() -> {
                        int qu = quantity.getValue();
                        boolean result = false;
                        try { result = SellController.handle(entity, qu); }
                        catch (Exception e) {
                            Platform.runLater(()-> Notifications.create().text(e.getMessage()).showWarning());
                            return;
                        }
                        if (result) {
                            Platform.runLater(()-> {
                                Notifications.create().title("Offerta acquistata").text("L'offerta è stata acquistata con successo").show();
                                parent.hide();
                            });
                        }
                        else {
                            Platform.runLater(() -> {
                                Notifications.create().title("Acquisto fallito").text("L'acquisto dell'offerta è fallito, riprova successivamente").showError();
                                parent.hide();
                            });
                        }
                    }).start();
                });
            }
        });
    }
}
