package com.example.businesscarddetector.View.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.SearchFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterDialog extends BottomSheetDialogFragment {
    private SearchFragment searchFragmentInstance;
    private View rootView;
    private TextView txtByCompany;
    private TextView txtByPerson;
    private TextView txtByDesignation;
    private ImageView chckCompany;
    private ImageView chckPerson;
    private ImageView chckDesignation;

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
        txtByCompany = rootView.findViewById(R.id.btn_company_txt);
        txtByDesignation = rootView.findViewById(R.id.btn_designation_txt);
        txtByPerson = rootView.findViewById(R.id.btn_person_txt);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogInflater = inflater.inflate(R.layout.dialog_filter, container, false);
        rootView = dialogInflater.getRootView();
//        initViews();
        return rootView;


    }

}
