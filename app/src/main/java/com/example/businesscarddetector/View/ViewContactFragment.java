package com.example.businesscarddetector.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.businesscarddetector.Local.AppDatabase;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.Presenter.Impl.ViewContactPresenterImpl;
import com.example.businesscarddetector.Presenter.ViewContactPresenter;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewInterface.ViewContactView;
import com.example.businesscarddetector.View.dialog.DeleteDialog;
import com.example.businesscarddetector.utils.GistFragmentUtils;

import java.util.Objects;

import ezvcard.Ezvcard;
import ezvcard.VCard;


public class ViewContactFragment extends Fragment implements View.OnClickListener, ViewContactView {
    private View rootView;
    private TextView txtCompany;
    private TextView txtPerson;
    private TextView txtContact;
    private TextView txtEmail;
    private TextView txtDesignation;
    private TextView txtDone;
    private VCardModel vCardModel;
    private ViewContactPresenter mPresenter;
    private TextView deleteText;
    private ImageView deleteImage;
    private ConstraintLayout deleteLayout;
    private TextView updateText;
    private ImageView updateImage;
    private ConstraintLayout updateLayout;
    private ContactModel contactModel;
    private boolean isRemoveBtn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addUpdatedFragmentInflater =
                inflater.inflate(R.layout.fragment_view_contact, container, false);
        rootView = addUpdatedFragmentInflater.getRootView();
        // Inflate the layout for this fragment
        initViews();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getContext());
        mPresenter = new ViewContactPresenterImpl(db.contactDao(), this);
        if (getArguments() != null) {
            if (getArguments().getSerializable("contactModel") != null) {
                vCardModel = (VCardModel) getArguments().getSerializable("contactModel");
                if (vCardModel.getContactModel() == null && vCardModel.getvCardString() != null) {
                    mPresenter.populateContact(vCardModel.getvCardString());
                } else if (vCardModel.getContactModel() != null && vCardModel.getvCardString() == null) {
                    this.contactModel = vCardModel.getContactModel();
                    mPresenter.populateContact(vCardModel.getContactModel());
                }
            }
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("contact_model_update"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            ContactModel contactModelUpdate = (ContactModel) intent.getSerializableExtra("contact_update");
            Log.d("receiver", "Got message: ");
            ContactModel contactModelById = mPresenter.getContactById(contactModelUpdate.id);
            setRecords(contactModel);
            Toast.makeText(getContext(), "Contact Update Successfully", Toast.LENGTH_LONG).show();
        }
    };

    private void initViews() {
        updateLayout = rootView.findViewById(R.id.layout_update);
        deleteLayout = rootView.findViewById(R.id.layout_delete);
        deleteImage = deleteLayout.findViewById(R.id.img_image);
        deleteText = deleteLayout.findViewById(R.id.txt_name);
        updateImage = updateLayout.findViewById(R.id.img_image);
        updateText = updateLayout.findViewById(R.id.txt_name);
        txtCompany = rootView.findViewById(R.id.txt_company);
        txtPerson = rootView.findViewById(R.id.txt_person);
        txtEmail = rootView.findViewById(R.id.txt_email);
        txtContact = rootView.findViewById(R.id.txt_contact);
        txtDesignation = rootView.findViewById(R.id.txt_designation);
        txtDone = rootView.findViewById(R.id.done_btn);
//        deleteBtn = rootView.findViewById(R.id.delete_btn);
        deleteText.setText("Delete");
        deleteImage.setImageResource(R.drawable.ic_delete);
        updateText.setText("Update");
        updateImage.setImageResource(R.drawable.ic_update);
//        updateBtn = rootView.findViewById(R.id.update_btn);
        deleteLayout.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == txtDone) {
            if (contactModel == null) {
                contactModel = new ContactModel(
                        txtPerson.getText().toString().trim(),
                        txtCompany.getText().toString().trim(),
                        txtDesignation.getText().toString().trim(),
                        txtContact.getText().toString().trim(),
                        txtEmail.getText().toString().trim());
            }
            /*else {
                contactModel.setEmailAddress(txtEmail.getText().toString().trim());
                contactModel.setCompanyName(txtCompany.getText().toString().trim());
                contactModel.setContactNumber(txtContact.getText().toString().trim());
                contactModel.setPersonName(txtPerson.getText().toString().trim());
                contactModel.setDesignation(txtDesignation.getText().toString().trim());
            }*/

            insertContact(contactModel);
        } else if (view == deleteLayout) {
            mPresenter._showConfirmDeleteDialog(this.contactModel);
        } else if (view == updateLayout) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contact_model_update", contactModel);
            AddUpdateFragment addUpdateFragment = new AddUpdateFragment();
            addUpdateFragment.setArguments(bundle);
            GistFragmentUtils.switchFragmentAdd(getActivity(), addUpdateFragment, true, false);
        }
    }

    private void insertContact(ContactModel contactModel) {

        mPresenter._insertContact(contactModel);

    }

    @Override
    public void populateContact(ContactModel contactModel, boolean showDoneBtn) {
        if (showDoneBtn) {
            isRemoveBtn = true;
            deleteText.setText("Remove");
            updateLayout.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
        } else {
            isRemoveBtn = false;
            deleteText.setText("Delete");
            updateLayout.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
        }
        this.contactModel = contactModel;
        setRecords(contactModel);

    }

    @Override
    public void close(boolean isInsert) {
        if (isInsert) {
            Toast.makeText(getContext(), "Contact Insert Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Contact Delete Successfully", Toast.LENGTH_LONG).show();
        }
        navigateToLandingFragment();
    }

    private void navigateToLandingFragment() {
        LandingFragment landingFragment = new LandingFragment();
        GistFragmentUtils.switchFragmentReplace(getActivity(), landingFragment, false, false);
    }

    @Override
    public void setPresenter(ViewContactPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showDeleteConfirmDialog(ContactModel contactModel) {
        DialogFragment deleteDialog = new DeleteDialog(this, isRemoveBtn);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact_model_delete", contactModel);
        deleteDialog.setArguments(bundle);
        deleteDialog.show(getChildFragmentManager(), "confirmDialog");
    }

    public void setConfirm(boolean isConfirm, ContactModel contactModel, boolean isRemoveBtn) {
        if (isRemoveBtn) {
            Objects.requireNonNull(getActivity()).onBackPressed();
        } else {
            mPresenter.setConfirm(isConfirm, contactModel);
        }
    }

    @Override
    public void alreadyExist() {
        Toast.makeText(getContext(), "Contact already Exist", Toast.LENGTH_LONG).show();
    }

    private void setRecords(ContactModel contactModel) {
        txtCompany.setText(" " + contactModel.getCompanyName());
        txtPerson.setText(" " + contactModel.getPersonName());
        txtDesignation.setText(" " + contactModel.getDesignation());
        txtContact.setText(" " + contactModel.getContactNumber());
        txtEmail.setText(" " + contactModel.getEmailAddress());
    }


}