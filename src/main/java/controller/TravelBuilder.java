package controller;

import model.entityDB.ViaggioEntity;
import java.sql.Date;

public class TravelBuilder extends EntityBuilder {

    public TravelBuilder() {

        entity = new ViaggioEntity();
    }

    @Override
    public void buildEntity(Object... objects) {

        ((ViaggioEntity)entity).setDestinazione((String) objects[0]);
        ((ViaggioEntity)entity).setDataArrivo((Date) objects[1]);
        ((ViaggioEntity)entity).setMezzo((String) objects[2]);
        ((ViaggioEntity)entity).setClasse((String) objects[3]);
        ((ViaggioEntity)entity).setStazionePartenza((String) objects[4]);
        ((ViaggioEntity)entity).setStazioneArrivo((String) objects[5]);

    }
}
