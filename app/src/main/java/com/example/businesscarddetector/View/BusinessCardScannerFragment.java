package com.example.businesscarddetector.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.utils.GistFragmentUtils;


public class BusinessCardScannerFragment extends Fragment {

    public BusinessCardScannerFragment(VCardModel vCardModel) {
        this.vCardModel = vCardModel;
    }

    private VCardModel vCardModel;
    private ConstraintLayout loaderLayout;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_card_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderLayout = view.findViewById(R.id.progres_bar_layout);
        progressBar = loaderLayout.findViewById(R.id.loader_progress);
        if (vCardModel != null) {
            navigateToViewContacts(vCardModel);
        } else {
            navigateToLandingFragment();
        }

    }

    private void navigateToLandingFragment() {
        LandingFragment landingFragment = new LandingFragment();
        GistFragmentUtils.switchFragmentReplace(getActivity(), landingFragment, false, false);
    }

    private void navigateToViewContacts(VCardModel vCardModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactModel", vCardModel);
        progressBar.setVisibility(View.GONE);
        ViewContactFragment viewContactFragment = new ViewContactFragment();
        viewContactFragment.setArguments(bundle);
        GistFragmentUtils.switchFragmentReplace(getActivity(), viewContactFragment, false, false);
    }

}