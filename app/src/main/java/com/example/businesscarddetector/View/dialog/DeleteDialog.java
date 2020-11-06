package com.example.businesscarddetector.View.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Delete;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.Presenter.ViewContactPresenter;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewContactFragment;


public class DeleteDialog extends DialogFragment implements View.OnClickListener {
    private View rootView;
    private TextView deleteBtn;
    private TextView cancelBtn;
    private TextView deleteTitleTxt;
    private ContactModel contactModel;
    private ViewContactFragment fragment;
    private boolean isRemove = false;
    private TextView deleteDesc;

    public DeleteDialog(ViewContactFragment fragment, boolean isRemove) {
        this.fragment = fragment;
        this.isRemove = isRemove;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View dialogInflater = inflater.inflate(R.layout.dialog_delete, container, false);
        rootView = dialogInflater.getRootView();
        initViews();
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable("contact_model_delete") != null) {
                contactModel = (ContactModel) getArguments().getSerializable("contact_model_delete");

            }
        }
        if (isRemove) {
            deleteTitleTxt.setText("Remove");
            deleteTitleTxt.append(" " + contactModel.getPersonName() + "?");
            deleteBtn.setText("Remove");
            deleteDesc.setText("This scanned contact will be lost");

        } else {
            deleteTitleTxt.setText("Delete");
            deleteTitleTxt.append(" " + contactModel.getPersonName() + "?");
            deleteBtn.setText("Delete");

        }


    }

    private void initViews() {
        deleteDesc = rootView.findViewById(R.id.txt_desc_delete);
        deleteTitleTxt = rootView.findViewById(R.id.txt_title_delete);
        deleteBtn = rootView.findViewById(R.id.btn_delete);
        cancelBtn = rootView.findViewById(R.id.btn_cancel);
        deleteBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == deleteBtn) {
            fragment.setConfirm(true, this.contactModel, isRemove);
        } else if (view == cancelBtn) {
            this.dismiss();
        }

    }
}
