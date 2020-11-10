package com.example.businesscarddetector.Presenter.Impl;


import androidx.lifecycle.Observer;

import com.example.businesscarddetector.Local.ContactDao;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.LandingPresenter;
import com.example.businesscarddetector.View.ViewInterface.LandingView;

import java.util.List;

public class LandingPresenterImpl implements LandingPresenter {
    private ContactDao contactDao;
    private LandingView landingView;

    public LandingPresenterImpl(ContactDao contactDao, LandingView landingView) {

        this.landingView = landingView;
        this.contactDao = contactDao;
        this.landingView.setPresenter(this);
    }


    @Override
    public void _getContacts() {
        contactDao._getContactsFromDb().observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByCName(String cName) {
        contactDao._getSearchContactsFromDbByCName(cName).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByPName(String pName) {
        contactDao._getSearchContactsFromDbByPName(pName).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByContact(String contact) {
        contactDao._getSearchContactsFromDbByContact(contact).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByDesig(String desig) {
        contactDao._getSearchContactsFromDbByDesig(desig).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByEmail(String email) {
        contactDao._getSearchContactsFromDbByEmail(email).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                landingView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    landingView.showEmptyMessage();
                }
            }
        });
    }
}

