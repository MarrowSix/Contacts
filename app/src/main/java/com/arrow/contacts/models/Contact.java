package com.arrow.contacts.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable{
    private Long id;
    private int imageID;
    private String firstLetter, pinYin, name;
    private String prefix, firstName, middleName, surName, suffix;
    private String photoUri;
    private List<String> phoneNumber;
    private List<Integer> phoneType;
    private List<String> emails;
    private List<Integer> emailsType;
    private List<Address> addresses;
    private List<Event> events;
    private String notes;
    private ArrayList<Group> groups;
    private Organization organization;

    public Contact(Long id, String prefix, String firstName, String middleName, String surName,
                   String photoUri, List<String> phoneNumber, String notes) {
        this.id = id;
        this.prefix = prefix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surName = surName;
        this.photoUri = photoUri;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }

    public Contact(String name,
                   List<String> phoneNumber, List<Integer> phoneType,
                   List<String> emails, List<Integer> emailsType,
                   String pinYin, String firstLetter,
                   int imageID, Long id) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.emails = emails;
        this.emailsType = emailsType;
        this.pinYin = pinYin;
        this.imageID = imageID;
        this.firstLetter = firstLetter;
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

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getPhoneType() {
        return phoneType;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<Integer> getEmailsType() {
        return emailsType;
    }
}
