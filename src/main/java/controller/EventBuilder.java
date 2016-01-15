package controller;

import model.entityDB.EventoEntity;

import java.sql.Date;
import java.sql.Timestamp;

public class EventBuilder extends EntityBuilder {

    public EventBuilder() { entity = new EventoEntity(); }

    @Override
    public void buildEntity(Object...objects) {

        ((EventoEntity)entity).setNumeroPosto((int) objects[0]);
        ((EventoEntity)entity).setDataFine((Timestamp) objects[1]);
        ((EventoEntity)entity).setLuogo((String) objects[2]);
    }
}
