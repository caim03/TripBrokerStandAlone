package controller;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Spinner;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import org.controlsfx.control.Notifications;
import view.GroupTripFormView;
import view.desig.PacketList;

import java.util.Date;
import java.util.List;

public class GroupTripOverseer implements ListChangeListener<AbstractEntity> {

    GroupTripFormView view;

    public GroupTripOverseer(GroupTripFormView view) {

        this.view = view;
    }

    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {

        c.next();

        if (c.wasAdded()) {

            int len = c.getAddedSize(), size = c.getList().size(), max = 0, qu;
            List added = c.getAddedSubList();

            for (int i = 0; i < len; ++i) {

                OffertaEntity newEntity = (OffertaEntity) added.get(i);
                int pos = size - len + i;

                qu = newEntity.getQuantità();

                if (qu < 10) {

                    c.getList().remove(pos, size);

                    Notifications.create().text("Il numero di biglietti disponibili è insufficiente per formare un viaggio di gruppo").showWarning();
                    return;
                } else if (max < qu) max = qu;
            }

            final int result = max;

            Platform.runLater(() -> view.refreshSpinners(result));
        }
    }
}
