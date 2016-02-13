package view.material;

import javafx.scene.input.KeyEvent;

public class NumericField extends MaterialTextField {

    private String discriminator = "0123456789";

    public NumericField() {

        this(true);
    }

    public NumericField(boolean isDouble) {

        super();

        if (isDouble) discriminator += ".";

        this.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue == null) setText(""); });

        this.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {

            String c = keyEvent.getCharacter(), text1 = NumericField.this.getText();

            int comma = text1.indexOf("."), pos = NumericField.this.getCaretPosition(),
                len = NumericField.this.getLength();

            if (!discriminator.contains(c) || //NON-NUMERIC CHARACTER OR
                (comma != -1 && //COMMA EXISTS AND
                (".".equals(c) || (pos > comma && len - comma > 2)) //ANOTHER COMMA ENTERED OR NO DECIMAL SPACE LEFT
                )) {
                keyEvent.consume(); //DON'T ADD CHARACTER
            }
        });
    }

    public double getNumber() {

        String string = getText();
        if ("".equals(string)) return 0;
        return Double.parseDouble(string);
    }
}
