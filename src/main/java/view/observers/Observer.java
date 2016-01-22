package view.observers;

/**
 * Observer interface from Observer pattern, realized as an interface for lack of Multiple Inheritance in Java.
 */
public interface Observer {

    Subject subject[] = new Subject[1]; //Monitored subject

    /**
     * update() method contract.
     */
    void update();

    /**
     * Default implementation of subject association method.
     * @param s Subject: monitored Sujìbject instance.
     */
    default void setSubject(Subject s) { subject[0] = s; }
}
