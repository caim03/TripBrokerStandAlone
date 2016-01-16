package controller;

import javafx.collections.ListChangeListener;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.NumberLabel;

import java.util.Date;
import java.util.List;

/***
 * ListChangeListener implementation used in conjunction with a PacketList
 * instance to monitor and eventually refuse Offers addition to an under construction
 * Packet. Offers acceptance or refusal depend on spatial and temporal comparison, as
 * explained below.
 */

public class PacketOverseer implements ListChangeListener<AbstractEntity> {

    NumberLabel labels[]; //Price bounds Labels, to be updated on Offer acceptance
    private static long acceptableDelay = 12 * 3600000;

    public PacketOverseer(NumberLabel... labels) { this.labels = labels; }

    /***
     * onChanged method Override.
     * When a new OfferEntity instance is added to the observed List, this Overseer instance
     * considers whether or not the inserted object fits into a Packet semantical structure.
     * It checks both spatial and temporal bounds of the new element and the previous one in the List,
     * and if they match the Change is allowed; otherwise, a warning notifications is shown to the
     * user and the element rejected.
     * @param c Change: a Change instance representing a change into the observed List elements
     */
    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {

        c.next();

        if (c.wasAdded()) {

            int len = c.getAddedSize(), size = c.getList().size();

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

                        Notifications.create().text("Le locazioni delle offerte non sono tra loro coerenti").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }

                    if (!checkDate(prevEntity, newEntity)) {

                        Notifications.create().text("Le date non sono tra loro coerenti").showWarning();
                        c.getList().remove(pos, size);
                        return;
                    }
                }

                for (NumberLabel lbl : labels) lbl.updateNumber(newEntity.getPrezzo());
            }
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

            if (next instanceof PernottamentoEntity)
                result = firstDate.getTime() - firstDate.getHours() * 3600000
                       - firstDate.getMinutes() * 60000 - firstDate.getSeconds() * 1000 == secondDate.getTime();
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
