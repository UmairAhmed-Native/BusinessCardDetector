package com.example.businesscarddetector.View.ViewInterface;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.AddUpdatePresenter;

public interface AddUpdateView extends BaseView<AddUpdatePresenter> {
    void populateContact(ContactModel contactModel);
    void clearAll();
    void close(boolean isInsert);

}
