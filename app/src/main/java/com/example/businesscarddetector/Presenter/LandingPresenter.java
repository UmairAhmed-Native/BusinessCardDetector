package com.example.businesscarddetector.Presenter;

import com.example.businesscarddetector.Model.ContactModel;

public interface LandingPresenter {
    void _getContacts();
    void _getContactsByCName(String cName);

    void _getContactsByPName(String pName);

    void _getContactsByContact(String contact);

    void _getContactsByDesig(String desig);

    void _getContactsByEmail(String email);
}