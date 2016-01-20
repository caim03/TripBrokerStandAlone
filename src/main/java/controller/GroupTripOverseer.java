package controller;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Spinner;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.GroupTripFormView;
import view.desig.OffersTabPane;
import view.desig.PacketList;
import view.material.NumberLabel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class GroupTripOverseer implements ListChangeListener<AbstractEntity> {

    GroupTripFormView view;
    private static long acceptableDelay = 12 * 3600000;
    NumberLabel labels[]; //Price bounds Labels, to be updated on Offer acceptance

    public GroupTripOverseer(GroupTripFormView view) { this.view = view; }
    public void subscribe(NumberLabel... labels) { this.labels = labels; }

    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {

        c.next();

        if (c.wasAdded()) {

            int len = c.getAddedSize(), size = c.getList().size(), max = 0, qu;

            //Getting all the added offers (tipically one at a time)
            List added = c.getAddedSubList();

            for (int i = 0; i < len; ++i) {

                if (!(added.get(i) instanceof OffertaEntity)) continue;
                /**
                 * for every OfferEntity instance in the added sublist,
                 * an evaluation check is performed
                 **/

                OffertaEntity newEntity = (OffertaEntity) added.get(i);

                int pos = size - len + i;
                //Absolute position of the entity

                if (pos > 0) {

                    OffertaEntity prevEntity = ((PacketList) c.getList()).getPrevious(pos);
                    /**
                     * Call to getPrevious(int position) method of PacketList;
                     * see that class documentation for explanation
                     */

                    if (!checkLocation(prevEntity, newEntity)) {
                        System.out.println("WRONG LOCATION");
                        Notifications.create().text("Le locazioni delle offerte non sono tra loro coerenti").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    if (!checkDate(prevEntity, newEntity)) {
                        System.out.println("WRONG TIME");
                        Notifications.create().text("Le date non sono tra loro coerenti").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }
                }
            }

            for (int i = 0; i < len; ++i) {

                OffertaEntity newEntity = (OffertaEntity) added.get(i);
                int pos = size - len + i;

                qu = newEntity.getQuantità();

                if (qu < 10) {

                    c.getList().remove(pos, size);

                    Notifications.create().text("Il numero di biglietti disponibili è insufficiente per formare un viaggio di gruppo").showWarning();
                    return;
                } else if (max < qu) max = qu;
                for (NumberLabel lbl : labels) lbl.updateNumber(newEntity.getPrezzo());
            }

            view.refreshSpinners(max);
        }

        else if (c.wasRemoved()) {

            List list = c.getList();
            int current = 0, qu;
            for (Object aList : list)
                current = current <= (qu = ((OffertaEntity) aList).getQuantità()) ?
                        qu : current;
            double price = 0;
            for (Object o : c.getRemoved())
                price -= ((OffertaEntity) o).getPrezzo();
            for (NumberLabel lbl : labels) lbl.updateNumber(price);
            view.forceRefresh(current == 0 ? 999 : current);
        }
    }

    /**
     * Utility method for entities temporal comparison
     * @param previous OffertaEntity: last meaningful entity in the observed List
     * @param next OffertaEntity: scrutinized entity
     * @return boolean: whether or not the entities can be placed one after another
     */
    private boolean checkDate(OffertaEntity previous, OffertaEntity next) {

        Date firstDate,
                secondDate = next.getDataInizio(); //Scrutiny refers to the beginning of the added offer
        boolean result;

        if (previous instanceof ViaggioEntity) {
            /**
             * PernottamentoEntities behave differently when next to ViaggioEntity instances.
             * Given that PernottamentoEntities do not specify any schedule restriction,
             * one can place them after a travel offer as long as day of arrival and reception matches.
             * For all the other Offers, common sense suggests that arrival should take place earlier than
             * the next offer start and that delays between the two should not be longer than 12 hours.
             **/

            firstDate = ((ViaggioEntity) previous).getDataArrivo();

            if (next instanceof PernottamentoEntity) {

                long travel = firstDate.getTime() - firstDate.getHours() * 3600000
                        - firstDate.getMinutes() * 60000 - firstDate.getSeconds() * 1000,
                        stay = secondDate.getTime() - secondDate.getHours() * 3600000
                                - secondDate.getMinutes() * 60000 - secondDate.getSeconds() * 1000;
                result = travel == stay;
            }
            else
                result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        else if (previous instanceof PernottamentoEntity) {
            /**
             * PernottamentoEntities put specific restriction for every type of OfferEntity.
             * An event could take place anytime between reception and overnight stay end.
             * A trip departure date should take place on the last day of overnight, as should a change
             * of overnight stays.
             */

            firstDate = ((PernottamentoEntity)previous).getDataFinale();
            firstDate = new Timestamp(firstDate.getTime() - firstDate.getHours() * 3600000
                    - firstDate.getMinutes() * 60000 - firstDate.getSeconds() * 1000);
            Date firstDateEnd = new Date(firstDate.getTime() + 3600000 * 23 + 60000 * 59);

            if (next instanceof EventoEntity) {
                Date zeroDate = previous.getDataInizio();
                zeroDate = new Timestamp(zeroDate.getTime() - zeroDate.getHours() * 3600000
                        - zeroDate.getMinutes() * 60000 - zeroDate.getSeconds() * 1000);
                Date thirdDate = ((EventoEntity) next).getDataFine();
                result = secondDate.after(zeroDate) && thirdDate.before(firstDateEnd);
            }
            else if (next instanceof ViaggioEntity)
                result = secondDate.after(firstDate) && firstDateEnd.after(secondDate);
            else
                result = firstDate.equals(secondDate);
        }

        else {
            /**
             * EventoEntities put no specific restriction on offers;
             * common sense suggests that any new offer should take place after the event ends,
             * but not later than 12 hours.
             * Tipically, EventoEntities are not scrutinized, due to the fact that they are often
             * included into overnight stays time spans.
             */
            firstDate = previous.getDataInizio();
            result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        return result;
    }

    /**
     * Utility method for entities spatial comparison
     * @param previous OffertaEntity: last meaningful entity in the observed List
     * @param next OffertaEntity: scrutinized entity
     * @return boolean: whether or not the entities can be placed one after another
     */
    boolean checkLocation(OffertaEntity previous, OffertaEntity next) {

        String city;
        if (previous instanceof ViaggioEntity) city = ((ViaggioEntity) previous).getDestinazione();
        else city = previous.getCittà();

        return city.equals(next.getCittà());
    }
}
