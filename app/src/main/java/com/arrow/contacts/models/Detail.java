package com.arrow.contacts.models;

import java.util.List;

public class Detail {
//    private List<String> phoneNumber;
//    private List<Integer> phoneType;
//    private List<String> emails;
//    private List<Integer> emailsType;
    private String value;
    private Integer type;
    private String prefix;
    private int mainIconId;
    private int backIconId;

    private  static final String[] numberType = {
            "",
            "住宅",
            "手机",
            "单位",
            "单位传真",
            "住宅传真",
            "",
            "其他"
    };

    private static final String[] emailType = {
            "",
            "住宅",
            "工作",
            "其他",
            "个人"
    };

    public Detail(String value, Integer type, String prefix, int mainIconId, int backIconId) {
        this.value = value;
        this.type = type;
        this.prefix = prefix;
        this.mainIconId = mainIconId;
        this.backIconId = backIconId;
    }

    public String getValue() {
        return value;
    }

    public String typeToString() {
        if (prefix.contains("@")) {
            return emailType[type];
        } else {
            return numberType[type];
        }
    }

    public Integer getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getMainIconId() {
        return mainIconId;
    }

    public int getBackIconId() {
        return backIconId;
    }

    //    public Detail(List<String> phoneNumber, List<Integer> phoneType, List<String> emails, List<Integer> emailsType) {
//        this.phoneNumber = phoneNumber;
//        this.phoneType = phoneType;
//        this.emails = emails;
//        this.emailsType = emailsType;
//    }

//    public List<String> getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public List<Integer> getPhoneType() {
//        return phoneType;
//    }
//
//    public List<String> getEmails() {
//        return emails;
//    }
//
//    public List<Integer> getEmailsType() {
//        return emailsType;
//    }
}
