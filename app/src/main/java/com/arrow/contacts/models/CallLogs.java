package com.arrow.contacts.models;

import com.arrow.contacts.R;

public class CallLogs {
    private String number;
    private Integer type;
    private Long date;
    private Long duration;
    private Integer callType;

    private  static final int[] callTypeImageIds= {
            R.drawable.ic_call_incoming,
            R.drawable.ic_call_outgoing,
            R.drawable.ic_call_missed
    };

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

    public CallLogs(String number, Integer type, Integer callType, Long date, Long duration) {
        this.number = number;
        this.type = type;
        this.callType = callType;
        this.date = date;
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public Integer getType() {
        return type;
    }

    public Long getDate() {
        return date;
    }

    public Long getDuration() {
        return duration;
    }

    public int getCallTypeImageId() {
        return callTypeImageIds[callType-1];
    }

    public String typeToString() {
        return numberType[type];
    }

    public Integer getCallType() {
        return callType;
    }
}
