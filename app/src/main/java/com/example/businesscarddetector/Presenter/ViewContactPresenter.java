package com.example.businesscarddetector.Presenter;

import com.example.businesscarddetector.Model.ContactModel;

public interface ViewContactPresenter {
    ContactModel getContactById(long id);

    void populateContact(String vCardString);

    void populateContact(ContactModel contactModel);

    void _insertContact(ContactModel contactModel);

    void _showConfirmDeleteDialog(ContactModel contactModel);

    void setConfirm(boolean confirm, ContactModel contactModel);


}
