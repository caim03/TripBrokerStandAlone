package view.observers;

import java.util.ArrayList;
import java.util.List;

public interface Subject<T> {

    List<Observer> observers = new ArrayList<>();
    default void subscribe(Observer observer) {
        observer.setSubject(this);
        observers.add(observer);
    }
    default void unsubscribe(Observer observer) { observers.remove(observer); }
    default void publish() { for (Observer o : observers) o.update(); }
    T requestInfo() ;
    default T requestInfo(Observer observer) { return requestInfo(); }
}
