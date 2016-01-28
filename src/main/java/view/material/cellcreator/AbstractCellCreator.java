package view.material.cellcreator;

import controller.Constants;
import javafx.scene.Node;
import model.entityDB.*;
import view.material.EmptyCell;

public abstract class AbstractCellCreator<T extends AbstractEntity> {

    abstract EmptyCell createCell(T entity);

    protected static class OfferCellCreator extends AbstractCellCreator<OffertaEntity> {

        private static OfferCellCreator me;

        public static OfferCellCreator instance() {

            if (me == null) me = new OfferCellCreator();
            return me;
        }

        @Override
        protected EmptyCell createCell(OffertaEntity entity) {

            String type = entity.getTipo(); //retrieve item type

            String image; //Declare image path String, initialized based on subclass membership
            if (Constants.stay.equals(type)) image = "stay.png"; //Pernottamento
            else if (Constants.event.equals(type)) image = "event.png"; //Evento
            else if (Constants.travel.equals(type)) { //Viaggio

                String vehicle = ((ViaggioEntity) entity).getMezzo();

                if (Constants.plane.equals(vehicle)) image = "airplane.png"; //Aereo
                else if (Constants.bus.equals(vehicle)) image = "bus.png"; //Bus
                else if (Constants.train.equals(vehicle)) image = "train.png"; //Treno
                else if (Constants.boat.equals(vehicle)) image = "boat.png"; //Nave
                else image = "create.png"; //Default
            }
            else image = "create.png"; //Default

            return new EmptyCell(entity.getNome()); //Automated cell creation
        }
    }

    public static EmptyCell getCell(AbstractEntity entity) {

        if (entity instanceof ViaggioEntity)
            return TravelCellCreator.instance().createCell((ViaggioEntity) entity);

        else if (entity instanceof EventoEntity)
            return EventCellCreator.instance().createCell((EventoEntity) entity);

        else if (entity instanceof PernottamentoEntity)
            return StayCellCreator.instance().createCell((PernottamentoEntity) entity);

        else if (entity instanceof CreaPacchettoEntity)
            return PacketCellCreator.instance().createCell((CreaPacchettoEntity) entity);

        else if (entity instanceof PrenotazioneEntity)
            return BookingCellCreator.instance().createCell((PrenotazioneEntity) entity);

        else return null;
    }
}
