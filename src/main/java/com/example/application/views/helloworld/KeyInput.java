package com.example.application.views.helloworld;

import java.math.BigInteger;
import java.util.Objects;

public class KeyInput extends NumberUtil{


    BigInteger value;

    public KeyInput(String label) {
        super(label);
        select.addValueChangeListener(event -> {
            if (!textField.getValue().equals("")) {

                int NewRadixInt;
                if (event.getValue().equals("Hexadecimal - 16")) NewRadixInt = 16;
                else NewRadixInt = 10;

                int OldRadixInt;
                if (Objects.isNull(event.getOldValue())) OldRadixInt = 16 ;
                else{
                    if (event.getOldValue().equals("Hexadecimal - 16")) OldRadixInt = 16;
                else OldRadixInt = 10;
                }

                //value = new BigIntegerInput(textField.getValue(),select.getValue());
                value = new BigInteger(textField.getValue(),OldRadixInt);
                textField.setValue(new BigInteger(textField.getValue(),OldRadixInt).toString(NewRadixInt));
            }
        });
    }
}
