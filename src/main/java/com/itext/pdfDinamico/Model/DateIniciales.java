package com.itext.pdfDinamico.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateIniciales {
    @JsonProperty("name")
    private String name;

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("id")
    private String id;

    // Constructor por defecto
    public DateIniciales() {
    }

    // Constructor
    public DateIniciales(String name, String hash, String id) {
        this.name = name;
        this.hash = hash;
        this.id = id;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
