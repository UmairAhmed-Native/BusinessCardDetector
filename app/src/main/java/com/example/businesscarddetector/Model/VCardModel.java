package com.example.businesscarddetector.Model;

import java.io.Serializable;

public class VCardModel implements Serializable {
    private ContactModel contactModel;
    private String vCardString;

    public VCardModel(ContactModel contactModel) {
        this.contactModel = contactModel;

    }

    public VCardModel(String vCardString) {
        this.vCardString = vCardString;
    }

    public ContactModel getContactModel() {
        return this.contactModel;
    }

    public String getvCardString() {
        return this.vCardString;
    }
}
