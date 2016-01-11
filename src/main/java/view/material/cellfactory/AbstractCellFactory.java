package view.material.cellfactory;

import controller.Constants;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;
import view.material.EmptyCell;

public abstract class AbstractCellFactory<T extends AbstractEntity> {

    abstract EmptyCell createCell(T entity);

    protected static class OfferCellFactory extends AbstractCellFactory<OffertaEntity> {

        private static OfferCellFactory me;

        public static OfferCellFactory instance() {

            if (me == null) me = new OfferCellFactory();
            return me;
        }

        @Override
        EmptyCell createCell(OffertaEntity entity) {

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
}
