package view;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import view.observers.Observer;

public class ObservantSpinner extends Spinner<Integer> {

    Observer adapter;

    public ObservantSpinner(int minimum, int maximum, int current) {
        super(minimum, maximum, current);
        setObserver(minimum, maximum);
    }

    private void setObserver(int minimum, int maximum) { adapter = new ObserverAdapter(this, minimum, maximum); }

    public Observer getObserverAdapter() { return adapter; }

    public class ObserverAdapter implements Observer {

        private ObservantSpinner adaptee;
        int minimum, maximum;

        ObserverAdapter(ObservantSpinner adaptee, int minimum, int maximum) {
            this.adaptee = adaptee;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public void update() {

            int qu = (int) Math.floor((Double) subject[0].requestInfo(this));
            System.out.println("QU IS " + qu);

            int ptr;

            try { ptr = adaptee.getValue(); }
            catch (NullPointerException e) { e.printStackTrace(); return; }
            catch (NumberFormatException e) { ptr = minimum; }

            System.out.println("PTR IS " + ptr);

            maximum = qu;
            adaptee.setValueFactory(new SpinnerValueFactory.
                    IntegerSpinnerValueFactory(minimum, maximum, ptr > qu ? qu : ptr));
            System.out.println("UPDATED");
        }
    }
}
