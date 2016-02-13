package view.material.cellcreator;

import model.entityDB.*;
import view.material.EmptyCell;

/**
 * Pseudo-Abstract Factory class for DBCell initialization.
 * @param <T>
 */
public abstract class AbstractCellCreator<T extends AbstractEntity> {

    abstract EmptyCell createCell(T entity);

    public static EmptyCell getCell(AbstractEntity entity) {

        if (entity instanceof ViaggioEntity)
            return TravelCellCreator.instance().createCell((ViaggioEntity) entity);

        else if (entity instanceof EventoEntity)
            return EventCellCreator.instance().createCell((EventoEntity) entity);

        else if (entity instanceof PernottamentoEntity)
            return StayCellCreator.instance().createCell((PernottamentoEntity) entity);

        else if (entity instanceof PacchettoEntity)
            return PacketCellCreator.instance().createCell((PacchettoEntity) entity);

        else if (entity instanceof PrenotazioneEntity)
            return BookingCellCreator.instance().createCell((PrenotazioneEntity) entity);

        else return null;
    }

    protected static class OfferCellCreator extends AbstractCellCreator<OffertaEntity> {

        private static OfferCellCreator me;
        protected static OfferCellCreator instance() {
            if (me == null) me = new OfferCellCreator();
            return me;
        }
        private OfferCellCreator() { super(); }

        @Override protected EmptyCell createCell(OffertaEntity entity) { return new EmptyCell(entity.getNome()); }
    }
}
