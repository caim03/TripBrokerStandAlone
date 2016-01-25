package controller.builder;

import model.entityDB.EventoEntity;

public class EventBuilder extends EntityBuilder<EventoEntity, EntityBuilder.EventArguments> {

    public EventBuilder() { entity = new EventoEntity(); }

    @Override
    public void buildEntity(EventArguments arguments) {
        entity.setDataFine(arguments.date);
        entity.setLuogo(arguments.location);
    }
}
