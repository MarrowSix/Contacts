package com.arrow.contacts.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable{
    private Long id;
    private Long rawContactId;
    private int imageID;
    private String firstLetter, pinYin, name;
    private String prefix, firstName, middleName, surName, suffix;
    private String photoUri;
    private List<String> phoneNumber;
    private List<Integer> phoneType;
    private List<String> emails;
    private List<Integer> emailsType;

    public Contact(Long id, String prefix, String firstName, String middleName, String surName,
                   String photoUri, List<String> phoneNumber) {
        this.id = id;
        this.prefix = prefix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surName = surName;
        this.photoUri = photoUri;
        this.phoneNumber = phoneNumber;
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

    public Contact(String name, String pinYin, String firstLetter, int imageID, Long id, Long rawContactId) {
        this.name = name;
        this.pinYin = pinYin;
        this.firstLetter = firstLetter;
        this.imageID = imageID;
        this.id = id;
        this.rawContactId = rawContactId;
    }

    public Long getRawContactId() {
        return rawContactId;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
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

    public Long getId() {
        return id;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setEmailsType(List<Integer> emailsType) {
        this.emailsType = emailsType;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneType(List<Integer> phoneType) {
        this.phoneType = phoneType;
    }
}
