package view.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject class of the Observer pattern. It is realized as an interface for lack of Multiple Inheritance in Java.
 * @param <T> an object representing the monitored state.
 */
public interface Subject<T> {

    List<Observer> observers = new ArrayList<>(); //List of observers

    /**
     * Default implementation of pattern subscribe method.
     * @param observer Observer: an observer monitoring this subject
     */
    default void subscribe(Observer observer) {
        observer.setSubject(this);
        observers.add(observer);
    }

    /**
     * Default implementation of pattern unsubscribe method. Unused.
     * @param observer Observer: the Observer object willing to stop monitoring
     */
    default void unsubscribe(Observer observer) { observers.remove(observer); }

    /**
     * Default implementation of pattern publish method.
     */
    default void publish() { for (Observer o : observers) o.update(); }

    /**
     * To-be-implemented method. Called after an observer has been notified of a change.
     * @return T: the state of the monitored object.
     */
    T requestInfo() ;

    /**
     * requestInfo() Overload. Deafult implementation calls for requestInfo().
     * @param observer Observer: the requesting Observer.
     * @return T: requested state.
     */
    default T requestInfo(Observer observer) { return requestInfo(); }
}
