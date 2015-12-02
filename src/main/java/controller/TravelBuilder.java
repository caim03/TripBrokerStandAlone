package controller;

import model.entityDB.ViaggioEntity;
import java.sql.Date;

public class TravelBuilder extends EntityBuilder{
    @Override
    public void buildProduct(String name, double price) {
        entity.setNome(name);
        entity.setPrezzo(price);
    }

    @Override
    public void buildOffer(String city, double price, int amount, byte state, Date date) {
        ((ViaggioEntity)entity).setCittà(city);
        ((ViaggioEntity)entity).setPrezzoFabbrica(price);
        ((ViaggioEntity)entity).setQuantità(amount);
        ((ViaggioEntity)entity).setStato(state);
        ((ViaggioEntity)entity).setDataInizio(date);
    }

    @Override
    public void buildEntity(Object... objects) throws ClassCastException{
        ((ViaggioEntity)entity).setDestinazione((String) objects[0]);
        ((ViaggioEntity)entity).setOraPartenza((int) objects[1]);
        ((ViaggioEntity)entity).setOraArrivo((int) objects[2]);
        ((ViaggioEntity)entity).setMezzo((String) objects[3]);
        ((ViaggioEntity)entity).setClasse((String) objects[4]);
        ((ViaggioEntity)entity).setStazionePartenza((String) objects[5]);
        ((ViaggioEntity)entity).setStazioneArrivo((String) objects[6]);
    }
}
