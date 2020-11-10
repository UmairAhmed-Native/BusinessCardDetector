package com.example.businesscarddetector.View;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import com.example.businesscarddetector.utils.Constants;
import com.example.businesscarddetector.Local.AppDatabase;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Presenter.Impl.LandingPresenterImpl;
import com.example.businesscarddetector.Presenter.LandingPresenter;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewInterface.LandingView;
import com.example.businesscarddetector.utils.EqualSpacingItemDecoration;
import com.example.businesscarddetector.utils.GistFragmentUtils;
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
    private EqualSpacingItemDecoration equalSpacingItemDecoration;

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

    }

    private void initView() {
        addNewContact = rootView.findViewById(R.id.add_contact_fab);
        contactListRv = rootView.findViewById(R.id.contact_list_rv);
        emptyView = rootView.findViewById(R.id.empty_view);
        qrScanLayout = rootView.findViewById(R.id.qr_scan_layout);
        contactTextTitle = rootView.findViewById(R.id.contacts_txt);
        searchLayout = rootView.findViewById(R.id.layout_search);
        equalSpacingItemDecoration = new EqualSpacingItemDecoration(5, 1);
        contactListAdapter = new ContactAdapter(contactModelList, this);
        contactListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactListRv.addItemDecoration(equalSpacingItemDecoration);
        contactListRv.setAdapter(contactListAdapter);
        contactListAdapter.notifyDataSetChanged();
        searchLayout.setOnClickListener(this);
        qrScanLayout.setOnClickListener(this);
        addNewContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == addNewContact) {
            navigateToAddUpdateFragment();
        } else if (view == qrScanLayout) {
            if (checkPermission()) {
                Intent i = new Intent(getActivity(), QRScannerActivity.class);
                i.putExtra(Constants.START_ACTIVITY_FOR_RESULT, true);
                getActivity().startActivity(i);
            }
        } else if (view == searchLayout) {
            navigateToSearchFragment();
        }
    }

    private void navigateToAddUpdateFragment() {
        AddUpdateFragment addUpdateFragment = new AddUpdateFragment();

        GistFragmentUtils.switchFragmentAdd(getActivity(), addUpdateFragment, true, false);
    }

    private void navigateToSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        GistFragmentUtils.switchFragmentAdd(getActivity(), searchFragment, true, false);
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
        GistFragmentUtils.switchFragmentAdd(getActivity(), viewContactFragment, false, false);

    }
}