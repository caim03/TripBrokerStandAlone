package view;

/** All implementing classes must override the harvest() method; this method is
 *  normally implemented to retrieve data from GUI components into the implementing
 *  class. It is used primarily for polymorphism purposes**/

public interface Collector {

    void harvest();
}
