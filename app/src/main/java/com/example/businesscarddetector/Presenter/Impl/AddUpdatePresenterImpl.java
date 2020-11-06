package com.example.businesscarddetector.Presenter.Impl;


import android.text.TextUtils;
import android.util.Patterns;

import com.example.businesscarddetector.Local.ContactDao;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.AddUpdatePresenter;
import com.example.businesscarddetector.View.ViewInterface.AddUpdateView;

public class AddUpdatePresenterImpl implements AddUpdatePresenter {
    private ContactDao _contactDao;
    private AddUpdateView addUpdateContactView;

    public AddUpdatePresenterImpl(ContactDao contactDao, AddUpdateView addUpdateContactView) {

        this.addUpdateContactView = addUpdateContactView;
        this.addUpdateContactView.setPresenter(this);
        this._contactDao = contactDao;
    }

    @Override
    public void _insertContact(ContactModel contactModel) {
        long ids = this._contactDao._insertContact(contactModel);
        addUpdateContactView.close(true);
    }

    @Override
    public int _validateContact(ContactModel contactModel) {
        if (TextUtils.isEmpty(contactModel.getCompanyName())) {
            return 1;
        } else if (TextUtils.isEmpty(contactModel.getPersonName())) {
            return 2;
        } else if (TextUtils.isEmpty(contactModel.getContactNumber())) {
            return 3;
        } else if (TextUtils.isEmpty(contactModel.getDesignation())) {
            return 4;
        } else if (TextUtils.isEmpty(contactModel.getEmailAddress())
                && (!Patterns.EMAIL_ADDRESS.matcher(contactModel.getEmailAddress()).matches())) {
            return 5;
        }
        return -1;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target));
    }

    @Override
    public ContactModel _getContactById(Long id) {
        return null;
    }

    @Override
    public void _updateContact(ContactModel contactModel) {
        ContactModel contactModel1 = this._contactDao._getContactById(contactModel.id);
        if (!contactModel1.getDesignation().equals(contactModel.getDesignation())) {
            contactModel1.setDesignation(contactModel.getDesignation());
        }
        if (!contactModel1.getPersonName().equals(contactModel.getPersonName())) {
            contactModel1.setPersonName(contactModel.getPersonName());
        }
        if (!contactModel1.getContactNumber().equals(contactModel.getContactNumber())) {
            contactModel1.setContactNumber(contactModel.getContactNumber());
        }
        if (!contactModel1.getCompanyName().equals(contactModel.getCompanyName())) {
            contactModel1.setCompanyName(contactModel.getCompanyName());
        }
        if (!contactModel1.getEmailAddress().equals(contactModel.getEmailAddress())) {
            contactModel1.setEmailAddress(contactModel.getEmailAddress());
        }
        int isUpdate = this._contactDao._updateContactModel(contactModel1);
        addUpdateContactView.close(false);
    }

    @Override
    public void _populateContactForUpdate(ContactModel contactModel) {
        addUpdateContactView.populateContact(contactModel);
    }
}

