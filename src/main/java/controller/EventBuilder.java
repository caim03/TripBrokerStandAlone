package controller;

import model.entityDB.EventoEntity;

import java.sql.Date;
import java.sql.Timestamp;

public class EventBuilder extends EntityBuilder<EventoEntity, EntityBuilder.EventArguments> {

    public EventBuilder() { entity = new EventoEntity(); }

    @Override
    protected void buildEntity(EventArguments arguments) {
        entity.setDataFine(arguments.endDate);
        entity.setLuogo(arguments.location);
    }
}
