package controller;

import model.entityDB.EventoEntity;
import java.sql.Date;

public class EventBuilder extends EntityBuilder{

    @Override
    public void buildProduct(String name, double price) {
        entity.setNome(name);
        entity.setPrezzo(price);
    }

    @Override
    public void buildOffer(String city, double price, int amount, byte state, Date date) {
        ((EventoEntity)entity).setCittà(city);
        ((EventoEntity)entity).setPrezzoFabbrica(price);
        ((EventoEntity)entity).setQuantità(amount);
        ((EventoEntity)entity).setStato(state);
        ((EventoEntity)entity).setDataInizio(date);
    }

    @Override
    public void buildEntity(Object...objects) throws ClassCastException {
        ((EventoEntity)entity).setNumeroPosto((int) objects[0]);
        ((EventoEntity)entity).setOraInizio((int) objects[1]);
        ((EventoEntity)entity).setOraFine((int) objects[2]);
        ((EventoEntity)entity).setLuogo((String) objects[3]);
    }
}
