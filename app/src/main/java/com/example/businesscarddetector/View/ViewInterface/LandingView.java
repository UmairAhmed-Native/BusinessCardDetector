package com.example.businesscarddetector.View.ViewInterface;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.LandingPresenter;

import java.util.List;

public interface LandingView extends BaseView<LandingPresenter> {

    void setContactList(List<ContactModel> contactModelList);

    void showEmptyMessage();
}
