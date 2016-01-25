package controller.builder;

import model.entityDB.PernottamentoEntity;

public class OvernightBuilder extends EntityBuilder<PernottamentoEntity, EntityBuilder.OvernightArguments> {

    public OvernightBuilder() { entity = new PernottamentoEntity(); }

    @Override
    public void buildEntity(OvernightArguments arguments) {
        entity.setDataFinale(arguments.date);
        entity.setServizio(arguments.service);
        entity.setQualità(arguments.quality);
        entity.setLuogo(arguments.location);
    }
}
