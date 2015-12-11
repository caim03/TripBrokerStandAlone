package view.material;

import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberLabel extends Label {

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
        this.addMod = addMod;
        this.prodMod = prodMod;

        updateNumber(number);
    }

    public void updateNumber(double b) {

        double addition = NumberLabel.round((b + addMod) * prodMod);

        number += addition;
        NumberLabel.round(number);

        setText();
    }

    public void reset() {

        number = 0;
        setText();
    }

    public void setText() {

        setText(defaultMsg + new DecimalFormat("#.##").format(number));
    }

    public double getNumber() {
        return number;
    }

    public static double round(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
