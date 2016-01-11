package view.material.cellfactory;

import model.entityDB.EventoEntity;
import view.material.EmptyCell;

import java.text.SimpleDateFormat;

public class EventCellFactory extends SubOfferCellFactory<EventoEntity> {

    private static EventCellFactory me;
    public static EventCellFactory instance() {

        if (me == null) me = new EventCellFactory();
        return me;
    }
    protected EventCellFactory() { super(); }

    @Override
    EmptyCell decorate(EmptyCell cell, EventoEntity entity) {

        cell.setThumbnail("event.png");

        String info1 = "presso " + entity.getLuogo() + ", " + entity.getCittà(),
               info2 = "dalle " + entity.getOraInizio() + ":00 alle " + entity.getOraFine() + ":00 del "
                       + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataInizio())
                       ;

        cell.addSubHeaders(info1, info2);
        return cell;
    }
}
