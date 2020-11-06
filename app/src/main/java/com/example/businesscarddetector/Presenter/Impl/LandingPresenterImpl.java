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
}

