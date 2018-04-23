package com.arrow.contacts.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Temp implements Serializable {
    private  static final String[] nType = {
            "住宅",
            "手机",
            "单位",
            "单位传真",
            "住宅传真",
            "其他"
    };

    private static final String[] eType = {
            "住宅",
            "工作",
            "其他",
            "个人"
    };

    private Map<String, String> numberTypeMap = new HashMap<>();
    private Map<String, String> emailTypeMap = new HashMap<>();

    private static List<String> numberType = Arrays.asList(nType);
    private static List<String> emailType = Arrays.asList(eType);

    private String name;
    private String firstName;
    private String lastName;
    private String cDomain;
    private String pNumber;
    private int numType;
    private String pEmail;
    private int emaType;

    public Temp(String firstName, String lastName,String cDomain, String pNumber, int numType, String pEmail, int emaType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cDomain = cDomain;
        this.pEmail = pEmail;
        this.pNumber = pNumber;
        this.emaType = emaType;
        this.numType = numType;
        initData();
    }

    private void initData() {
        numberTypeMap.put("住宅", "1");
        numberTypeMap.put("手机", "2");
        numberTypeMap.put("单位", "3");
        numberTypeMap.put("单位传真", "4");
        numberTypeMap.put("住宅传真", "5");
        numberTypeMap.put("其他", "7");

        emailTypeMap.put("住宅", "1");
        emailTypeMap.put("工作", "2");
        emailTypeMap.put("其他", "3");
        emailTypeMap.put("个人", "4");
    }

    public String getName() {
        return lastName + firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getcDomain() {
        return cDomain;
    }

    public String getpNumber() {
        return pNumber;
    }

    public String getpEmail() {
        return pEmail;
    }

    public String getSEmaType() {
        return emailTypeMap.get(eType[emaType]);
    }

    public String getSNumType() {
        return numberTypeMap.get(nType[numType]);
    }

    public int getEmaType() {
        return emaType;
    }

    public int getNumType() {
        return numType;
    }
}
