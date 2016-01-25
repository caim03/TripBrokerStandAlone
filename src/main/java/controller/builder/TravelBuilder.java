package controller.builder;

import model.entityDB.ViaggioEntity;

public class TravelBuilder extends EntityBuilder<ViaggioEntity, EntityBuilder.TravelArguments> {

    public TravelBuilder() { entity = new ViaggioEntity(); }

    @Override
    public void buildEntity(TravelArguments arguments) {

        entity.setDestinazione(arguments.destination);
        entity.setDataArrivo(arguments.date);
        entity.setMezzo(arguments.vehicle);
        entity.setClasse(arguments.quality);
    }
}
