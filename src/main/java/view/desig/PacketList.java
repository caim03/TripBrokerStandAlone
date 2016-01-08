package view.desig;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;

import java.util.Date;

public class PacketList<T extends AbstractEntity> extends SimpleListProperty<AbstractEntity> {

    int last = -1;

    public PacketList() {

        super(FXCollections.observableArrayList());
    }

    @Override
    public boolean add(AbstractEntity entity) {

        if (!entity.isValid()) return super.add(entity);
        else if (!(entity instanceof OffertaEntity)) return false;

        OffertaEntity element = (OffertaEntity) entity;
        System.out.println("OFFER " + element.getNome());
        boolean bool = super.add(element);

        if (last != -1) {

            Date date = element.getDataInizio();
            if (!date.before(((PernottamentoEntity)getPrevious()).getDataFinale())) last = -1;
        }

        if (element instanceof PernottamentoEntity) {

            track();
        }

        return bool;
    }

    public OffertaEntity getPrevious() {

        if (size() < 2) return null;

        if (last != -1) return (OffertaEntity) get(last);
        else return (OffertaEntity) get(size() - 2);
    }

    void track() {

        last = size() - 1;
    }
}
