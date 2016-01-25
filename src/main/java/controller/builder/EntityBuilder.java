package controller.builder;

import controller.Constants;
import model.entityDB.OffertaEntity;

import java.sql.Timestamp;

/*** This class use Pattern Builder to build an offer in more steps;
 *   the patter builder separates the construction of a complex object from its representation.
 *   In this way we can construct an object step-by-step avoiding argument heavy constructors.
 *
 *   The classes that extend EntityBuilder must override 'buildEntity' factory method, in which they define
 *   a way to construct the right object (travel, stay or event). ***/

public abstract class EntityBuilder<T extends OffertaEntity, A extends EntityBuilder.Arguments> {

    protected T entity;

    public T getEntity(){
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

    public void buildOffer(String city, int amount, int prenotazioni, Timestamp date) {
        /** @param String; this string represents the city of the offer
         *  @param double; this double represents the factory price of the offer
         *  @param int; this integer represents the quantity bought of the offer
         *  @param int; this integer represents the number of booking of the offer (initially is set to 0)
         *  @param Timestamp; this argument represents the date of the offer as timestamp **/

        entity.setCittà(city);
        entity.setQuantità(amount);
        entity.setPrenotazioni(prenotazioni);
        entity.setDataInizio(date);
    }

    public abstract void buildEntity(A arguments);

    public static EntityBuilder getBuilder(String type) {
        if (Constants.travel.equals(type)) return new TravelBuilder();
        else if (Constants.event.equals(type)) return new EventBuilder();
        else if (Constants.stay.equals(type)) return new OvernightBuilder();
        return null;
    }

    /** **/

    public static class Arguments {

        protected Timestamp date;

        public static Arguments from(String destination, Timestamp arrivalDate, String vehicle, String quality) {
            return new TravelArguments(destination, arrivalDate, vehicle, quality);
        }

        public static Arguments from(String location, Timestamp endDate) {
            return new EventArguments(location, endDate);
        }

        public static Arguments from(String location, String service, String quality, Timestamp checkOut) {
            return new OvernightArguments(location, service, quality, checkOut);
        }

        protected Arguments(Timestamp date) { this.date = date; }

        public Timestamp getDate() { return this.date; }

        public void setDate(Timestamp timestamp) {
            this.date = timestamp;
        }
    }

    protected static class TravelArguments extends Arguments {

        protected String destination, vehicle, quality;

        public TravelArguments(String destination, Timestamp arrivalDate, String vehicle, String quality) {
            super(arrivalDate);
            this.destination = destination;
            this.vehicle = vehicle;
            this.quality = quality;
        }
    }

    protected static class EventArguments extends Arguments {

        protected String location;

        public EventArguments(String location, Timestamp endDate) {
            super(endDate);
            this.location = location;
        }
    }

    protected static class OvernightArguments extends Arguments {

        protected String location, service, quality;

        public OvernightArguments(String location, String service, String quality, Timestamp checkOut) {
            super(checkOut);
            this.location = location;
            this.service = service;
            this.quality = quality;
        }
    }
}
