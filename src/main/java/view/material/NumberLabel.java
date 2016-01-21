package view.material;

import javafx.scene.control.Label;
import view.observers.Observer;
import view.observers.Subject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberLabel extends Label implements Observer {

    String defaultMsg;
    double number = 0, addMod, prodMod;

    public NumberLabel(String defaultMsg) {
        this(defaultMsg, 0);
    }

    public NumberLabel(String defaultMsg, double number) {

        this(defaultMsg, number, 0, 1);
    }

    public NumberLabel(String defaultMsg, double number, double addMod, double prodMod) {

        this.defaultMsg = defaultMsg;
        this.number = number;
        this.addMod = addMod;
        this.prodMod = prodMod;

        setText();
    }

    public void reset() {
        number = 0;
        setText();
    }

    public void setText() {
        double modified = NumberLabel.round((number + addMod) * prodMod);
        setText(defaultMsg + new DecimalFormat("#.##").format(modified));
    }

    public double getNumber() {
        return number;
    }

    public static double round(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void setMod(double value) {

        prodMod = value;
        setText();
    }

    @Override
    public void update() {
        number = (double) subject[0].requestInfo();
        setText();
    }
}
