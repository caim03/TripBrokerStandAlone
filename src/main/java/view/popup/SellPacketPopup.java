package view.popup;

import controller.agent.SellController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.entityDB.PacchettoEntity;
import org.controlsfx.control.Notifications;
import view.material.*;

public class SellPacketPopup extends PopupDecorator {

    PacchettoEntity entity;
    private HBox box;
    private MaterialSpinner spinner;
    private Button button;

    public SellPacketPopup(PacketPopup base) {
        super(base);
        this.entity = base.getEntity();
    }

    public SellPacketPopup(PacchettoEntity entity) { this(new PacketPopup(entity)); }

    @Override
    protected Node decorate() {

        button = new FlatButton("Acquista");

        box = new HBox(button);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(16));

        return box;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && newValue instanceof LayerPane) {
                box.getChildren().remove(spinner);
                int qu = entity.retrieveQuantity();
                if (qu <= 0) {
                    box.getChildren().clear();
                    box.getChildren().add(new Label("Non disponibile"));
                }
                else {
                    spinner = new MaterialSpinner((LayerPane) newValue, 1, qu);
                    box.getChildren().add(0, spinner);

                    button.addEventFilter(MouseEvent.MOUSE_CLICKED,
                            event -> {

                                final int purchased[] = new int[1];
                                try { purchased[0] = Integer.parseInt(spinner.getValue()); }
                                catch (NumberFormatException e) { purchased[0] = 0; }

                                ProgressCircle circle = ProgressCircle.miniCircle();
                                box.getChildren().clear();
                                box.getChildren().add(circle);

                                new Thread(() -> {
                                    boolean result = false;
                                    try { result = SellController.handle(entity, purchased[0]); }
                                    catch (Exception e) {
                                        Platform.runLater(() -> {
                                            Notifications.create().
                                                    text(e.getMessage()).
                                                    showWarning();
                                            box.getChildren().clear();
                                            box.getChildren().add(button);
                                        });
                                        return;
                                    }

                                    if (result) {
                                        Platform.runLater(() -> {
                                            parent.hide();
                                            Notifications.create().text("Pacchetto acquistato x" + purchased[0]).show();
                                        });
                                    }
                                    else
                                        Platform.runLater(() -> {
                                        parent.hide();
                                        Notifications.create().text("Errore durante l'acquisto").showError();
                                    });
                                }).start();
                            });
                }
            }
        });
    }
}
