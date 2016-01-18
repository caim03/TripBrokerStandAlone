package controller;

import model.entityDB.OffertaEntity;
import model.entityDB.ProdottoEntity;

import java.sql.Timestamp;

/*** This class use Pattern Builder to build an offer in more steps;
 *   the patter builder separates the construction of a complex object from its representation.
 *   In this way we can construct an object step-by-step avoiding argument heavy constructors.
 *
 *   The classes that extend EntityBuilder must override 'buildEntity' factory method, in which they define
 *   a way to construct the right object (travel, stay or event). ***/

public abstract class EntityBuilder {

    protected ProdottoEntity entity;

    public ProdottoEntity getEntity(){
        /** @result ProdottoEntity; return the product entity **/

        return entity;
    }

    public void buildProduct(String name, double price, String type) {
        /** @param String; this string represents the name of the product
         *  @param double; this double represents the price of the product
         *  @param String; this string represents the type of the product (travel, event, stay or packet) **/

        entity.setNome(name);
        entity.setPrezzo(price);
        entity.setTipo(type);
    }

    public void buildOffer(String city, double price, int amount, int state, Timestamp date) {
        /** @param String; this string represents the city of the offer
         *  @param double; this double represents the factory price of the offer
         *  @param int; this integer represents the quantity bought of the offer
         *  @param int; this integer represents the number of booking of the offer (initially is set to 0)
         *  @param Timestamp; this argument represents the date of the offer as timestamp **/

        ((OffertaEntity)entity).setCittà(city);
        ((OffertaEntity)entity).setPrezzoFabbrica(price);
        ((OffertaEntity)entity).setQuantità(amount);
        ((OffertaEntity)entity).setPrenotazioni(state);
        ((OffertaEntity)entity).setDataInizio(date);
    }

    public abstract void buildEntity(Object...objects);
    /** @param Object[]; a list of objects that depend by the particular offer that implement this method **/
}
