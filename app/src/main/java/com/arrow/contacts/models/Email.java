package com.arrow.contacts.models;

public class Email {
    private String value;
    private int type;

    public Email(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}
