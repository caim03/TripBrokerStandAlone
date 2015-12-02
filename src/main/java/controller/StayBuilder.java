package controller;

import model.entityDB.PernottamentoEntity;
import java.sql.Date;

public class StayBuilder extends EntityBuilder{
    @Override
    public void buildProduct(String name, double price) {
        entity.setNome(name);
        entity.setPrezzo(price);
    }

    @Override
    public void buildOffer(String city, double price, int amount, byte state, Date date) {
        ((PernottamentoEntity)entity).setCittà(city);
        ((PernottamentoEntity)entity).setPrezzoFabbrica(price);
        ((PernottamentoEntity)entity).setQuantità(amount);
        ((PernottamentoEntity)entity).setStato(state);
        ((PernottamentoEntity)entity).setDataInizio(date);
    }

    @Override
    public void buildEntity(Object... objects) throws ClassCastException {
        ((PernottamentoEntity)entity).setDataFinale((Date) objects[0]);
        ((PernottamentoEntity)entity).setServizio((String) objects[1]);
        ((PernottamentoEntity)entity).setQualità((String) objects[2]);
        ((PernottamentoEntity)entity).setLuogo((String) objects[3]);
    }
}
