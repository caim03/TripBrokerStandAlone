package controller;

import model.entityDB.PernottamentoEntity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class StayBuilder extends EntityBuilder {

    public StayBuilder() {

        entity = new PernottamentoEntity();
    }

    @Override
    public void buildEntity(Object... objects) {

        ((PernottamentoEntity)entity).setDataFinale((Timestamp) objects[0]);
        ((PernottamentoEntity)entity).setServizio((String) objects[1]);
        ((PernottamentoEntity)entity).setQualità((String) objects[2]);
        ((PernottamentoEntity)entity).setLuogo((String) objects[3]);
    }
}
