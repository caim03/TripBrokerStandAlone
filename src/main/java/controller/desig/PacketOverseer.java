package controller.desig;

import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/***
 * Overseer implementation used in conjunction with a PacketList
 * instance to monitor and eventually refuse Offers addition to an under construction
 * Packet. Offers acceptance or refusal depend on spatial and temporal comparison.
 */
public class PacketOverseer extends Overseer {

    protected PacketList subjectList;
    private boolean notify;
    private static long acceptableDelay = 12 * 3600000;
    private final String
            ERROR_LOC = "Le locazioni delle offerte non sono tra loro coerenti",
            ERROR_TME = "Le date non sono tra loro coerenti";

    public PacketOverseer(PacketList subjectList, boolean notify) {
        this.subjectList = subjectList;
        this.notify = notify;
    }

    @Override
    protected void checkAdded(Change<? extends AbstractEntity> c) {

        int len = c.getAddedSize(), size = c.getList().size();

        //Getting all the added offers (tipically one at a time)
        List added = c.getAddedSubList();

        double price = subjectList.getPrice();

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
                    c.getList().remove(pos, size);
                    if (notify) Notifications.create().text(ERROR_LOC).showWarning();
                    return;
                }

                if (!checkDate(prevEntity, newEntity)) {
                    c.getList().remove(pos, size);
                    if (notify) Notifications.create().text(ERROR_TME).showWarning();
                    return;
                }
            }

            if (someOtherAddedCheck(c, newEntity)) {
                price += newEntity.getPrezzo();
                updateSubject(price);
            }
            else {
                c.getList().remove(pos, size);
                if (notify) Notifications.create().text(someOtherAddedMessage()).showWarning();
                return;
            }
        }
    }

    protected boolean someOtherAddedCheck(Change<? extends AbstractEntity> c, OffertaEntity entity) { return true; }
    protected String someOtherAddedMessage() { return ""; }

    @Override
    protected void checkRemoved(Change<? extends AbstractEntity> c) {

        int len = c.getRemovedSize();
        List<AbstractEntity> removed = (List<AbstractEntity>) c.getRemoved();

        if (c.getList().size() == 0) subjectList.setPrice(0.0);
        else {

            double price = subjectList.getPrice();

            for (int i = 0; i < len; ++i) {

                if (removed.get(i) instanceof OffertaEntity) {
                    OffertaEntity item = (OffertaEntity) removed.get(i);
                    price -= item.getPrezzo();
                }
            }

            updateSubject(price);
        }

        someOtherRemovedCheck(c);
    }

    protected void someOtherRemovedCheck(Change<? extends AbstractEntity> c) { }

    protected void updateSubject(double price) { subjectList.setPrice(price); }

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
