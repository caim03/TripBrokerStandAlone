package view.material.cellcreator;

import model.entityDB.EventoEntity;
import view.material.EmptyCell;

import java.text.SimpleDateFormat;

public class EventCellCreator extends SubOfferCellCreator<EventoEntity> {

    private static EventCellCreator me;
    public static EventCellCreator instance() {

        if (me == null) me = new EventCellCreator();
        return me;
    }
    protected EventCellCreator() { super(); }

    @Override
    EmptyCell decorate(EmptyCell cell, EventoEntity entity) {

        cell.setThumbnail("event.png");

        String info1 = "presso " + entity.getLuogo() + ", " + entity.getCittà(),
               info2 = "dalle " + new SimpleDateFormat("HH:mm").format(entity.getDataInizio()) +
                       " alle " + new SimpleDateFormat("HH:mm").format(entity.getDataFine()) + " del "
                       + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataInizio());

        cell.addSubHeaders(info1, info2);
        return cell;
    }
}
