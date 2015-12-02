package controller;

import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import java.sql.Date;

public class StayBuilder extends EntityBuilder {

    public StayBuilder() {

        entity = new PernottamentoEntity();
    }

    @Override
    public void buildEntity(Object... objects) {

        ((PernottamentoEntity)entity).setDataFinale((Date) objects[0]);
        ((PernottamentoEntity)entity).setServizio((String) objects[1]);
        ((PernottamentoEntity)entity).setQualità((String) objects[2]);
        ((PernottamentoEntity)entity).setLuogo((String) objects[3]);
    }
}
