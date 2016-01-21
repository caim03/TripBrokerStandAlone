package view.observers;

public interface Observer {

    Subject subject[] = new Subject[1];
    void update();
    default void setSubject(Subject s) { subject[0] = s; }
}
