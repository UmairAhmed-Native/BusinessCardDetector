package com.example.businesscarddetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.View.BusinessCardScannerFragment;
import com.example.businesscarddetector.View.LandingFragment;
import com.example.businesscarddetector.View.ViewContactFragment;
import com.example.businesscarddetector.utils.Constants;
import com.example.businesscarddetector.utils.GistFragmentUtils;

import io.reactivex.annotations.Nullable;

import static com.example.businesscarddetector.utils.Constants.REQUEST_CODE_QR_SCAN;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getStringExtra(Constants.GOT_RESULT) != null) {
            String result = getIntent().getStringExtra(Constants.GOT_RESULT);
            VCardModel vCardModel = new VCardModel(result);
            Bundle bundle = new Bundle();
            bundle.putSerializable("vCardInfo", vCardModel);
            BusinessCardScannerFragment businessCardScannerFragment = new BusinessCardScannerFragment(vCardModel);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, businessCardScannerFragment).commit();
        } else {
            LandingFragment landingFragment = new LandingFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, landingFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (GistFragmentUtils.getCurrentFragment(this) instanceof ViewContactFragment) {
            LandingFragment landingFragment = new LandingFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, landingFragment).commit();
        } else {
            super.onBackPressed();

        }
    }
}