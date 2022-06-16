package com.example.application.views.helloworld;

import java.math.BigInteger;

public class BigIntegerInput {

    private BigInteger value;

    public BigIntegerInput(String value, String radix) {

        int radixInt;
        if (radix.equals("Hexadecimal - 16")) radixInt = 16;
        else radixInt = 10;

        this.value = new BigInteger(value,radixInt);
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
}
