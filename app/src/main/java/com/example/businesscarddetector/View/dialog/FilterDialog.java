package com.example.businesscarddetector.View.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.SearchFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private SearchFragment searchFragmentInstance;
    private View rootView;
    private TextView txtByCompany;
    private TextView txtByPerson;
    private TextView txtByDesignation;
    private ImageView chckCompany;
    private ImageView chckPerson;
    private ImageView chckDesignation;
    private RelativeLayout rrMain;
    public int searchType;

    public FilterDialog(SearchFragment searchFragment) {
        this.searchFragmentInstance = searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.theme_botom_sheet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());

        });

        rrMain = rootView.findViewById(R.id.rr_main);
        txtByCompany = rootView.findViewById(R.id.btn_company_txt);
        chckCompany = rootView.findViewById(R.id.check_company);
        txtByDesignation = rootView.findViewById(R.id.btn_designation_txt);
        chckPerson = rootView.findViewById(R.id.check_person);
        txtByPerson = rootView.findViewById(R.id.btn_person_txt);
        chckDesignation = rootView.findViewById(R.id.check_designation);
        rrMain.setOnClickListener(this);
        txtByCompany.setOnClickListener(this);
        txtByDesignation.setOnClickListener(this);
        txtByPerson.setOnClickListener(this);
        if (searchType == 1) {
            chckDesignation.setVisibility(View.GONE);
            chckPerson.setVisibility(View.GONE);
            chckCompany.setVisibility(View.VISIBLE);
        } else if (searchType == 2) {
            chckDesignation.setVisibility(View.GONE);
            chckPerson.setVisibility(View.VISIBLE);
            chckCompany.setVisibility(View.GONE);
        } else if (searchType == 3) {
            chckDesignation.setVisibility(View.VISIBLE);
            chckPerson.setVisibility(View.GONE);
            chckCompany.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogInflater = inflater.inflate(R.layout.dialog_filter, container, false);
        rootView = dialogInflater.getRootView();
//        initViews();
        return rootView;


    }

    @Override
    public void onClick(View view) {
        if (view == rrMain) {
            dismiss();
        } else if (view == txtByCompany) {
            chckDesignation.setVisibility(View.GONE);
            chckPerson.setVisibility(View.GONE);
            chckCompany.setVisibility(View.VISIBLE);
            searchFragmentInstance.searchFilter(1);
            dismiss();
        } else if (view == txtByPerson) {
            chckDesignation.setVisibility(View.GONE);
            chckPerson.setVisibility(View.VISIBLE);
            chckCompany.setVisibility(View.GONE);
            searchFragmentInstance.searchFilter(2);
            dismiss();
        } else if (view == txtByDesignation) {
            chckDesignation.setVisibility(View.VISIBLE);
            chckPerson.setVisibility(View.GONE);
            chckCompany.setVisibility(View.GONE);
            searchFragmentInstance.searchFilter(3);
            dismiss();
        }
    }
}
