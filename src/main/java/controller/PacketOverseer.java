package controller;

import javafx.collections.ListChangeListener;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.NumberLabel;

import java.util.Date;
import java.util.List;

public class PacketOverseer implements ListChangeListener<AbstractEntity> {

    NumberLabel labels[];
    private static long acceptableDelay = 12 * 3600000;

    public PacketOverseer(NumberLabel... labels) {

        this.labels = labels;
    }

    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {

        c.next();

        if (c.wasAdded()) {

            int len = c.getAddedSize(), size = c.getList().size();

            List added = c.getAddedSubList();

            for (int i = 0; i < len; ++i) {

                if (!(added.get(i) instanceof OffertaEntity)) continue;

                OffertaEntity newEntity = (OffertaEntity) added.get(i);

                int pos = size - len + i;

                if (pos > 0) {

                    OffertaEntity prevEntity = ((PacketList) c.getList()).getPrevious(pos);

                    if (!checkLocation(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Last location does not match new starting location!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    if (!checkDate(prevEntity, newEntity)) {

                        Notifications.create().text("Ops! Dates do not match!").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }
                }

                for (NumberLabel lbl : labels) lbl.updateNumber(newEntity.getPrezzo());
            }
        }
    }

    private boolean checkDate(OffertaEntity previous, OffertaEntity next) {

        Date firstDate, secondDate = next.getDataInizio();
        boolean result;

        if (previous instanceof ViaggioEntity) {

            firstDate = ((ViaggioEntity) previous).getDataArrivo();

            if (next instanceof PernottamentoEntity)
                result = firstDate.getTime() - firstDate.getHours() * 3600000 - firstDate.getMinutes() * 60000 == secondDate.getTime();

            else
                result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        else if (previous instanceof PernottamentoEntity) {

            firstDate = ((PernottamentoEntity)previous).getDataFinale();
            Date firstDateEnd = new Date(firstDate.getTime() + 3600000 * 23 + 60000 * 59);

            if (next instanceof EventoEntity) {
                Date zeroDate = previous.getDataInizio();
                Date thirdDate = ((EventoEntity) next).getDataFine();
                result = secondDate.after(zeroDate) && thirdDate.before(firstDateEnd);
            }
            else if (next instanceof ViaggioEntity)
                result = secondDate.after(firstDate) && firstDateEnd.after(secondDate);
            else
                result = firstDate.equals(secondDate);
        }

        else {
            firstDate = previous.getDataInizio();
            result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        System.out.println("RESULT " + result);
        return result;
    }

    boolean checkLocation(OffertaEntity previous, OffertaEntity next) {

        String city;
        if (previous instanceof ViaggioEntity) city = ((ViaggioEntity) previous).getDestinazione();
        else city = previous.getCittà();

        return city.equals(next.getCittà());
    }
}
