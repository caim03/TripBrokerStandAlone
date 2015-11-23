package controller;

import model.entityDB.EventoEntity;
import model.entityDB.ProdottoEntity;

import java.sql.Date;

public class InsertOfferController {

    public static abstract class EntityBuilder {
        protected ProdottoEntity entity;
        public abstract void buildProductPart(String name, double price);
        public abstract void buildOfferPart(String city, double price, int amount, byte state, Date date);
        public abstract void buildEventPart(Object...objects);
    }
    public static class EventBuilder extends EntityBuilder{

        public EventBuilder() {

            entity = new EventoEntity();
        }

        @Override
        public void buildProductPart(String name, double price){
            entity.setNome(name);
            entity.setPrezzo(price);
        }

        @Override
        public void buildOfferPart(String city, double price, int amount, byte state, Date date){
            ((EventoEntity)entity).setCittà(city);
            ((EventoEntity)entity).setPrezzoFabbrica(price);
            ((EventoEntity)entity).setQuantità(amount);
            ((EventoEntity)entity).setStato(state);
            ((EventoEntity)entity).setDataInizio(date);
        }

        @Override
        public void buildEventPart(Object... objects) throws ClassCastException {
            ((EventoEntity)entity).setNumeroPosto((int) objects[0]);
            ((EventoEntity)entity).setOraInizio((int) objects[1]);
            ((EventoEntity)entity).setOraFine((int) objects[2]);
            ((EventoEntity)entity).setLuogo((String) objects[3]);
        }
    }

}
