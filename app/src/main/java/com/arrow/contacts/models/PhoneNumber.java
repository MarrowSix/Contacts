package com.arrow.contacts.models;

public class PhoneNumber {
    private String value;
    private int type;

    public PhoneNumber(String number, int type) {
        this.value = number;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}
