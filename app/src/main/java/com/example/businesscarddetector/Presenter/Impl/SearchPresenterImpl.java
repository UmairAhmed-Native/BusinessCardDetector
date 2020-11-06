package com.example.businesscarddetector.Presenter.Impl;

import androidx.lifecycle.Observer;

import com.example.businesscarddetector.Local.ContactDao;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.SearchPresenter;
import com.example.businesscarddetector.View.ViewInterface.SearchView;

import java.util.List;

public class SearchPresenterImpl implements SearchPresenter {
    private ContactDao contactDao;
    private SearchView mView;

    public SearchPresenterImpl(ContactDao contactDao, SearchView mView) {

        this.mView = mView;
        this.contactDao = contactDao;
        this.mView.setPresenter(this);
    }

    @Override
    public void _getContacts() {
        contactDao._getContactsFromDb().observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByCName(String cName) {
        contactDao._getSearchContactsFromDbByCName(cName).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByPName(String pName) {
        contactDao._getSearchContactsFromDbByPName(pName).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByContact(String contact) {
        contactDao._getSearchContactsFromDbByContact(contact).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByDesig(String desig) {
        contactDao._getSearchContactsFromDbByDesig(desig).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void _getContactsByEmail(String email) {
        contactDao._getSearchContactsFromDbByEmail(email).observeForever(new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModelList) {
                mView.setContactList(contactModelList);
                if (contactModelList == null || contactModelList.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }
}
