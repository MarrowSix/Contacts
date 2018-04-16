package com.arrow.contacts.models;

import java.util.ArrayList;

public class Contact {
    private int id;
    private String firstLetter, pinYin;
    private String prefix, firstName, middleName, surName, suffix;
    private String photoUri;
    private String phoneNumber;
    private ArrayList<Email> emails;
    private ArrayList<Address> addresses;
    private ArrayList<Event> events;
    private String notes;
    private ArrayList<Group> groups;
    private Organization organization;

    public Contact(int id, String prefix, String firstName, String middleName, String surName,
                   String photoUri, String phoneNumber, String notes) {
        this.id = id;
        this.prefix = prefix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surName = surName;
        this.photoUri = photoUri;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        return firstLetter;
    }
}