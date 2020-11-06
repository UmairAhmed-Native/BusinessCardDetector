package com.example.businesscarddetector.Presenter;


import com.example.businesscarddetector.Model.ContactModel;

public interface AddUpdatePresenter {

    void _insertContact(ContactModel contactModel);

    int _validateContact(ContactModel contactModel);

    ContactModel _getContactById(Long id);

    void _updateContact(ContactModel contactModel);

    void _populateContactForUpdate(ContactModel contactModel);

}

