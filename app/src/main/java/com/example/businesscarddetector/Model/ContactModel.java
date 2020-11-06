package com.example.businesscarddetector.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contact_model")
public class ContactModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "person_name")
    private String personName;
    @ColumnInfo(name = "company_name")
    private String companyName;
    @ColumnInfo(name = "designation")
    private String designation;
    @ColumnInfo(name = "contact_no")
    private String contactNumber;
    @ColumnInfo(name = "email_address")
    private String emailAddress;

    @Ignore
    public ContactModel() {
        this.personName = "";
        this.companyName = "";
        this.designation = null;
        this.contactNumber = "";
        this.emailAddress = "";
    }

    public ContactModel(String personName, String companyName, String designation, String contactNumber, String emailAddress) {
        this.companyName = companyName;
        this.personName = personName;
        this.contactNumber = contactNumber;
        this.designation = designation;
        this.emailAddress = emailAddress;
    }


    public String getCompanyName() {
        return this.companyName;
    }

    public String getPersonName() {
        return this.personName;
    }

    public String getDesignation() {
        return this.designation;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
