package view;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import view.observers.Observer;

public class ObservantSpinner extends Spinner<Integer> implements Observer {

    int minimum, maximum;

    public ObservantSpinner(int minimum, int maximum, int current) {
        super(minimum, maximum, current);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public void update() {

        int qu = (int) Math.floor((Double) subject[0].requestInfo(this));

        int ptr;

        try { ptr = getValue(); }
        catch (NullPointerException e) { e.printStackTrace(); return; }
        catch (NumberFormatException e) { ptr = minimum; }

        maximum = qu;
        setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(minimum, maximum, ptr > qu ? qu : ptr));
    }
}
