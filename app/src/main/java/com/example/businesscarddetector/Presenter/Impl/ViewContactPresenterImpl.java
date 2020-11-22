package com.example.businesscarddetector.Presenter.Impl;

import android.text.TextUtils;

import com.example.businesscarddetector.Local.ContactDao;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.ViewContactPresenter;
import com.example.businesscarddetector.View.ViewInterface.ViewContactView;

import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class ViewContactPresenterImpl implements ViewContactPresenter {
    private ContactDao contactDao;
    private ViewContactView mView;

    public ViewContactPresenterImpl(ContactDao _contactDao, ViewContactView _mView) {
        this.contactDao = _contactDao;
        this.mView = _mView;
        this.mView.setPresenter(this);
    }

    @Override
    public ContactModel getContactById(long id) {
        return contactDao._getContactById(id);
    }

    @Override
    public void populateContact(String vCardString) {
        boolean showDoneBtn = true;
        String contact = "";
        String designation = "";
        String email = "";
        VCard vcard = Ezvcard.parse(vCardString).first();
        if (vcard == null) {
            mView.invalid();
        } else {
            for (int i = 0; i < vcard.getTelephoneNumbers().size(); i++) {
                if (!TextUtils.isEmpty(vcard.getTelephoneNumbers().get(i).getText())
                        && vcard.getTelephoneNumbers().get(i).getText() != null) {
                    contact = vcard.getTelephoneNumbers().get(i).getText();
                    break;
                }
            }
            for (int i = 0; i < vcard.getTitles().size(); i++) {
                if (!TextUtils.isEmpty(vcard.getTitles().get(i).getValue())
                        && vcard.getTitles().get(i).getValue() != null) {
                    designation = vcard.getTitles().get(i).getValue();
                    break;
                }
            }
            for (int i = 0; i < vcard.getEmails().size(); i++) {
                if (!TextUtils.isEmpty(vcard.getEmails().get(i).getValue())
                        && vcard.getEmails().get(i).getValue() != null) {
                    email = vcard.getEmails().get(i).getValue();
                    break;
                }
            }
            ContactModel contactModel = new ContactModel(
                    vcard.getFormattedName().getValue(),
                    vcard.getOrganization().getValues().get(0), designation,
                    contact, email
            );


            mView.populateContact(contactModel, showDoneBtn);
        }

    }

    @Override
    public void populateContact(ContactModel contactModel) {
        boolean showDoneBtn = false;
        mView.populateContact(contactModel, showDoneBtn);
    }

    @Override
    public void _insertContact(ContactModel contactModel) {
        ContactModel contactModel1 = this.contactDao._getContactByCName(contactModel.getCompanyName());
        if (contactModel1 != null) {
            if (validateContact(contactModel, contactModel1)) {
                long ids = this.contactDao._insertContact(contactModel);
                mView.close(true);
            } else {
                mView.alreadyExist(true);
            }

        } else {
            long ids = this.contactDao._insertContact(contactModel);
            mView.close(true);
        }

    }

    @Override
    public void _showConfirmDeleteDialog(ContactModel contactModel) {
        mView.showDeleteConfirmDialog(contactModel);
    }


    @Override
    public void setConfirm(boolean confirm, ContactModel contactModel) {
        if (confirm) {
            ContactModel contactModel1 = contactDao._getContactById(contactModel.id);
            contactDao._deleteContact(contactModel1);
            mView.close(false);
        }
    }

    private Boolean validateContact(ContactModel contactModelFromQR, ContactModel contactModelFromDB) {
        boolean notExist = false;
        if (!contactModelFromQR.getCompanyName().equals(contactModelFromDB.getCompanyName())) {
            notExist = true;
        } else if (!contactModelFromQR.getPersonName().equals(contactModelFromDB.getPersonName())) {
            notExist = true;
        } else if (!contactModelFromQR.getEmailAddress().equals(contactModelFromQR.getEmailAddress())) {
            notExist = true;
        } else if (!contactModelFromQR.getDesignation().equals(contactModelFromDB.getDesignation())) {
            notExist = true;
        } else if (!contactModelFromQR.getContactNumber().equals(contactModelFromDB.getContactNumber())) {
            notExist = true;
        }
        return notExist;
    }
}
