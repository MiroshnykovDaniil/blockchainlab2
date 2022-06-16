package com.example.application.views.helloworld;

public class NumberInput extends NumberUtil {



    public NumberInput(String label) {

        super(label);
        select.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            System.out.println(selectStringComponentValueChangeEvent.getValue());
            textField.setReadOnly(false);
        });

    }
}
