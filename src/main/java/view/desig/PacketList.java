package view.desig;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.entityDB.*;

import java.util.Date;

public class PacketList<T extends OffertaEntity> extends SimpleListProperty<AbstractEntity> {

    public PacketList() {

        super(FXCollections.observableArrayList());
    }

    public OffertaEntity getPrevious(int pos) {

        if (pos > size() || pos < 1) return null;

        if (get(pos - 1) instanceof PernottamentoEntity) return (OffertaEntity) get(pos - 1);
        else {

            PernottamentoEntity entity = (PernottamentoEntity) getPrevious(pos - 1);
            OffertaEntity realPrev = (OffertaEntity) get(pos - 1);
            Date finale;
            if (realPrev instanceof ViaggioEntity)
                finale = ((ViaggioEntity) realPrev).getDataArrivo();
            else if (realPrev instanceof EventoEntity)
                finale = ((EventoEntity) realPrev).getDataFine();
            else finale = ((PernottamentoEntity) realPrev).getDataFinale();

            if (entity != null && entity.getDataFinale().after(finale)) {
                System.out.println("PERNOTTAMENTO " + entity.getNome() + " " + entity.getDataFinale().toString());
                return entity;
            }
            else {
                System.out.println("OFFERTA " + realPrev.getNome().toString());
                return realPrev;
            }
        }
    }
}
