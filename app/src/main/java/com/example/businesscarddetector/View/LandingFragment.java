package com.example.businesscarddetector.View;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.businesscarddetector.Adapter.ContactAdapter;
import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.QRScannerActivity;
import com.example.businesscarddetector.View.ViewInterface.ContactNavigationListener;
import com.example.businesscarddetector.View.dialog.FilterDialog;
import com.example.businesscarddetector.utils.Constants;
import com.example.businesscarddetector.Local.AppDatabase;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.Impl.LandingPresenterImpl;
import com.example.businesscarddetector.Presenter.LandingPresenter;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewInterface.LandingView;
import com.example.businesscarddetector.utils.EqualSpacingItemDecoration;
import com.example.businesscarddetector.utils.GistFragmentUtils;
import com.example.businesscarddetector.utils.KeyboardUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.businesscarddetector.utils.Constants.REQUEST_CODE_QR_SCAN;

public class LandingFragment extends Fragment implements View.OnClickListener, LandingView, ContactNavigationListener {
    private RecyclerView contactListRv;
    private TextView emptyView;
    private View rootView;
    private FloatingActionButton addNewContact;
    private ContactAdapter contactListAdapter;
    private List<ContactModel> contactModelList = new ArrayList<ContactModel>();
    private LandingPresenter landingPresenter;
    private TextView contactTextTitle;
    private ImageView qrScanImage;
    private TextView qrScanText;
    private ConstraintLayout searchLayout;
    private ConstraintLayout qrScanLayout;
    private EditText searchEdt;
    private ImageView searchFilter;
    private EqualSpacingItemDecoration equalSpacingItemDecoration;
    private String searchString;
    private int searchType = -1;
    private FilterDialog filterDialog;
    private ConstraintLayout mainContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View landingFragmentInflater =
                inflater.inflate(R.layout.fragment_landing, container, false);
        rootView = landingFragmentInflater.getRootView();
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getContext());
        landingPresenter = new LandingPresenterImpl(db.contactDao(), this);
        landingPresenter._getContacts();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("contact_model_insert"));
    }

    private void initView() {
        mainContainer = rootView.findViewById(R.id.main_container);
        filterDialog = new FilterDialog(this);
        addNewContact = rootView.findViewById(R.id.add_contact_fab);
        contactListRv = rootView.findViewById(R.id.contact_list_rv);
        emptyView = rootView.findViewById(R.id.empty_view);
        qrScanLayout = rootView.findViewById(R.id.qr_scan_layout);
        contactTextTitle = rootView.findViewById(R.id.contacts_txt);
        searchLayout = rootView.findViewById(R.id.layout_search);
        searchEdt = searchLayout.findViewById(R.id.txt_name);
        equalSpacingItemDecoration = new EqualSpacingItemDecoration(5, 1);
        contactListAdapter = new ContactAdapter(contactModelList, this);
        contactListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactListRv.addItemDecoration(equalSpacingItemDecoration);
        contactListRv.setAdapter(contactListAdapter);
        searchFilter = searchLayout.findViewById(R.id.img_filter_search);
        contactListAdapter.notifyDataSetChanged();
        qrScanLayout.setOnClickListener(this);
        addNewContact.setOnClickListener(this);
        searchFilter.setOnClickListener(this);
        mainContainer.setOnClickListener(this);
        searchEdt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                searchString = searchEdt.getText().toString();
                if (TextUtils.isEmpty(searchString)) {
                    landingPresenter._getContacts();
                } else {
                    searchFilter(searchType);
                }


            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("contact_model_insert"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            ContactModel contactModelUpdate = (ContactModel) intent.getSerializableExtra("contact_model_insert");
            Log.d("receiver", "Got message: ");
            landingPresenter._getContacts();
            Toast.makeText(getContext(), "Record Insert Successfully", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View view) {
        if (view == addNewContact) {
            KeyboardUtil.hideKeyboard(getActivity());
            navigateToAddUpdateFragment();
        } else if (view == qrScanLayout) {
            KeyboardUtil.hideKeyboard(getActivity());
            if (checkPermission()) {
                Intent i = new Intent(getActivity(), QRScannerActivity.class);
                i.putExtra(Constants.START_ACTIVITY_FOR_RESULT, true);
                getActivity().startActivity(i);
            }
        } else if (view == searchFilter) {
            filterDialog.searchType = this.searchType;
            filterDialog.show(getFragmentManager(), "filter_dialog");
            // navigateToSearchFragment();
        } else if (view == mainContainer) {
            KeyboardUtil.hideKeyboard(getActivity());
        }
    }

    private void navigateToAddUpdateFragment() {
        AddUpdateFragment addUpdateFragment = new AddUpdateFragment();

        GistFragmentUtils.switchFragmentAdd(getActivity(), addUpdateFragment, true, false);
    }

    private void navigateToSearchFragment() {
//        SearchFragment searchFragment = new SearchFragment();
//        GistFragmentUtils.switchFragmentAdd(getActivity(), searchFragment, true, false);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                Constants.PERMISSION_REQUEST_CODE);
    }


    @Override
    public void setContactList(List<ContactModel> contactModelList) {
        emptyView.setVisibility(View.GONE);
        contactListRv.setVisibility(View.VISIBLE);
        contactTextTitle.setVisibility(View.VISIBLE);
        this.contactModelList.clear();
        this.contactModelList.addAll(contactModelList);
        contactListAdapter.notifyDataSetChanged();
    }


    @Override
    public void showEmptyMessage() {
        emptyView.setVisibility(View.VISIBLE);
        contactListRv.setVisibility(View.GONE);
        contactTextTitle.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(LandingPresenter presenter) {
        this.landingPresenter = presenter;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(getActivity(), QRScannerActivity.class);
                    getActivity().startActivityForResult(i, Constants.REQUEST_CODE_QR_SCAN);

                    // main logic
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_QR_SCAN && data != null) {
             navigateToViewContacts(data);
         }

     }*/

    @Override
    public void navigateToContactDetail(ContactModel contactModel) {
        VCardModel vCardModel = new VCardModel(contactModel);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactModel", vCardModel);
        ViewContactFragment viewContactFragment = new ViewContactFragment();
        viewContactFragment.setArguments(bundle);
        GistFragmentUtils.switchFragmentAdd(getActivity(), viewContactFragment, true, false);

    }

    public void searchFilter(int searchType) {
        switch (searchType) {
            case 1: {
                this.searchType = 1;
                getContactsByCompany(searchString);
            }
            break;
            case 2: {
                this.searchType = 2;
                getContactsByPerson(searchString);
            }
            break;
            case 3: {
                this.searchType = 3;
                getContactsByDesignation(searchString);
            }
            break;
            default: {
                getContactsByCompany(searchString);
            }
            break;
        }
    }

    private void getContactsByCompany(String search) {
        landingPresenter._getContactsByCName(search);
    }

    private void getContactsByPerson(String search) {
        landingPresenter._getContactsByPName(search);
    }

    private void getContactsByDesignation(String search) {
        landingPresenter._getContactsByDesig(search);
    }
}