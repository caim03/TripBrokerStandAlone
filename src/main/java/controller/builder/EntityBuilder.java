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

    /** @return ProdottoEntity; return the product entity **/
    public T getEntity() { return entity; }

    /** @param name; this string represents the name of the product
     *  @param price; this double represents the price of the product
     *  @param type; this string represents the type of the product (travel, event, stay or packet) **/
    public void buildProduct(String name, double price, String type) {

        entity.setNome(name);
        entity.setPrezzo(price);
        entity.setTipo(type);
    }

    /** @param city; this string represents the city of the offer
     *  @param amount; this integer represents the quantity bought of the offer
     *  @param prenotazioni; this integer represents the number of booking of the offer (initially is set to 0)
     *  @param date; this argument represents the date of the offer as timestamp **/
    public void buildOffer(String city, int amount, int prenotazioni, Timestamp date) {

        entity.setCittà(city);
        entity.setQuantità(amount);
        entity.setPrenotazioni(prenotazioni);
        entity.setDataInizio(date);
    }

    public abstract void buildEntity(A arguments);

    /** @param type; the type of offer **/
    public static EntityBuilder getBuilder(String type) {
        if (Constants.travel.equals(type)) return new TravelBuilder();
        else if (Constants.event.equals(type)) return new EventBuilder();
        else if (Constants.stay.equals(type)) return new OvernightBuilder();
        return null;
    }


    public static class Arguments {

        protected Timestamp date;

        /** @param destination; represents the city of destination of the travel
         *  @param arrivalDate; the arrival date
         *  @param vehicle; the vehicle used in the travel
         *  @param quality; the class of travel
         *  @return Arguments; a set of parameters **/
        public static Arguments from(String destination, Timestamp arrivalDate, String vehicle, String quality) {
            return new TravelArguments(destination, arrivalDate, vehicle, quality);
        }

        /** @param location; represents the location of the event
         *  @param endDate; represents the date of end event
         *  @return Arguments; a set of parameters **/
        public static Arguments from(String location, Timestamp endDate) {
            return new EventArguments(location, endDate);
        }

        /** @param location; represents the location of the stay
         *  @param service; represents the service of the stay
         *  @param quality; represents the quality, in stars, of the stay
         *  @param checkOut; checkout timestamp
         *  @return Arguments; a set of parameters **/
        public static Arguments from(String location, String service, String quality, Timestamp checkOut) {
            return new OvernightArguments(location, service, quality, checkOut);
        }

        protected Arguments(Timestamp date) { this.date = date; }

        /** @return Timestamp; return the timestamp of the date **/
        public Timestamp getDate() { return this.date; }

        /** @param timestamp; set the timestamp value **/
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

        /** @param location; the location of the event
         *  @param endDate; the end date of the event**/
        public EventArguments(String location, Timestamp endDate) {
            super(endDate);
            this.location = location;
        }
    }

    protected static class OvernightArguments extends Arguments {

        protected String location, service, quality;

        /** @param location; the location of the stay
         *  @param service; the service of the stay
         *  @param quality; the quality of the stay
         *  @param checkOut; the date of check out **/
        public OvernightArguments(String location, String service, String quality, Timestamp checkOut) {
            super(checkOut);
            this.location = location;
            this.service = service;
            this.quality = quality;
        }
    }
}
