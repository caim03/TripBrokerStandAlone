package controller;

import model.entityDB.ProdottoEntity;
import java.sql.Date;

public abstract class EntityBuilder {
    protected ProdottoEntity entity;

    public ProdottoEntity getEntity(){
        return entity;
    }

    public abstract void buildProduct(String name, double price);
    public abstract void buildOffer(String city, double price, int amount, byte state, Date date);
    public abstract void buildEntity(Object...objects);
}
