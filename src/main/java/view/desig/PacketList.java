package view.desig;

import controller.PacketOverseer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.entityDB.*;
import view.ObservantSpinner;
import view.observers.Observer;

import javax.security.auth.Subject;
import java.util.Date;

/***
 * Peculiar ObservableList implementation, used in conjunction with PacketOverseer objects,
 * aimed at supervise offer additions to a Packet under construction. This class
 * wisely selects the previous-in-line offer to be compared with the addition.
 * It also implements Subject<Double> interface because the total price of OffertaEntity
 * assembly is monitored by NumberLabel ObserverAdapters.
 * @param <T> extends OffertaEntity: any OffertaEntity subclass instance
 */
public class PacketList<T extends OffertaEntity> extends SimpleListProperty<AbstractEntity> implements view.observers.Subject<Double> {

    Double price = 0.0;

    public PacketList() {
        super(FXCollections.observableArrayList());
        addListener();
    }

    protected void addListener() { addListener(new PacketOverseer(this)); }

    /**
     * Main research method provided by the class. It recursively checks for any
     * PernottamentoEntity instances, which often have a wide temporal range that
     * includes other timelines.
     * @param pos int: the position from which this class should begin introspection
     * @return OffertaEntity: the ultimately selected OffertaEntity instance, or null if
     *                        inconsistencies were detected
     */
    public OffertaEntity getPrevious(int pos) {

        /**
         * Must check for PernottamentoEntity instances; after that, it is
         * important to check if the actual previous instance takes place after
         * the PernottamentoEntity. One of them is then returned.
         */

        PernottamentoEntity entity = goDeep(pos);
        OffertaEntity realPrev = (OffertaEntity) get(pos - 1);
        Date finale;
        if (realPrev instanceof ViaggioEntity)
            finale = ((ViaggioEntity) realPrev).getDataArrivo();
        else if (realPrev instanceof EventoEntity)
            finale = ((EventoEntity) realPrev).getDataFine();
        else finale = ((PernottamentoEntity) realPrev).getDataFinale();

        if (entity != null && entity.getDataFinale().after(finale))
            return entity;
        else
            return realPrev;
    }

    public PernottamentoEntity goDeep(int pos) {

        if (pos > size() || pos < 1) return null; //Invalid position; also, recursion base

        else if ((get(pos - 1) instanceof PernottamentoEntity))
            return (PernottamentoEntity) get(pos - 1); //PernottamentoEntity found

        else return goDeep(pos - 1);
    }

    /**
     * Implementation of requestInfo() Subject method.
     * @return Double: total price.
     */
    @Override public Double requestInfo() { return getPrice(); }

    /**
     * Setter method realizing the publishing routine.
     * @param price double: total price.
     */
    public void setPrice(double price) {
        this.price = price;
        publish();
    }

    public Double getPrice() { return price; }
}
