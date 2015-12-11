package view.desig;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;

import java.util.Date;

public class PacketList extends SimpleListProperty<OffertaEntity> {

    int last = -1;

    public PacketList() {

        super(FXCollections.observableArrayList());
    }

    @Override
    public boolean add(OffertaEntity element) {

        boolean bool = super.add(element);

        if (last != -1) {

            Date date = element.getDataInizio();
            if (!date.before(((PernottamentoEntity)getPrevious()).getDataFinale())) last = -1;
        }

        if (element instanceof PernottamentoEntity) {

            track();
        }

        System.out.println("LAST " + last);

        return bool;
    }

    public OffertaEntity getPrevious() {

        if (size() < 2) return null;

        if (last != -1) return get(last);
        else return get(size() - 2);
    }

    void track() {

        last = size() - 1;
    }
}
