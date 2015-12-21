package controller;

import model.entityDB.EventoEntity;

public class EventBuilder extends EntityBuilder {

    public EventBuilder() { entity = new EventoEntity(); }

    @Override
    public void buildEntity(Object...objects) {

        ((EventoEntity)entity).setNumeroPosto((int) objects[0]);
        ((EventoEntity)entity).setOraInizio((int) objects[1]);
        ((EventoEntity)entity).setOraFine((int) objects[2]);
        ((EventoEntity)entity).setLuogo((String) objects[3]);
    }
}
