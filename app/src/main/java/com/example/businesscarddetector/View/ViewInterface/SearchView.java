package com.example.businesscarddetector.View.ViewInterface;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.SearchPresenter;

import java.util.List;

public interface SearchView extends BaseView<SearchPresenter> {
    void setContactList(List<ContactModel> contactModelList);

    void showEmptyMessage();
}
