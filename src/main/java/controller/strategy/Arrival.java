package controller.strategy;

import java.sql.Timestamp;

/**
 * Acorn class; Station instances wraps up this value for search management.
 * It contains a city name and (usually) a timestamp indicating time of arrival.
 * Timestamp is needed to select other relatively forthcoming travels; however,
 * it can be omitted, with no strong repercussions on the search algorithm.
 */
public class Arrival {

    private String city; //Arriving city name
    private Timestamp arrival; //Time of arrival

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Timestamp getArrival() { return arrival; }
    public void setArrival(Timestamp arrival) { this.arrival = arrival; }

    /**
     * Main constructor.
     * @param city String: the city name.
     * @param arrival Timestamp: time of arrival.
     */
    public Arrival(String city, Timestamp arrival) {
        this.city = city;
        this.arrival = arrival;
    }
    public Arrival(String city) { this(city, null); }
}
