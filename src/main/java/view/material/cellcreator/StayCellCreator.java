package view.material.cellcreator;

import model.entityDB.PernottamentoEntity;
import view.material.EmptyCell;

import java.text.SimpleDateFormat;

public class StayCellCreator extends SubOfferCellCreator<PernottamentoEntity> {

    private static StayCellCreator me;
    protected static StayCellCreator instance() {

        if (me == null) me = new StayCellCreator();
        return me;
    }
    protected StayCellCreator() { super(); }

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
