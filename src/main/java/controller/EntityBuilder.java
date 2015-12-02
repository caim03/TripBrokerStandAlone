package controller;

import model.entityDB.OffertaEntity;
import model.entityDB.ProdottoEntity;
import java.sql.Date;

public abstract class EntityBuilder {

    protected ProdottoEntity entity;

    public ProdottoEntity getEntity(){
        return entity;
    }

    public void buildProduct(String name, double price) {

        entity.setNome(name);
        entity.setPrezzo(price);
    }

    public void buildOffer(String city, double price, int amount, byte state, Date date) {

        ((OffertaEntity)entity).setCittà(city);
        ((OffertaEntity)entity).setPrezzoFabbrica(price);
        ((OffertaEntity)entity).setQuantità(amount);
        ((OffertaEntity)entity).setStato(state);
        ((OffertaEntity)entity).setDataInizio(date);
    }

    public abstract void buildEntity(Object...objects);
}
