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
            System.out.println("LEN " + len);
            List added = c.getAddedSubList();
            for (int i = 0; i < len; ++i) {

                OffertaEntity newEntity = (OffertaEntity) added.get(i);
                System.out.println("OFFER");

                int pos = size - len + i;

                if (pos > 0) {

                    OffertaEntity prevEntity = ((PacketList) c.getList()).getPrevious();

                    if (!checkLocation(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Last location does not match new starting location!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    System.out.println("OK");

                    if (checkDate(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Dates do not match!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    System.out.println("OI DUMBASS");
                }

                qu = newEntity.getQuantità();

                if (qu < 10) {

                    System.out.println("NO COUNTRY FOR OLD MEN");
                    c.getList().remove(pos, size);

                    Notifications.create().text("Insufficient number of tickets to assemble a group trip!").showWarning();
                    return;
                } else if (max < qu) max = qu;
            }

            final int result = max;

            Platform.runLater(() -> view.refreshSpinners(result));
        }
    }

    private boolean checkDate(OffertaEntity previous, OffertaEntity next) {

        Date firstDate, secondDate;

        if (previous instanceof ViaggioEntity) {
            firstDate = ((ViaggioEntity) previous).getDataArrivo();
        }
        else if (previous instanceof PernottamentoEntity) {
            firstDate = ((PernottamentoEntity)previous).getDataFinale();
        }
        else firstDate = previous.getDataInizio();

        secondDate = next.getDataInizio();

        return secondDate.after(firstDate);
    }

    boolean checkLocation(OffertaEntity previous, OffertaEntity next) {

        String city;
        if (previous instanceof ViaggioEntity) city = ((ViaggioEntity) previous).getDestinazione();
        else city = previous.getCittà();

        return city.equals(next.getCittà());
    }
}
