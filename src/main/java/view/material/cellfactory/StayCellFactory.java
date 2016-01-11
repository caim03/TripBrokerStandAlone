package view.material.cellfactory;

import model.entityDB.PernottamentoEntity;
import view.material.EmptyCell;

import java.text.SimpleDateFormat;

public class StayCellFactory extends SubOfferCellFactory<PernottamentoEntity> {

    private static StayCellFactory me;
    public static StayCellFactory instance() {

        if (me == null) me = new StayCellFactory();
        return me;
    }
    protected StayCellFactory() { super(); }

    @Override
    EmptyCell decorate(EmptyCell cell, PernottamentoEntity entity) {

        cell.setThumbnail("stay.png");

        String info1 = "presso " + entity.getLuogo() + ", " + entity.getCittà(),
               info2 =  "stelle: " + entity.getQualità()+ "; servizio: " + entity.getServizio(),
               info3 = "dal " + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataInizio())
                        + " al " + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataFinale());

        cell.addSubHeaders(info1, info2, info3);
        return cell;
    }
}
