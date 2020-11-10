package com.example.businesscarddetector.View;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.businesscarddetector.Local.AppDatabase;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.Presenter.AddUpdatePresenter;
import com.example.businesscarddetector.Presenter.Impl.AddUpdatePresenterImpl;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewInterface.AddUpdateView;
import com.example.businesscarddetector.utils.Constants;
import com.example.businesscarddetector.utils.GistFragmentUtils;
import com.example.businesscarddetector.utils.KeyboardUtil;
import com.github.mangstadt.vinnie.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AddUpdateFragment extends Fragment implements View.OnClickListener, AddUpdateView {

    private View rootView;
    private TextInputEditText edtCompany;
    private TextInputEditText edtPerson;
    private TextInputEditText edtContact;
    private TextInputEditText edtDesignation;
    private TextInputEditText edtEmail;
    private TextView btnInsert;
    private AddUpdatePresenter mPresenter;
    private ContactModel contactModel;
    private boolean isEdit = false;
    private TextView insertTitle;
    private ImageView backArrow;
    private TextInputLayout errorCompanyNameEdt;
    private TextInputLayout errorPersonNameEdt;
    private TextInputLayout errorContactEdt;
    private TextInputLayout errorDesignationEdt;
    private TextInputLayout errorEmailEdt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addUpdatedFragmentInflater =
                inflater.inflate(R.layout.fragment_add_update, container, false);
        rootView = addUpdatedFragmentInflater.getRootView();
        // Inflate the layout for this fragment
        initViews();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = AppDatabase.getInstance(getContext());
        mPresenter = new
                AddUpdatePresenterImpl(
                db.contactDao(),
                this
        );
        if (getArguments() != null) {
            if (getArguments().getSerializable("contact_model_update") != null) {
                contactModel = (ContactModel) getArguments().getSerializable("contact_model_update");
                mPresenter._populateContactForUpdate(contactModel);
            }
        }


    }

    private void initViews() {
        errorCompanyNameEdt = rootView.findViewById(R.id.company_name_txt_layout);
        errorPersonNameEdt = rootView.findViewById(R.id.person_name_txt_layout);
        errorContactEdt = rootView.findViewById(R.id.contact_txt_layout);
        errorDesignationEdt = rootView.findViewById(R.id.designation_txt_layout);
        errorEmailEdt = rootView.findViewById(R.id.email_txt_layout);
        backArrow = rootView.findViewById(R.id.img_back_arrow);
        edtCompany = rootView.findViewById(R.id.company_name_edt);
        edtContact = rootView.findViewById(R.id.contact_edt);
        edtPerson = rootView.findViewById(R.id.person_name_edt);
        edtDesignation = rootView.findViewById(R.id.designation_edt);
        edtEmail = rootView.findViewById(R.id.email_edt);
        btnInsert = rootView.findViewById(R.id.insert_btn);
        edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtContact.setInputType(InputType.TYPE_CLASS_PHONE);
        edtCompany.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edtPerson.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edtDesignation.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        insertTitle = rootView.findViewById(R.id.insert_txt);

        edtCompany.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(25)
        });
        edtPerson.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(25)
        });
        edtDesignation.setFilters(new InputFilter[]{
                alphabetFilter, new InputFilter.LengthFilter(20)
        });
        edtContact.setFilters(new InputFilter[]{
                numberFilter, lengthFilter
        });
        btnInsert.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        edtCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    errorCompanyNameEdt.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    errorPersonNameEdt.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    errorContactEdt.setHelperText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDesignation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    errorDesignationEdt.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    errorEmailEdt.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View view) {

        if (view == btnInsert) {
            if (contactModel == null) {
                contactModel = new ContactModel(
                        edtPerson.getText().toString().trim(),
                        edtCompany.getText().toString().trim(),
                        edtDesignation.getText().toString().trim(),
                        edtContact.getText().toString().trim(),
                        edtEmail.getText().toString().trim());
            } else {
                contactModel.setEmailAddress(edtEmail.getText().toString().trim());
                contactModel.setCompanyName(edtCompany.getText().toString().trim());
                contactModel.setContactNumber(edtContact.getText().toString().trim());
                contactModel.setPersonName(edtPerson.getText().toString().trim());
                contactModel.setDesignation(edtDesignation.getText().toString().trim());
            }

            if (isEdit) {
                updateContact(contactModel);
            } else {
                insertContact(contactModel);
            }

            //   navController.navigate(R.id.action_addContactFragment_to_landingFragment)
        } else if (view == backArrow) {
            GistFragmentUtils.onBackPressed(getActivity());
        }

    }

    private void updateContact(ContactModel contactModel) {
        int errorViewType = mPresenter._validateContact(contactModel);
        switch (errorViewType) {
            case -1: {
                KeyboardUtil.hideKeyboard(getActivity());
                mPresenter._updateContact(contactModel);
            }
            break;
            case 1: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 2: {
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 3: {
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 4: {
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 5: {
                errorEmailEdt.setHelperText("This field is required");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 6: {
                errorEmailEdt.setHelperText("Enter valid Email");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 7: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorEmailEdt.setHelperText("This field is required");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 8: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 9: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 10: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
        }
    }

    private void insertContact(ContactModel contactModel) {
        int errorViewType = mPresenter._validateContact(contactModel);
        switch (errorViewType) {
            case -1: {
                KeyboardUtil.hideKeyboard(getActivity());
                mPresenter._insertContact(contactModel);
            }
            break;
            case 1: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 2: {
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 3: {
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 4: {
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 5: {
                errorEmailEdt.setHelperText("This field is required");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 6: {
                errorEmailEdt.setHelperText("Enter valid Email");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;

            case 7: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorEmailEdt.setHelperText("This field is required");
                errorEmailEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 8: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorContactEdt.setHelperText("This field is required");
                errorContactEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 9: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorDesignationEdt.setHelperText("This field is required");
                errorDesignationEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
            case 10: {
                errorCompanyNameEdt.setHelperText("This field is required");
                errorCompanyNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
                errorPersonNameEdt.setHelperText("This field is required");
                errorPersonNameEdt.setHelperTextTextAppearance(R.style.error_appearance);
            }
            break;
        }


    }

    @Override
    public void populateContact(ContactModel contactModel) {
        isEdit = true;
        insertTitle.setText("Update Contact");
        edtCompany.setText(contactModel.getCompanyName());
        edtPerson.setText(contactModel.getPersonName());
        edtDesignation.setText(contactModel.getDesignation());
        edtEmail.setText(contactModel.getEmailAddress());
        edtContact.setText(contactModel.getContactNumber());
        btnInsert.setText("Update");
    }

    @Override
    public void clearAll() {

    }

    @Override
    public void close(boolean isInsert) {
        if (isInsert) {
            Toast.makeText(getContext(), "Record Insert Successfully", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent("contact_model_update");
            intent.putExtra("contact_update", contactModel);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }

        GistFragmentUtils.onBackPressed(getActivity());

    }


    @Override
    public void setPresenter(AddUpdatePresenter presenter) {
        mPresenter = presenter;
    }

    private InputFilter alphabetFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence cs, int start,
                                   int end, Spanned spanned, int dStart, int dEnd) {
            // TODO Auto-generated method stub
            if (cs.equals("") || cs.equals(".")) { // for backspace
                return cs;
            }
            if (cs.toString().matches("[a-zA-Z. ]+")) {
                return cs;
            }
            return "";
        }
    };
    private InputFilter numberFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence cs, int start,
                                   int end, Spanned spanned, int dStart, int dEnd) {
            if (cs.equals("") || cs.equals("-") || cs.equals("+")) { // for backspace
                return cs;
            }
            if (cs.toString().matches("[+0-9 ]+")) {
                return cs;
            }
            return "";
        }
    };
    int maxLength = 15;
    private InputFilter lengthFilter = new InputFilter.LengthFilter(maxLength);


}
