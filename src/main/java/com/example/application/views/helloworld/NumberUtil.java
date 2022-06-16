package com.example.application.views.helloworld;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

public class NumberUtil extends HorizontalLayout {


    TextField textField = new TextField();
    Select<String> select = new Select<>();

    public enum values{ hexadecimal, decimal}

    {
        //select.setItems(values.values());
        select.setItems("Hexadecimal - 16","Decimal - 10");
        select.setLabel("Numeral system");

        select.setWidth("20%");
        textField.setWidth("90%");

        add(textField,select);
        textField.setReadOnly(true);

    }



    public NumberUtil(String label) {
        textField.setLabel(label);
    }
}
