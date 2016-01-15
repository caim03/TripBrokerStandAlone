package controller;

import model.entityDB.OffertaEntity;
import model.entityDB.ProdottoEntity;

import java.sql.Timestamp;

public abstract class EntityBuilder {

    protected ProdottoEntity entity;

    public ProdottoEntity getEntity(){
        return entity;
    }

    public void buildProduct(String name, double price, String type) {

        entity.setNome(name);
        entity.setPrezzo(price);
        entity.setTipo(type);
    }

    public void buildOffer(String city, double price, int amount, byte state, Timestamp date) {

        ((OffertaEntity)entity).setCittà(city);
        ((OffertaEntity)entity).setPrezzoFabbrica(price);
        ((OffertaEntity)entity).setQuantità(amount);
        ((OffertaEntity)entity).setStato(state);
        ((OffertaEntity)entity).setDataInizio(date);
    }

    public abstract void buildEntity(Object...objects);
}
