package controller;

import controller.command.Command;
import javafx.collections.ListChangeListener;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.NumberLabel;

import java.util.Date;
import java.util.List;

public class PacketOverseer implements ListChangeListener<AbstractEntity> {

    NumberLabel labels[];

    public PacketOverseer(NumberLabel... labels) {

        this.labels = labels;
    }

    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {

        c.next();

        if (c.wasAdded()) {

            int len = c.getAddedSize(), size = c.getList().size();
            System.out.println("SIZE " + size);
            List added = c.getAddedSubList();
            for (int i = 0; i < len; ++i) {

                OffertaEntity newEntity = (OffertaEntity) added.get(i);

                int pos = size - len + i;

                if (pos > 0) {

                    OffertaEntity prevEntity = ((PacketList)c.getList()).getPrevious();

                    if (!checkLocation(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Last location does not match new starting location!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    if (checkDate(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Dates do not match!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    OffertaEntity entity = (OffertaEntity) c.getList().get(pos - 1);
                    String city;
                    if (entity instanceof ViaggioEntity) city = ((ViaggioEntity) entity).getDestinazione();
                    else city = entity.getCittà();

                    String newCity = (newEntity).getCittà();

                    if (!city.equals(newCity)) {
                    }
                }

                for (NumberLabel lbl : labels) lbl.updateNumber(newEntity.getPrezzo());
            }
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
