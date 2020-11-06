package com.example.businesscarddetector.View.ViewInterface;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.ViewContactPresenter;

public interface ViewContactView extends BaseView<ViewContactPresenter> {

    void populateContact(ContactModel contactModel,boolean showDoneBtn);
    void close(boolean isInsert);
    void showDeleteConfirmDialog(ContactModel contactModel);
    void alreadyExist();
}
