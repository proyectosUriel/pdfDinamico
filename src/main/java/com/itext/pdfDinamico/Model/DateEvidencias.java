package com.itext.pdfDinamico.Model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateEvidencias {
    @JsonProperty("name_tag")
    private String name_tag;

    @JsonProperty("name")
    private String name;

    @JsonProperty("date_tag")
    private String date_tag;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("receiver_tag")
    private String receiver_tag;

    @JsonProperty("receiver")
    private String receiver;

    @JsonProperty("detail_label")
    private String detail_label;

    @JsonProperty("detail")
    private String detail;

    // Constructor por defecto
    public DateEvidencias() {
    }

    // Constructor
    public DateEvidencias(String name_tag, String name, String date_tag, Date date, String receiver_tag,
            String receiver, String detail_label, String detail) {
        this.name_tag = name_tag;
        this.name = name;
        this.date_tag = date_tag;
        this.date = date;
        this.receiver_tag = receiver_tag;
        this.receiver = receiver;
        this.detail_label = detail_label;
        this.detail = detail;
    }

    // Getters & Setters
    public String getName_tag() {
        return name_tag;
    }

    public void setName_tag(String name_tag) {
        this.name_tag = name_tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_tag() {
        return date_tag;
    }

    public void setDate_tag(String date_tag) {
        this.date_tag = date_tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReceiver_tag() {
        return receiver_tag;
    }

    public void setReceiver_tag(String receiver_tag) {
        this.receiver_tag = receiver_tag;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDetail_label() {
        return detail_label;
    }

    public void setDetail_label(String detail_label) {
        this.detail_label = detail_label;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    
 
}
