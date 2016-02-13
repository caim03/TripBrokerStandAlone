package view.popup;

import controller.Constants;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.DBManager;
import model.dao.DAO;
import model.dao.OffertaDaoHibernate;
import model.dao.StatusDaoHibenate;
import model.entityDB.PacchettoEntity;
import model.entityDB.StatusEntity;
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

                                int purchased;
                                try { purchased = Integer.parseInt(spinner.getValue()); }
                                catch (NumberFormatException e) {
                                    Notifications.create().text("Seleziona una quantità").showWarning();
                                    return;
                                }

                                ProgressCircle circle = ProgressCircle.miniCircle();
                                box.getChildren().clear();
                                box.getChildren().add(circle);

                                new Thread(() -> {
                                    try {
                                        DBManager.initHibernate();
                                        int i;
                                        for (i = 0; i < entity.retrieveOffers().size(); ++i) {
                                            entity.retrieveOffers().get(i).
                                                    setQuantità(entity.retrieveOffers().get(i).getQuantità() - purchased);

                                            OffertaDaoHibernate.instance().update(entity.retrieveOffers().get(i));
                                        }
                                        DAO dao = StatusDaoHibenate.getInstance();
                                        StatusEntity entries = (StatusEntity) dao.getById(Constants.entries);
                                        entries.update(Math.round(entity.getPrezzo() * qu * 100) / 100.0);
                                        dao.update(entries);
                                    }
                                    catch (Exception e) {
                                        Notifications.create().
                                                text("Errore - non è stato possibile effettuare l'acquisto").
                                                showError();
                                    }
                                    finally { DBManager.shutdown(); }

                                    Platform.runLater(() -> {
                                        parent.hide();
                                        Notifications.create().text("Pacchetto acquistato x" + purchased).show();
                                    });
                                }).start();
                            });
                }
            }
        });
    }
}
