package com.itext.pdfDinamico.Model;

public class DateEvidencias {
    private String name;
    private String hash;
    private String id;

    // Constructor por defecto
    public DateEvidencias() {
    }

    // Constructor
    public DateEvidencias(String name, String hash, String id) {
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
