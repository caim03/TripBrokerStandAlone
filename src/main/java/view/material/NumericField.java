package view.material;

import javafx.event.EventHandler;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Material;

public class NumericField extends MaterialTextField {

    private String discriminator = "0123456789";

    public NumericField() {

        this(true);
    }

    public NumericField(boolean isDouble) {

        super();

        if (isDouble) discriminator += ".";

        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {

                String c = keyEvent.getCharacter(), text = NumericField.this.getText();

                int comma = text.indexOf("."), pos = NumericField.this.getCaretPosition(),
                    len = NumericField.this.getLength();

                if (!discriminator.contains(c) || //NON-NUMERIC CHARACTER OR
                    (comma != -1 && //COMMA EXISTS AND
                    (".".equals(c) || (pos > comma && len - comma > 2)) //ANOTHER COMMA ENTERED OR NO DECIMAL SPACE LEFT
                    )) {
                    keyEvent.consume(); //DON'T ADD CHARACTER
                }
            }
        });
    }

    public double getNumber() {

        String string = getText();
        if ("".equals(string)) return 0;
        return Double.parseDouble(string);
    }
}
