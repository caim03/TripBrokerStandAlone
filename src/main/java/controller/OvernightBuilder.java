package controller;

import model.entityDB.PernottamentoEntity;

public class OvernightBuilder extends EntityBuilder<PernottamentoEntity, EntityBuilder.OvernightArguments> {

    public OvernightBuilder() { entity = new PernottamentoEntity(); }

    @Override
    protected void buildEntity(OvernightArguments arguments) {
        entity.setDataFinale(arguments.checkOut);
        entity.setServizio(arguments.service);
        entity.setQualità(arguments.quality);
        entity.setLuogo(arguments.location);
    }
}
